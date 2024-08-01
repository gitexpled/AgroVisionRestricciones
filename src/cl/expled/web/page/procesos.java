package cl.expled.web.page;

import java.io.BufferedInputStream;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import lib.db.DiccionarioDB;
import lib.db.MercadoDB;
import lib.db.especieDB;
import lib.db.estadoProductorDB;
import lib.db.exportarSapDB;
import lib.db.informesDB;
import lib.db.mailDB;
import lib.security.session;
import lib.struc.Diccionario;
import lib.struc.Mercado;
import lib.struc.cargaManual;
import lib.struc.especie;
import lib.struc.filterSql;
import lib.struc.mail;
import lib.struc.restriccion;

@Controller
public class procesos {

	// ADMINISTRACION DE FOLIOS CAF
	@RequestMapping(value = "/exportaCvs/{id}", method = { RequestMethod.GET })
	public void exportaCsv(HttpServletResponse response, @PathVariable("id") String id, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			String errorMessage = "Session terminada ";
			OutputStream outputStream;
			try {
				outputStream = response.getOutputStream();
				outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
				outputStream.close();
			} catch (IOException e) {
				// 
				e.printStackTrace();
			}

			return;
		}
		// application/vnd.ms-excel
		String mimeType = "application/octet-stream";
		System.out.println("mimetype : " + mimeType);
		//
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("inline; filename=\"archivo.csv\""));

		ArrayList<String[]> pp = exportarSapDB.view(ses.getIdTemporada());
		Iterator<String[]> rest = pp.iterator();
		String html = "";
		html = "";

		while (rest.hasNext()) {
			String[] array = rest.next();
			String res = Arrays.toString(array).replace("[", "").replace("]", "").replace("null", "").replace(", ", ",").replace(",", ";");
			html += res + "\n";
		}
		OutputStream outputStream;
		try {
			outputStream = response.getOutputStream();
			outputStream.write(html.getBytes(Charset.forName("UTF-8")));
			outputStream.close();
		} catch (IOException e) {
			// 
			e.printStackTrace();
		}

		return;

	}

	// ADMINISTRACION DE FOLIOS CAF
	@RequestMapping(value = "/exportaExcelParcela/{id}", method = { RequestMethod.GET })
	public void exportaExcelParcela(HttpServletResponse response, @PathVariable("id") String id, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			String errorMessage = "Session terminada ";
			OutputStream outputStream;
			try {
				outputStream = response.getOutputStream();
				outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
				outputStream.close();
			} catch (IOException e) {
				// 
				e.printStackTrace();
			}

			return;
		}

		String mimeType = "application/octet-stream";
		System.out.println("mimetype : " + mimeType);
		//
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("inline; filename=\"EstadoProductorParcela_ExcelNormal.xlsx\""));

		// Workbook book=WorkbookFactory.create(in);
		Workbook book = new XSSFWorkbook();
		System.out.println("comienzo excel");

		ArrayList<especie> esp = null;
		try {
			ArrayList<filterSql> filter = new ArrayList<filterSql>();
			esp = especieDB.getAll(filter, "idEspecie", 0, 9999);
		} catch (Exception e) {
			// 
			e.printStackTrace();
		}

		Iterator<especie> ff = esp.iterator();
		while (ff.hasNext()) {
			especie es = ff.next();

			Sheet sheet = book.createSheet(es.getEspecie());
			System.out.println("..");
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

			int i = 0;
			int a = 0;
			int x = 0;
			ArrayList<String[]> pp = estadoProductorDB.getEstadoProductorB(ses.getIdTemporada(), es.getIdEspecie(), "", "","", true);
//			ArrayList<String[]> pp = estadoProductorDB.getEstadoProductorB(ses.getIdTemporada(), es.getIdEspecie(), "", "","","", true, "");
			Iterator<String[]> rest = pp.iterator();

			while (rest.hasNext()) {
				String[] r = rest.next();
				sheet.autoSizeColumn((short) x);
				Row filas = sheet.createRow(a);
				System.out.println(es.getIdEspecie() + "-->" + r.length);
				for (i = 0; i < r.length; i++) {
					try {
						Cell col = filas.createCell(i);

						col.setCellStyle(datoStyle);

						col.setCellValue(r[i].toString().toUpperCase());

						// System.out.println(es.getIdEspecie()+"-->"+r.length+":::"+i);
					} catch (Exception e) {
						
					}
					
				}
				a++;
				x++;
			}

		}
		// FIN DE EXCEL
		UUID uuid = UUID.randomUUID();
		String fileStr = "/tmp/" + uuid.toString() + ".xlsx";

		FileOutputStream fileout = new FileOutputStream(fileStr);
		book.write(fileout);
		fileout.close();

		File file = new File(fileStr);
		FileInputStream fis = new FileInputStream(file);
		FileCopyUtils.copy(fis, response.getOutputStream());
		fis.close();
		System.out.println(file.getAbsolutePath());
		file.delete();

	}

	// ADMINISTRACION DE FOLIOS CAF
	@RequestMapping(value = "/exportaExcelParcelaSapOld/{id}/{tipo}", method = { RequestMethod.GET })
	public void exportaExcelParcelaSapoOld(HttpServletResponse response, @PathVariable("id") String id,@PathVariable("tipo") String tipo, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
//		if (ses.isValid()) {
//			String errorMessage = "Session terminada ";
//			OutputStream outputStream;
//			try {
//				outputStream = response.getOutputStream();
//				outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
//				outputStream.close();
//			} catch (IOException e) {
//				
//				e.printStackTrace();
//			}
//
//			return;
//		}

		String mimeType = "application/octet-stream";
		System.out.println("mimetype : " + mimeType);
		//
		response.setContentType(mimeType);
		String fileName="EstadoProductorParcela_Mercado";
		if (tipo.equals("Y"))
			fileName="EstadoProductorParcela_Cliente";
		response.setHeader("Content-Disposition", String.format("inline; filename=\""+fileName+".xlsx\""));

		// Workbook book=WorkbookFactory.create(in);
		Workbook book = new XSSFWorkbook();
		System.out.println("comienzo excel");

		ArrayList<especie> esp = null;
		try {
			ArrayList<filterSql> filter = new ArrayList<filterSql>();
			esp = especieDB.getAll(filter, "idEspecie", 0, 9999);
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}

		
		Sheet sheet = book.createSheet("SAP");
		System.out.println("..");
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
		Iterator<especie> ff = esp.iterator();
		boolean swTitulos=true;
		int x = 0;
		int a = 0;
		while (ff.hasNext()) {
			especie es = ff.next();

			

			int i = 0;
			
			
			
			ArrayList<String[]> pp = estadoProductorDB.getEstadoProductorC(2, es.getIdEspecie(), "", "",tipo, swTitulos);
			Iterator<String[]> rest = pp.iterator();

			while (rest.hasNext()) {
				String[] r = rest.next();
				sheet.autoSizeColumn((short) x);
				Row filas = sheet.createRow(a);
				System.out.println(es.getIdEspecie() + "-->" + r.length);
				for (i = 0; i < r.length; i++) {
					Cell col = filas.createCell(i);

					col.setCellStyle(datoStyle);

					col.setCellValue(r[i].toString().toUpperCase());

					// System.out.println(es.getIdEspecie()+"-->"+r.length+":::"+i);
				}
				a++;
				x++;
				swTitulos=false;
			}

		}
		// FIN DE EXCEL
		UUID uuid = UUID.randomUUID();
		String fileStr = "/tmp/__" + uuid.toString() + ".xlsx";

		FileOutputStream fileout = new FileOutputStream(fileStr);
		book.write(fileout);
		fileout.close();

		File file = new File(fileStr);
		FileInputStream fis = new FileInputStream(file);
		FileCopyUtils.copy(fis, response.getOutputStream());
		fis.close();
		System.out.println(file.getAbsolutePath());
		file.delete();

	}
	// ADMINISTRACION DE FOLIOS CAF
	@RequestMapping(value = "/exportaExcelParcelaSap/{id}/{tipo}", method = { RequestMethod.GET })
	public void exportaExcelParcelaSap(HttpServletResponse response, @PathVariable("id") String id,@PathVariable("tipo") String tipo, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			String errorMessage = "Session terminada ";
			OutputStream outputStream;
			try {
				outputStream = response.getOutputStream();
				outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
				outputStream.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}

			return;
		}

		String mimeType = "application/octet-stream";
		System.out.println("mimetype : " + mimeType);
		//
		response.setContentType(mimeType);
		String fileName="EstadoProductorParcela_Mercado";
		if (tipo.equals("Y"))
			fileName="EstadoProductorParcela_Cliente";
		response.setHeader("Content-Disposition", String.format("inline; filename=\""+fileName+".xlsx\""));

		// Workbook book=WorkbookFactory.create(in);
		Workbook book = new XSSFWorkbook();
		System.out.println("comienzo excel");

		ArrayList<especie> esp = null;
		try {
			ArrayList<filterSql> filter = new ArrayList<filterSql>();
			esp = especieDB.getAll(filter, "idEspecie", 0, 9999);
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}

		
		Sheet sheet = book.createSheet("SAP");
		System.out.println("..");
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
		Iterator<especie> ff = esp.iterator();
		boolean swTitulos=true;
		int x = 0;
		int a = 0;
		String json = estadoProductorDB.getRestricionesParcelaTurnoExcel(ses.getIdTemporada(), 1, "", "",tipo, swTitulos);
		JSONObject j = new JSONObject(json);
		System.out.println(j);
		JSONArray columns = j.getJSONArray("columns");
		JSONArray data = j.getJSONArray("data");
		int i = 1;
		
		Row fila = sheet.createRow(0);
		for (int e = 0; e < columns.length(); ++e) {
            String header = columns.getString(e);
            Cell cell = fila.createCell(e);
            cell.setCellStyle(datoStyle);
            cell.setCellValue(header.toString().toUpperCase());
        }
		for (int e = 0; e < data.length(); ++e) {
			JSONArray ex = data.getJSONArray(e);
        	Row dataRow = sheet.createRow(i);
			for(int d = 0; d < ex.length(); d++){
				String header = String.valueOf(ex.get(d));
	            Cell cell = dataRow.createCell(d);
	            cell.setCellStyle(datoStyle);
	            cell.setCellValue(header.toString().toUpperCase());
			}
            i++;
        }
//		while (ff.hasNext()) {
//			especie es = ff.next();
//
//			
//
//			
//			
////			ArrayList<String[]> pp = estadoProductorDB.getEstadoProductorC(ses.getIdTemporada(), es.getIdEspecie(), "", "",tipo, swTitulos);
////			Iterator<String[]> rest = pp.iterator();
////
////			while (rest.hasNext()) {
////				String[] r = rest.next();
////				sheet.autoSizeColumn((short) x);
////				Row filas = sheet.createRow(a);
////				System.out.println(es.getIdEspecie() + "-->" + r.length);
////				for (i = 0; i < r.length; i++) {
////					Cell col = filas.createCell(i);
////
////					col.setCellStyle(datoStyle);
////
////					col.setCellValue(r[i].toString().toUpperCase());
////
////					// System.out.println(es.getIdEspecie()+"-->"+r.length+":::"+i);
////				}
////				a++;
////				x++;
////				swTitulos=false;
////			}
//
//		}
		// FIN DE EXCEL
		UUID uuid = UUID.randomUUID();
		String fileStr = "/tmp/__" + uuid.toString() + ".xlsx";

		FileOutputStream fileout = new FileOutputStream(fileStr);
		book.write(fileout);
		fileout.close();

		File file = new File(fileStr);
		FileInputStream fis = new FileInputStream(file);
		FileCopyUtils.copy(fis, response.getOutputStream());
		fis.close();
		System.out.println(file.getAbsolutePath());
		file.delete();

	}
	// ADMINISTRACION DE FOLIOS CAF
	@RequestMapping(value = "/exportaExcelOld/{id}", method = { RequestMethod.GET })
	public void exportaExcelOl(HttpServletResponse response, @PathVariable("id") String id, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			String errorMessage = "Session terminada ";
			OutputStream outputStream;
			try {
				outputStream = response.getOutputStream();
				outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return;
		}

		String mimeType = "application/octet-stream";
		System.out.println("mimetype : " + mimeType);
		//
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("inline; filename=\"archivo.xlsx\""));

		// Workbook book=WorkbookFactory.create(in);
		Workbook book = new XSSFWorkbook();
		System.out.println("comienzo excel");

		ArrayList<especie> esp = null;
		try {
			ArrayList<filterSql> filter = new ArrayList<filterSql>();
			esp = especieDB.getAll(filter, "idEspecie", 0, 9999);
		} catch (Exception e) {
			// 
			e.printStackTrace();
		}

		Iterator<especie> ff = esp.iterator();
		while (ff.hasNext()) {
			especie es = ff.next();
			ArrayList<String> tipoMercado = new ArrayList<String>();
			
				tipoMercado.add("N");
				tipoMercado.add("Y");
				for (String tipoM : tipoMercado) {
					String cliente = "";
					if (tipoM.equals("Y"))
						cliente = " - CLIENTE";
					
					Sheet sheet = book.createSheet(es.getEspecie() + cliente);
					System.out.println("..");
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

					int i = 0;
					int a = 0;
					int x = 0;
					//ArrayList<String[]> pp = estadoProductorDB.getEstadoProductorA(ses.getIdTemporada(), es.getIdEspecie(), "", "","","", true, tipoM);
					ArrayList<String[]> pp = estadoProductorDB.getEstadoProductorB(ses.getIdTemporada(), es.getIdEspecie(), "", "","", true);
					Iterator<String[]> rest = pp.iterator();

					while (rest.hasNext()) {
						String[] r = rest.next();
						sheet.autoSizeColumn((short) x);
						Row filas = sheet.createRow(a);
//						System.out.println(es.getIdEspecie() + "-->" + r.length);
						for (i = 0; i < r.length; i++) {
							try {
								Cell col = filas.createCell(i);

								col.setCellStyle(datoStyle);

								col.setCellValue(r[i].toString().toUpperCase());

							} catch (Exception e) {
								
								System.out.println(e.getMessage());
								e.printStackTrace();
							}
							// System.out.println(es.getIdEspecie()+"-->"+r.length+":::"+i);
						}
						a++;
						x++;
					}

				} // FIN FOREACH
			

		}
		// FIN DE EXCEL
		
		try {
			UUID uuid = UUID.randomUUID();
			String fileStr = "/tmp/" + uuid.toString() + ".xlsx";
	
			FileOutputStream fileout = new FileOutputStream(fileStr);
			book.write(fileout);
			fileout.close();
	
			File file = new File(fileStr);
			FileInputStream fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, response.getOutputStream());
			fis.close();
			System.out.println(file.getAbsolutePath());
			file.delete();
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	// ADMINISTRACION DE FOLIOS CAF
	@RequestMapping(value = "/exportaExcel/{id}", method = { RequestMethod.GET })
	public void exportaExcel(HttpServletResponse response, @PathVariable("id") String id, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			String errorMessage = "Session terminada ";
			OutputStream outputStream;
			try {
				outputStream = response.getOutputStream();
				outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
				outputStream.close();
			} catch (IOException e) {
				// 
				e.printStackTrace();
			}

			return;
		}

		String mimeType = "application/octet-stream";
		System.out.println("mimetype : " + mimeType);
		//
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("inline; filename=\"archivo.xlsx\""));

		// Workbook book=WorkbookFactory.create(in);
		Workbook book = new XSSFWorkbook();
		System.out.println("comienzo excel");

		ArrayList<especie> esp = null;
		try {
			ArrayList<filterSql> filter = new ArrayList<filterSql>();
			esp = especieDB.getAll(filter, "idEspecie", 0, 9999);
		} catch (Exception e) {
			// 
			e.printStackTrace();
		}
		String json = estadoProductorDB.getRestriccionesExcel(ses.getIdTemporada(), 1, "", "","", true);
		System.out.println(ses.getIdTemporada());
		JSONObject j = new JSONObject(json);
		System.out.println(j);
		JSONArray columns = j.getJSONArray("columns");
		JSONArray data = j.getJSONArray("data");
		Iterator<especie> ff = esp.iterator();
		while (ff.hasNext()) {
			especie es = ff.next();
			ArrayList<String> tipoMercado = new ArrayList<String>();
			
				tipoMercado.add("N");
				tipoMercado.add("Y");
				for (String tipoM : tipoMercado) {
					String cliente = "";
					if (tipoM.equals("Y"))
						cliente = " - CLIENTE";
					
					Sheet sheet = book.createSheet(es.getEspecie() + cliente);
					System.out.println("..");
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
						for(int d = 0; d < ex.length(); d++){
							if(ex.getString(d).equals(es.getPf())){
								espe = ex.getString(d);
							}
						}
			        }
					for (int e = 0; e < data.length(); ++e) {
						JSONArray ex = data.getJSONArray(e);
			        	Row dataRow = sheet.createRow(i);
						for(int d = 0; d < ex.length(); d++){
							if(ex.getString(2).toUpperCase().equals(es.getPf().toUpperCase())){
								String header = ex.getString(d);
					            Cell cell = dataRow.createCell(d);
					            cell.setCellStyle(datoStyle);
					            cell.setCellValue(header.toString().toUpperCase());
							}
						}
			            i++;
			        }
				} // FIN FOREACH
			

		}
		// FIN DE EXCEL
		
		try {
			UUID uuid = UUID.randomUUID();
			String fileStr = "/tmp/" + uuid.toString() + ".xlsx";
	
			FileOutputStream fileout = new FileOutputStream(fileStr);
			book.write(fileout);
			fileout.close();
	
			File file = new File(fileStr);
			FileInputStream fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, response.getOutputStream());
			fis.close();
			System.out.println(file.getAbsolutePath());
			file.delete();
		} catch (Exception e) {
			// TODO
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/caExcel/{id}", method = { RequestMethod.GET })
	public void getExcel(HttpServletResponse response, @PathVariable("id") String id, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			String errorMessage = "Session terminada ";
			OutputStream outputStream;
			try {
				outputStream = response.getOutputStream();
				outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
				outputStream.close();
			} catch (IOException e) {
				// 
				e.printStackTrace();
			}

			return;
		}

		String mimeType = "application/octet-stream";
		System.out.println("mimetype : " + mimeType);
		//

		/*
		 * "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting
		 */
		// response.setHeader("Content-Disposition", String.format("attachment;
		// filename=\"%s\"", file.getName()));

		// response.setContentLength((int) file.length());
		mail archivos = mailDB.getMailFile(id);
		InputStream inputStream = archivos.getFile();
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + archivos.getArchivo() + "\""));

		// Copy bytes from source to destination(outputstream in this example),
		// closes both streams.
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();

	}

	@RequestMapping(value = "/caData/{id}", method = { RequestMethod.GET })
	public void getData(HttpServletResponse response, @PathVariable("id") String id, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			String errorMessage = "Session terminada ";
			OutputStream outputStream;
			try {
				outputStream = response.getOutputStream();
				outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
				outputStream.close();
			} catch (IOException e) {
				// 
				e.printStackTrace();
			}

			return;
		}
		/*
		 * mail archivos = mailDB.getMailFile(id); InputStream inputStream = archivos.getFile();
		 * 
		 * Workbook workbook = new XSSFWorkbook(inputStream); Sheet datatypeSheet = workbook.getSheetAt(0); Iterator<Row> iterator = datatypeSheet.iterator(); String errorMessage =
		 * "<table border=1> "; errorMessage +=
		 * "<tr><td>especie</td><td>codigo</td><td>productor</td><td>fecha ingreso</td><td>fecha muestra</td><td>fecha emision</td><td>resultado</td> </tr>" ; while (iterator.hasNext()) {
		 * errorMessage += "<tr> "; Row currentRow = iterator.next(); Iterator<Cell> cellIterator = currentRow.iterator();
		 * 
		 * errorMessage += "<td>"+currentRow.getCell(5)+"</td>"; errorMessage += "<td>"+currentRow.getCell(8)+"</td>"; errorMessage += "<td>"+currentRow.getCell(9)+"</td>"; errorMessage +=
		 * "<td>"+currentRow.getCell(10)+"</td>"; errorMessage += "<td>"+currentRow.getCell(11)+"</td>"; errorMessage += "<td>"+currentRow.getCell(12)+"</td>"; errorMessage +=
		 * "<td>"+currentRow.getCell(19)+"</td>"; // errorMessage += "<td>"+currentRow.getCell(9)+"</td>";
		 * 
		 * 
		 * errorMessage += "</tr> "; } errorMessage += "</table> "; System.out.println();
		 * 
		 * 
		 */
		String errorMessage = informesDB.getDetalleExcel(id);

		OutputStream outputStream2;
		try {
			outputStream2 = response.getOutputStream();
			outputStream2.write(errorMessage.getBytes(Charset.forName("iso-8859-1")));
			outputStream2.close();
		} catch (IOException e) {
			// 
			e.printStackTrace();
		}

		return;

	}

	@RequestMapping(value = "/test2/{id}", method = { RequestMethod.GET })
	public void test(HttpServletResponse response, @PathVariable("id") String id, HttpSession httpSession) throws InvalidFormatException, IOException {
		long startTime = System.currentTimeMillis();

		try {
			FileInputStream fis = new FileInputStream("/eDte/hola.docx");
			XWPFDocument document = new XWPFDocument(OPCPackage.open(fis));
			// XWPFDocument document = new XWPFDocument( Data.class.getResourceAsStream(
			// "DocxStructures.docx" ) );

			// 2) Convert POI XWPFDocument 2 PDF with iText
			File outFile = new File("/eDte/DocxStructures.pdf");
			outFile.getParentFile().mkdirs();

			OutputStream out = new FileOutputStream(outFile);
			PdfOptions options = null;// PDFViaITextOptions.create().fontEncoding( "windows-1250" );
			PdfConverter.getInstance().convert(document, out, options);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		System.out.println("Generate DocxStructures.pdf with " + (System.currentTimeMillis() - startTime) + " ms.");
	}

	@RequestMapping("/adm/proceso_cargaManual")
	public ModelAndView estadoProductorContent(Model model, HttpSession httpSession) {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		ArrayList<Diccionario> mer = null;
		try {

			mer = DiccionarioDB.getSelect();
		} catch (Exception e) {
			// 
			e.printStackTrace();
		}

		// ModelAndView model2 = new ModelAndView("content/informe/estadoProductor");
		// model2.addObject("th",mer);
		model.addAttribute("optionDiccionario", mer);

		return new ModelAndView("content/proceso/cargaManual");
	}

	@RequestMapping("/adm/proceso_cargaManual2")
	public ModelAndView estadoProductorContent2(Model model, HttpSession httpSession) {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		ArrayList<Diccionario> mer = null;
		try {

			mer = DiccionarioDB.getSelect();
		} catch (Exception e) {
			// 
			e.printStackTrace();
		}

		// ModelAndView model2 = new ModelAndView("content/informe/estadoProductor");
		// model2.addObject("th",mer);
		model.addAttribute("optionDiccionario", mer);

		return new ModelAndView("content/proceso/cargaManual2");
	}

	@RequestMapping(value = "/exportaExcelDB/{id}", method = { RequestMethod.GET })
	public void exportaExcelDB(HttpServletResponse response, @PathVariable("id") String id, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			String errorMessage = "Session terminada ";
			OutputStream outputStream;
			try {
				outputStream = response.getOutputStream();
				outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
				outputStream.close();
			} catch (IOException e) {
				// 
				e.printStackTrace();
			}

			return;
		}

		String mimeType = "application/octet-stream";
		System.out.println("mimetype : " + mimeType);
		//
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("inline; filename=\"EstadoProductorTurno.xlsx\""));

		// Workbook book=WorkbookFactory.create(in);
		Workbook book = new XSSFWorkbook();
		System.out.println("comienzo excel");

		Sheet sheet = book.createSheet("DATA");
		System.out.println("..");
		CellStyle tituloEstilo = book.createCellStyle();
		tituloEstilo.setAlignment(HorizontalAlignment.CENTER);
		tituloEstilo.setVerticalAlignment(VerticalAlignment.CENTER);
		Font fuenteTitulo = book.createFont();
		fuenteTitulo.setBold(true);
		fuenteTitulo.setFontHeightInPoints((short) 14);
		tituloEstilo.setFont(fuenteTitulo);

		Row filaTitulo = sheet.createRow(0);
		Cell celdaTitulo = filaTitulo.createCell(0);
		celdaTitulo.setCellStyle(tituloEstilo);
		celdaTitulo.setCellValue("DB INFORMES");
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 15));

		mail dd = mailDB.getMailMax();
		Row filaTitulo2 = sheet.createRow(2);
		Cell celdaTitulo2 = filaTitulo2.createCell(0);
		// celdaTitulo2.setCellStyle(tituloEstilo2);
		celdaTitulo2.setCellValue("Fecha " + dd.getFechaCarga());

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

		Row filaEncabezado = sheet.createRow(3);

		Cell celdaEncabezado0 = filaEncabezado.createCell(0);
		celdaEncabezado0.setCellStyle(headerStyle);
		celdaEncabezado0.setCellValue("COD. PRODUCTOR");
		
		Cell celdaEncabezado8 = filaEncabezado.createCell(1);
		celdaEncabezado8.setCellStyle(headerStyle);
		celdaEncabezado8.setCellValue("COD. PARCELA");
		
		Cell celdaEncabezado9 = filaEncabezado.createCell(2);
		celdaEncabezado9.setCellStyle(headerStyle);
		celdaEncabezado9.setCellValue("COD. TURNO");

		Cell celdaEncabezado1 = filaEncabezado.createCell(3);
		celdaEncabezado1.setCellStyle(headerStyle);
		celdaEncabezado1.setCellValue("PRODUCTOR");

		Cell celdaEncabezado10 = filaEncabezado.createCell(4);
		celdaEncabezado10.setCellStyle(headerStyle);
		celdaEncabezado10.setCellValue("FECHA INGRESO");
		
		Cell celdaEncabezado11 = filaEncabezado.createCell(5);
		celdaEncabezado11.setCellStyle(headerStyle);
		celdaEncabezado11.setCellValue("FECHA CARGA");

		Cell celdaEncabezado2 = filaEncabezado.createCell(6);
		celdaEncabezado2.setCellStyle(headerStyle);
		celdaEncabezado2.setCellValue("INGR. ACTIVO");

		Cell celdaEncabezado3 = filaEncabezado.createCell(7);
		celdaEncabezado3.setCellStyle(headerStyle);
		celdaEncabezado3.setCellValue("RESULTADO");

		Cell celdaEncabezado4 = filaEncabezado.createCell(8);
		celdaEncabezado4.setCellStyle(headerStyle);
		celdaEncabezado4.setCellValue("VARIEDAD");

		Cell celdaEncabezado5 = filaEncabezado.createCell(9);
		celdaEncabezado5.setCellStyle(headerStyle);
		celdaEncabezado5.setCellValue("ESPECIE");

		Cell celdaEncabezado6 = filaEncabezado.createCell(10);
		celdaEncabezado6.setCellStyle(headerStyle);
		celdaEncabezado6.setCellValue("MUESTRA");

		Cell celdaEncabezado7 = filaEncabezado.createCell(11);
		celdaEncabezado7.setCellStyle(headerStyle);
		celdaEncabezado7.setCellValue("AUTOMATICA");

		sheet.setAutoFilter(CellRangeAddress.valueOf("A4:I4"));

		// INGRESAMOS LA DATA DEL EXCEL
		CellStyle datoStyle = book.createCellStyle();
		datoStyle.setBorderBottom(BorderStyle.THIN);
		datoStyle.setBorderLeft(BorderStyle.THIN);
		datoStyle.setBorderRight(BorderStyle.THIN);
		datoStyle.setBorderTop(BorderStyle.THIN);

		CellStyle siStyle = book.createCellStyle();
		siStyle.setBorderBottom(BorderStyle.THIN);
		siStyle.setBorderLeft(BorderStyle.THIN);
		siStyle.setBorderRight(BorderStyle.THIN);
		siStyle.setBorderTop(BorderStyle.THIN);

		Font font2 = book.createFont();
		font2.setFontName("Arial");

		font2.setColor(IndexedColors.BLUE.getIndex());
		siStyle.setFont(font2);

		CellStyle noStyle = book.createCellStyle();
		noStyle.setBorderBottom(BorderStyle.THIN);
		noStyle.setBorderLeft(BorderStyle.THIN);
		noStyle.setBorderRight(BorderStyle.THIN);
		noStyle.setBorderTop(BorderStyle.THIN);

		Font font3 = book.createFont();
		font3.setFontName("Arial");

		font3.setColor(IndexedColors.RED.getIndex());
		noStyle.setFont(font3);
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		ArrayList<restriccion> datas;
		try {
			datas = estadoProductorDB.getAllRest(filter, "", 0, 99999);
			Iterator<restriccion> f = datas.iterator();

			int a = 4;
			int x = 0;
			while (f.hasNext()) {
				restriccion row = f.next();
				String[] r = { row.getCodProductor(), row.getCodParcela(), row.getCodTurno() ,row.getNomProductor(), row.getFecha(),row.getFechaCarga() ,row.getCodProducto(), row.getLimite(), (row.getVariedad() + "").replace("null", ""), row.getEspecie(), row.getnMuestra(),
						row.getAutomatica() };

				sheet.autoSizeColumn((short) x);
				Row filas = sheet.createRow(a);
				// System.out.println(es.getIdEspecie()+"-->"+r.length);
				int i = 0;
				for (i = 0; i < r.length; i++) {
					Cell col = filas.createCell(i);

					col.setCellStyle(datoStyle);
					col.setCellValue(r[i].toString());

					// System.out.println(a+"-->"+r[i].toString()+":::"+i);
				}
				a++;
				x++;

			}

		} catch (Exception e) {
			// 
			e.printStackTrace();
		}
		// FIN DE EXCEL

		FileOutputStream fileout = new FileOutputStream("/eDte/hf/Reporte.xlsx");
		book.write(fileout);
		fileout.close();

		File file = new File("/eDte/hf/Reporte.xlsx");
		FileInputStream fis = new FileInputStream(file);
		FileCopyUtils.copy(fis, response.getOutputStream());
		fis.close();

	}

	@RequestMapping(value = "/exportaExcelProduccion/{id}", method = { RequestMethod.GET })
	public void exportaExcelProduccion(HttpServletResponse response, @PathVariable("id") String id, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			String errorMessage = "Session terminada ";
			OutputStream outputStream;
			try {
				outputStream = response.getOutputStream();
				outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
				outputStream.close();
			} catch (IOException e) {
				// 
				e.printStackTrace();
			}

			return;
		}

		String mimeType = "application/octet-stream";
		System.out.println("mimetype : " + mimeType);
		//
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("inline; filename=\"estado_productor_producion.xlsx\""));

		// Workbook book=WorkbookFactory.create(in);
		Workbook book = new XSSFWorkbook();
		System.out.println("comienzo excel");

		ArrayList<especie> esp = null;
		try {
			ArrayList<filterSql> filter = new ArrayList<filterSql>();
			esp = especieDB.getAll(filter, "idEspecie", 0, 9999);
		} catch (Exception e) {
			// 
			e.printStackTrace();
		}

		Iterator<especie> ff = esp.iterator();
		while (ff.hasNext()) {
			especie es = ff.next();

			Sheet sheet = book.createSheet(es.getEspecie());
			System.out.println("..");
			CellStyle tituloEstilo = book.createCellStyle();
			tituloEstilo.setAlignment(HorizontalAlignment.CENTER);
			tituloEstilo.setVerticalAlignment(VerticalAlignment.CENTER);
			Font fuenteTitulo = book.createFont();
			fuenteTitulo.setBold(true);
			fuenteTitulo.setFontHeightInPoints((short) 14);
			tituloEstilo.setFont(fuenteTitulo);

			Row filaTitulo = sheet.createRow(0);
			Cell celdaTitulo = filaTitulo.createCell(0);
			celdaTitulo.setCellStyle(tituloEstilo);
			celdaTitulo.setCellValue("Habilitación ".toUpperCase() + es.getEspecie());
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 15));

			mail dd = mailDB.getMailMax();
			Row filaTitulo2 = sheet.createRow(2);
			Cell celdaTitulo2 = filaTitulo2.createCell(0);
			// celdaTitulo2.setCellStyle(tituloEstilo2);
			celdaTitulo2.setCellValue("Fecha " + dd.getFechaCarga());

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

			Row filaEncabezado = sheet.createRow(3);

			Cell celdaEncabezado0 = filaEncabezado.createCell(0);
			celdaEncabezado0.setCellStyle(headerStyle);
			celdaEncabezado0.setCellValue("COD. PRODUCTOR");

			Cell celdaEncabezado1 = filaEncabezado.createCell(1);
			celdaEncabezado1.setCellStyle(headerStyle);
			celdaEncabezado1.setCellValue("PRODUCTOR");

			Cell celdaEncabezado2 = filaEncabezado.createCell(2);
			celdaEncabezado2.setCellStyle(headerStyle);
			celdaEncabezado2.setCellValue("GGN");

			ArrayList<Mercado> mer = null;
			try {
				ArrayList<filterSql> filter = new ArrayList<filterSql>();
				mer = MercadoDB.getMercado(filter, "idMercado", 0, 9999);
			} catch (Exception e) {
				// 
				e.printStackTrace();
			}

			Iterator<Mercado> f = mer.iterator();
			int i = 3;
			while (f.hasNext()) {
				Mercado r = f.next();
				Cell celdaEncabezado = filaEncabezado.createCell(i);
				celdaEncabezado.setCellStyle(headerStyle);
				celdaEncabezado.setCellValue(r.getMercado().toUpperCase());
				++i;
			}

			// INGRESAMOS LA DATA DEL EXCEL
			CellStyle datoStyle = book.createCellStyle();
			datoStyle.setBorderBottom(BorderStyle.THIN);
			datoStyle.setBorderLeft(BorderStyle.THIN);
			datoStyle.setBorderRight(BorderStyle.THIN);
			datoStyle.setBorderTop(BorderStyle.THIN);

			CellStyle siStyle = book.createCellStyle();
			siStyle.setBorderBottom(BorderStyle.THIN);
			siStyle.setBorderLeft(BorderStyle.THIN);
			siStyle.setBorderRight(BorderStyle.THIN);
			siStyle.setBorderTop(BorderStyle.THIN);

			Font font2 = book.createFont();
			font2.setFontName("Arial");

			font2.setColor(IndexedColors.BLUE.getIndex());
			siStyle.setFont(font2);

			CellStyle noStyle = book.createCellStyle();
			noStyle.setBorderBottom(BorderStyle.THIN);
			noStyle.setBorderLeft(BorderStyle.THIN);
			noStyle.setBorderRight(BorderStyle.THIN);
			noStyle.setBorderTop(BorderStyle.THIN);

			Font font3 = book.createFont();
			font3.setFontName("Arial");

			font3.setColor(IndexedColors.RED.getIndex());
			noStyle.setFont(font3);

			int a = 4;
			int x = 0;
			ArrayList<String[]> pp = estadoProductorDB.getEstadoProductorPro(ses.getIdTemporada(), es.getIdEspecie(), "", "");
			Iterator<String[]> rest = pp.iterator();

			while (rest.hasNext()) {
				String[] r = rest.next();
				sheet.autoSizeColumn((short) x);
				Row filas = sheet.createRow(a);
				System.out.println(es.getIdEspecie() + "-->" + r.length);
				for (i = 0; i < r.length; i++) {
					Cell col = filas.createCell(i);

					if (r[i].toString().equals("Y")) {
						col.setCellStyle(siStyle);
						col.setCellValue("SI");
					} else if (r[i].toString().equals("N")) {
						col.setCellStyle(noStyle);
						col.setCellValue("NO");
					} else {
						col.setCellStyle(datoStyle);
						col.setCellValue(r[i].toString());
					}

					System.out.println(es.getIdEspecie() + "-->" + r.length + ":::" + i);
				}
				a++;
				x++;
			}

		}
		// FIN DE EXCEL

		FileOutputStream fileout = new FileOutputStream("/eDte/hf/Reporte.xlsx");
		book.write(fileout);
		fileout.close();

		File file = new File("/eDte/hf/Reporte.xlsx");
		FileInputStream fis = new FileInputStream(file);
		FileCopyUtils.copy(fis, response.getOutputStream());
		fis.close();
	}
}
