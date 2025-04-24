package lib.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import lib.struc.especie;



public class excel {

	//static Logger log = LoggerFactory.getLogger(api.cl.goplicity.controller.excel.class);
	public String createExcel() throws Exception
	{
		String fileStr="";
		// Workbook book=WorkbookFactory.create(in);
				Workbook book = new XSSFWorkbook();
				//log.info("comienzo excel");

				ArrayList<especie> arrEspecie = null;
				especieDB especie=new especieDB();
				try {
					arrEspecie = especie.getAllExcel();
				} catch (Exception e) {
					//
					e.printStackTrace();
				}

				Iterator<especie> ff = arrEspecie.iterator();
				while (ff.hasNext()) {
					especie es = ff.next();
				
					estadoProductorNewDB dataDB = new estadoProductorNewDB();
					String json = dataDB.getRestriccionesExcel(0, es.getIdEspecie(), "", "", "", "", "",true);
					
					JSONObject j = new JSONObject(json);
					//log.info("jsonO:"+j);
					JSONArray columns = j.getJSONArray("columns");
					JSONArray data = j.getJSONArray("data");

					Sheet sheet = book.createSheet(es.getEspecie());
					//log.info("-------------------------------------------------");
					CellStyle tituloEstilo = book.createCellStyle();
					tituloEstilo.setAlignment(HorizontalAlignment.CENTER);
					tituloEstilo.setVerticalAlignment(VerticalAlignment.CENTER);
					Font fuenteTitulo = book.createFont();
					fuenteTitulo.setBold(true);
					fuenteTitulo.setFontHeightInPoints((short) 14);
					tituloEstilo.setFont(fuenteTitulo);

					CellStyle headerStyle = book.createCellStyle();
					headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
					headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					headerStyle.setBorderBottom(BorderStyle.THIN);
					headerStyle.setBorderLeft(BorderStyle.THIN);
					headerStyle.setBorderRight(BorderStyle.THIN);
					headerStyle.setBorderTop(BorderStyle.THIN);

					Font font = book.createFont();
					font.setFontName("Arial");
					font.setBold(true);
					font.setColor(IndexedColors.WHITE.getIndex());
					headerStyle.setFont(font);

					// INGRESAMOS LA DATA DEL EXCEL
					CellStyle datoStyle = book.createCellStyle();
					datoStyle.setBorderBottom(BorderStyle.THIN);
					datoStyle.setBorderLeft(BorderStyle.THIN);
					datoStyle.setBorderRight(BorderStyle.THIN);
					datoStyle.setBorderTop(BorderStyle.THIN);

					int i = 1;
					int a = 0;
					int x = 0;
					Row fila = sheet.createRow(0);
					for (int e = 0; e < columns.length(); ++e) {
						String header = columns.getString(e);
						Cell cell = fila.createCell(e);
						cell.setCellStyle(datoStyle);
						cell.setCellValue(header.toString().toUpperCase());
					}
					String espe = "";
					for (int e = 0; e < data.length(); ++e) {
						JSONArray ex = data.getJSONArray(e);
						for (int d = 0; d < ex.length(); d++) {
							if (ex.getString(d).equals(es.getPf())) {
								espe = ex.getString(d);
							}
						}
					}
					for (int e = 0; e < data.length(); ++e) {
						JSONArray ex = data.getJSONArray(e);
						Row dataRow = sheet.createRow(i);
						for (int d = 0; d < ex.length(); d++) {
							if (ex.getString(3).toUpperCase().equals(es.getPf().toUpperCase())) {
								String header = ex.getString(d);
								Cell cell = dataRow.createCell(d);
								cell.setCellStyle(datoStyle);
								cell.setCellValue(header.toString().toUpperCase());
							}
						}
						i++;
					}

				}
				// FIN DE EXCEL

				try {
					UUID uuid = UUID.randomUUID();
					fileStr = "/tmp/" + uuid.toString() + ".xlsx";
					//log.info(fileStr);
					FileOutputStream fileout = new FileOutputStream(fileStr);
					book.write(fileout);
					fileout.close();

					
				} catch (Exception e) {
					// TODO
					//log.error(e.getMessage());
					e.printStackTrace();
				}
				
				return fileStr;
	
	}
}
