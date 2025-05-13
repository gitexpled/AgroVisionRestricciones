package lib.servicios;

import com.spire.xls.*;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.FileCopyUtils;
import lib.db.estadoProductorNewDB;
import lib.db.especieDB;
import lib.struc.especie;

public class ExportarExcel {

    public static void exportarGeneral(HttpServletResponse response, String idTemporada) throws Exception {

        // --- Archivo temporal -------------------------------------------------
        String filename = UUID.randomUUID() + ".xlsx";
        String path = System.getProperty("java.io.tmpdir") + File.separator + filename;

        // --- Crear workbook sin hojas por defecto ----------------------------
        Workbook workbook = new Workbook();
        workbook.getWorksheets().clear();

        List<especie> especies =
            especieDB.getAll(new ArrayList<>(), "idEspecie", 0, 9999);
        estadoProductorNewDB dataDB = new estadoProductorNewDB();

        for (especie es : especies) {

            // ----- Obtener datos JSON ----------------------------------------
            String json = dataDB.getRestriccionesExcel(
                    Integer.parseInt(idTemporada),
                    es.getIdEspecie(), "", "", "", "", "", true);
            JSONObject obj = new JSONObject(json);
            JSONArray columns = obj.getJSONArray("columns");
            JSONArray data    = obj.getJSONArray("data");

            // ----- Filtrar filas del PF --------------------------------------
            List<JSONArray> filasValidas = new ArrayList<>();
            for (int i = 0; i < data.length(); i++) {
                JSONArray row = data.getJSONArray(i);
                if (row.getString(3).equalsIgnoreCase(es.getPf())) {
                    filasValidas.add(row);
                }
            }
            if (filasValidas.isEmpty()) continue;   // no crea hoja

            // ----- Crear hoja -------------------------------------------------
            Worksheet sheet = workbook.getWorksheets().add(es.getEspecie());

            // ----- Estilos ----------------------------------------------------
            // Encabezado SIN color de fondo ⇒ sin patrón en “X”
            CellStyle headerStyle = workbook.getStyles().addStyle("Header");
            headerStyle.getFont().isBold(true);
            // Texto azul (o negro, como prefieras)
            headerStyle.getFont().setColor(new Color(0, 112, 192));

            headerStyle.setHorizontalAlignment(HorizontalAlignType.Center);
            headerStyle.setVerticalAlignment(VerticalAlignType.Center);


            // Estilos para valores
            CellStyle styleSI = workbook.getStyles().addStyle("SI");
            styleSI.getFont().setColor(new Color(0, 176, 80));
            styleSI.setColor(new Color(220, 255, 220));    // fondo verde claro

            CellStyle styleNO = workbook.getStyles().addStyle("NO");
            styleNO.getFont().setColor(new Color(192, 0, 0));
            styleNO.setColor(new Color(255, 230, 230));    // fondo rojo claro

            CellStyle stylePI = workbook.getStyles().addStyle("PI");
            stylePI.getFont().setColor(new Color(255, 128, 0));
            stylePI.setColor(new Color(255, 250, 220));    // fondo naranjo claro

            CellStyle baseStyle = workbook.getStyles().addStyle("Base");
            // baseStyle sin color de fondo

            // ----- Escribir encabezados --------------------------------------
            for (int c = 0; c < columns.length(); c++) {
                CellRange h = sheet.getCellRange(1, c + 1);
                h.setText(columns.getString(c).toUpperCase());
                h.setStyle(headerStyle);
                h.getBorders().setLineStyle(LineStyleType.Thin);
            }

            // ----- Índice de columna “CLP” -----------------------------------
            int clpIdx = -1;
            for (int c = 0; c < columns.length(); c++) {
                if ("CLP".equalsIgnoreCase(columns.getString(c))) {
                    clpIdx = c; break;
                }
            }

            // ----- Rellenar datos -------------------------------------------
            int fila = 2;
            for (JSONArray row : filasValidas) {
                for (int c = 0; c < row.length(); c++) {
                    String raw = row.getString(c).trim();
                    String val = raw.toUpperCase();

                    CellRange cell = sheet.getCellRange(fila, c + 1);
                    cell.getBorders().setLineStyle(LineStyleType.Thin);

                    if (c == clpIdx) {      // Columna CLP
                        if (val.startsWith("SI")) {
                            cell.setText("✔️"); cell.setStyle(styleSI);
                        } else if (val.startsWith("NO")) {
                            cell.setText("❌"); cell.setStyle(styleNO);
                        } else {
                            cell.setText("");   cell.setStyle(baseStyle);
                        }
                    } else if (val.startsWith("SI")) {
                        cell.setText("✔️"); cell.setStyle(styleSI);
                    } else if (val.startsWith("NO")) {
                        cell.setText("❌"); cell.setStyle(styleNO);
                    } else if (val.startsWith("PI")) {
                        cell.setText("PI❗"); cell.setStyle(stylePI);
                    } else {
                        cell.setText(raw);   cell.setStyle(baseStyle);
                    }
                }
                fila++;
            }

            // ----- Autoajustar columnas --------------------------------------
            for (int c = 1; c <= columns.length(); c++) {
                sheet.autoFitColumn(c);
            }
        }

        // ----- Guardar y devolver -------------------------------------------
        workbook.saveToFile(path, ExcelVersion.Version2016);
        try (FileInputStream fis = new FileInputStream(path)) {
            response.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader(
                "Content-Disposition", "attachment; filename=\"reporte.xlsx\"");
            FileCopyUtils.copy(fis, response.getOutputStream());
        }
        new File(path).delete(); // limpiar temporal
    }
}
