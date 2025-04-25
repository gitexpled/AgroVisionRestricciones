package lib.db;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.IconMultiStateFormatting.IconSet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lib.struc.especie;
import lib.struc.jerarquia;

public class excel {
    private static class Styles {
        final XSSFCellStyle header, base, check, cross;
        Styles(XSSFCellStyle h, XSSFCellStyle b, XSSFCellStyle c1, XSSFCellStyle c2) {
            header = h; base = b; check = c1; cross = c2;
        }
    }

    private void borders(XSSFCellStyle s) {
        s.setBorderTop(BorderStyle.THIN);
        s.setBorderBottom(BorderStyle.THIN);
        s.setBorderLeft(BorderStyle.THIN);
        s.setBorderRight(BorderStyle.THIN);
    }

    private XSSFColor rgb(int r, int g, int b) {
        byte[] arr = { (byte) r, (byte) g, (byte) b };
        try { return new XSSFColor(arr, null); }
        catch (Throwable t) { return new XSSFColor(arr); }
    }

    private Styles buildStyles(XSSFWorkbook wb) {
    	XSSFColor blue  = rgb(  0, 112, 192); 
    	XSSFColor green = rgb(  0, 176,  80);
    	XSSFColor red   = rgb(192,   0,   0);
    	XSSFColor white = rgb(255, 255, 255);
        XSSFCellStyle header = wb.createCellStyle();
        header.setFillForegroundColor(blue);
        header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        borders(header);
        XSSFFont hFont = wb.createFont();
        hFont.setBold(true); hFont.setFontName("Arial"); hFont.setColor(white);
        header.setFont(hFont);

        XSSFCellStyle base = wb.createCellStyle(); borders(base);
        XSSFCellStyle check = wb.createCellStyle(); check.cloneStyleFrom(base);
        XSSFFont gFont = wb.createFont(); gFont.setColor(green); check.setFont(gFont);
        XSSFCellStyle cross = wb.createCellStyle(); cross.cloneStyleFrom(base);
        XSSFFont rFont = wb.createFont(); rFont.setColor(red); cross.setFont(rFont);

        return new Styles(header, base, check, cross);
    }
    private void writeRow(Row row, JSONArray jsonRow, Styles st, XSSFWorkbook wb) {

        for (int c = 0; c < jsonRow.length(); c++) {
            String v = jsonRow.getString(c).trim().toUpperCase();
            Cell cell = row.createCell(c);

            switch (v) {
                case "SI": case "SI.":
                    cell.setCellValue("✔️"); cell.setCellStyle(st.check); break;
                case "NO": case "NO.":
                    cell.setCellValue("❌"); cell.setCellStyle(st.cross); break;
                case "PI": case "PI.":
                    XSSFRichTextString rt = new XSSFRichTextString("PI❗");
                    XSSFFont bold = wb.createFont(); bold.setBold(true);
                    rt.applyFont(0, 2, bold);
                    XSSFFont yell = wb.createFont();
                    yell.setColor(rgb(255, 192, 0)); yell.setBold(true);
                    rt.applyFont(2, 3, yell);
                    cell.setCellValue(rt);
                    cell.setCellStyle(st.base);
                    break;
                default:
                    cell.setCellValue(v); cell.setCellStyle(st.base); break;
            }
        }
    }
    public String createExcel() throws Exception {

        XSSFWorkbook wb = new XSSFWorkbook();
        Styles st = buildStyles(wb);

        List<especie> especies = new especieDB().getAllExcel();

        for (especie es : especies) {

            estadoProductorNewDB d = new estadoProductorNewDB();
            String json = d.getRestriccionesExcel(0, es.getIdEspecie(),"","","","", "", true);
            JSONObject o = new JSONObject(json);
            JSONArray cols = o.getJSONArray("columns");
            JSONArray data = o.getJSONArray("data");

            Sheet sh = wb.createSheet(es.getEspecie());
            Row head = sh.createRow(0);
            for (int c = 0; c < cols.length(); c++) {
                Cell cell = head.createCell(c);
                cell.setCellStyle(st.header);
                cell.setCellValue(cols.getString(c).toUpperCase());
            }
            int rowIdx = 1;
            for (int r = 0; r < data.length(); r++) {
                JSONArray jRow = data.getJSONArray(r);
                if (!jRow.getString(3).equalsIgnoreCase(es.getPf())) continue;
                Row row = sh.createRow(rowIdx++);
                writeRow(row, jRow, st, wb);
            }
            SheetConditionalFormatting cf = sh.getSheetConditionalFormatting();
            CellRangeAddress[] rg = { CellRangeAddress.valueOf("I2:DA500") };
            ConditionalFormattingRule rule = cf.createConditionalFormattingRule(IconSet.GREY_3_ARROWS);
            rule.getMultiStateFormatting().setIconOnly(true);
            cf.addConditionalFormatting(rg, rule);
        }
        String path = "/tmp/" + UUID.randomUUID() + ".xlsx";
        try (FileOutputStream out = new FileOutputStream(path)) { wb.write(out); }
        wb.close();
        return path;
    }
    
    public String createExcelResumen(String fecha) throws Exception {

        XSSFWorkbook wb = new XSSFWorkbook();
        Styles st = buildStyles(wb);

        List<especie> especies = new especieDB().getAllExcel();
        estadoProductorNewDB d = new estadoProductorNewDB();
        ArrayList<jerarquia> jerarquias = d.getCambios(fecha);
        
        String json = "";
        
        for (especie es : especies) {
        	
        	boolean cambio = false;
	        for (jerarquia jr : jerarquias) {
	        	if(es.getEspecie().equals(jr.getEspecie()) || jr.getEspecie().equals(""))
	        	{
	        		cambio = true;
	        		json = d.getRestriccionesExcel(0, es.getIdEspecie(),"","","","", "", true);
	        		//System.out.println(json);
	        	}
	        	
	        	
	        }
	        
	        if(cambio) {  
            
	            JSONObject o = new JSONObject(json);
	            JSONArray cols = o.getJSONArray("columns");
	            JSONArray data = o.getJSONArray("data");
	
	            Sheet sh = wb.createSheet(es.getEspecie());
	            Row head = sh.createRow(0);
	            for (int c = 0; c < cols.length(); c++) {
	                Cell cell = head.createCell(c);
	                cell.setCellStyle(st.header);
	                cell.setCellValue(cols.getString(c).toUpperCase());
	            }
	            int rowIdx = 1;
	            for (int r = 0; r < data.length(); r++) {
	                JSONArray jRow = data.getJSONArray(r);
	                boolean cambio2 = false;
			        for (jerarquia jr : jerarquias) {
			        	if(
			        			(es.getEspecie().equals(jr.getEspecie()) || jr.getEspecie().equals("")) &&
			        			(jRow.getString(4).trim().toUpperCase().equals(jr.getEtapa()) || jr.getEtapa().equals("")) &&
			        			(jRow.getString(5).trim().toUpperCase().equals(jr.getCampo()) || jr.getCampo().equals("")) &&
			        			(jRow.getString(6).trim().toUpperCase().equals(jr.getVariedad()) || jr.getVariedad().equals("")) 
			        	)
			        	{
			        		cambio2 = true;
			        		//System.out.println(row.getString(4).trim().toUpperCase());
			        	}
			        	
			        	
			        }
			        
			        if(cambio2) {
			        	if (!jRow.getString(3).equalsIgnoreCase(es.getPf())) continue;
		                Row row = sh.createRow(rowIdx++);
		                writeRow(row, jRow, st, wb);
			        }
	                
	            }
	            SheetConditionalFormatting cf = sh.getSheetConditionalFormatting();
	            CellRangeAddress[] rg = { CellRangeAddress.valueOf("I2:DA500") };
	            ConditionalFormattingRule rule = cf.createConditionalFormattingRule(IconSet.GREY_3_ARROWS);
	            rule.getMultiStateFormatting().setIconOnly(true);
	            cf.addConditionalFormatting(rg, rule);
	        }
        }
        String path = "/tmp/" + UUID.randomUUID() + ".xlsx";
        try (FileOutputStream out = new FileOutputStream(path)) { wb.write(out); }
        wb.close();
        return path;
    }
}
