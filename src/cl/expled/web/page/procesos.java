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

import org.apache.poi.hssf.record.cf.Threshold;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
import org.apache.poi.ss.usermodel.ConditionalFormattingThreshold;
import org.apache.poi.ss.usermodel.ConditionalFormattingThreshold.RangeType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IconMultiStateFormatting;
import org.apache.poi.ss.usermodel.IconMultiStateFormatting.IconSet;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
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
import lib.db.estadoProductorNewDB;
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
	public void exportaCsv(HttpServletResponse response, @PathVariable("id") String id, HttpSession httpSession)
			throws Exception {
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
			String res = Arrays.toString(array).replace("[", "").replace("]", "").replace("null", "").replace(", ", ",")
					.replace(",", ";");
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
	public void exportaExcelParcela(HttpServletResponse response, @PathVariable("id") String id,
			HttpSession httpSession) throws Exception {
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
		response.setHeader("Content-Disposition",
				String.format("inline; filename=\"EstadoProductorParcela_ExcelNormal.xlsx\""));

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
			ArrayList<String[]> pp = estadoProductorDB.getEstadoProductorB(ses.getIdTemporada(), es.getIdEspecie(), "","", "", true);

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
	public void exportaExcelParcelaSapoOld(HttpServletResponse response, @PathVariable("id") String id,
			@PathVariable("tipo") String tipo, HttpSession httpSession) throws Exception {
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
		String fileName = "EstadoProductorParcela_Mercado";
		if (tipo.equals("Y"))
			fileName = "EstadoProductorParcela_Cliente";
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + fileName + ".xlsx\""));

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
		boolean swTitulos = true;
		int x = 0;
		int a = 0;
		while (ff.hasNext()) {
			especie es = ff.next();

			int i = 0;

			ArrayList<String[]> pp = estadoProductorDB.getEstadoProductorC(2, es.getIdEspecie(), "", "", tipo,
					swTitulos);
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
				swTitulos = false;
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
	public void exportaExcelParcelaSap(HttpServletResponse response, @PathVariable("id") String id,
			@PathVariable("tipo") String tipo, HttpSession httpSession) throws Exception {
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
		String fileName = "EstadoProductorParcela_Mercado";
		if (tipo.equals("Y"))
			fileName = "EstadoProductorParcela_Cliente";
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + fileName + ".xlsx\""));

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
		boolean swTitulos = true;
		int x = 0;
		int a = 0;
		estadoProductorNewDB dataDB = new estadoProductorNewDB();
		String json = dataDB.getRestricionesParcelaTurnoExcel(ses.getIdTemporada(), 7, "", "", tipo, swTitulos);
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
			for (int d = 0; d < ex.length(); d++) {
				String header = String.valueOf(ex.get(d));
				Cell cell = dataRow.createCell(d);
				cell.setCellStyle(datoStyle);
				cell.setCellValue(header.toString().toUpperCase());
			}
			i++;
		}

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
	public void exportaExcelOl(HttpServletResponse response, @PathVariable("id") String id, HttpSession httpSession)
			throws Exception {
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
				// ArrayList<String[]> pp =
				// estadoProductorDB.getEstadoProductorA(ses.getIdTemporada(),
				// es.getIdEspecie(), "", "","","", true, tipoM);
				ArrayList<String[]> pp = estadoProductorDB.getEstadoProductorB(ses.getIdTemporada(), es.getIdEspecie(),
						"", "", "", true);
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
	public void exportaExcel(HttpServletResponse response, @PathVariable("id") String id, HttpSession httpSession)
			throws Exception {
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

		Iterator<especie> ff = esp.iterator();
		while (ff.hasNext()) {
			especie es = ff.next();
			ArrayList<String> tipoMercado = new ArrayList<String>();
			estadoProductorNewDB dataDB = new estadoProductorNewDB();
			String json = dataDB.getRestriccionesExcel(ses.getIdTemporada(), es.getIdEspecie(), "", "", "", "", "",true);
			System.out.println(ses.getIdTemporada());
			JSONObject j = new JSONObject(json);
			System.out.println(j);
			JSONArray columns = j.getJSONArray("columns");
			JSONArray data = j.getJSONArray("data");

			Sheet sheet = book.createSheet(es.getEspecie());
			System.out.println("-------------------------------------------------");
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
					
						if (header.toString().toUpperCase().equals("SI."))
							cell.setCellValue("LC");
						else
							cell.setCellValue(header.toString().toUpperCase());	
					}
				}
				i++;
			}
			  SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();
			//generamos seteo de Hoja
			 CellRangeAddress[] regiones = {
			            CellRangeAddress.valueOf("I2:DA500")
			        };
			 ConditionalFormattingRule rule = sheetCF.createConditionalFormattingRule(IconSet.GREY_3_ARROWS);
			 IconMultiStateFormatting iconFmt = rule.getMultiStateFormatting();
			 iconFmt.setIconSet(IconSet.GREY_3_ARROWS);
			 iconFmt.setIconOnly(true);
		        
		        iconFmt.setIconOnly(true);
		        
		        ConditionalFormattingThreshold[] thresholds = iconFmt.getThresholds();
		        
		        // threshold[0] -> Valor >= 1 => ya no muestra la cruz roja sino algo mejor
		        thresholds[0].setRangeType(RangeType.NUMBER);
		        thresholds[0].setValue(1.0);
		        
		        // threshold[1] -> Valor >= 2 => check verde
		        thresholds[1].setRangeType(RangeType.NUMBER);
		        thresholds[1].setValue(2.0);
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
	public void getExcel(HttpServletResponse response, @PathVariable("id") String id, HttpSession httpSession)
			throws Exception {
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
		 * "Content-Disposition : attachment" will be directly download, may provide
		 * save as popup, based on your browser setting
		 */
		// response.setHeader("Content-Disposition", String.format("attachment;
		// filename=\"%s\"", file.getName()));

		// response.setContentLength((int) file.length());
		

		// Copy bytes from source to destination(outputstream in this example),
		// closes both streams.
		

	}

	@RequestMapping(value = "/caData/{id}", method = { RequestMethod.GET })
	public void getData(HttpServletResponse response, @PathVariable("id") String id, HttpSession httpSession)
			throws Exception {
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
		 * mail archivos = mailDB.getMailFile(id); InputStream inputStream =
		 * archivos.getFile();
		 * 
		 * Workbook workbook = new XSSFWorkbook(inputStream); Sheet datatypeSheet =
		 * workbook.getSheetAt(0); Iterator<Row> iterator = datatypeSheet.iterator();
		 * String errorMessage = "<table border=1> "; errorMessage +=
		 * "<tr><td>especie</td><td>codigo</td><td>productor</td><td>fecha ingreso</td><td>fecha muestra</td><td>fecha emision</td><td>resultado</td> </tr>"
		 * ; while (iterator.hasNext()) { errorMessage += "<tr> "; Row currentRow =
		 * iterator.next(); Iterator<Cell> cellIterator = currentRow.iterator();
		 * 
		 * errorMessage += "<td>"+currentRow.getCell(5)+"</td>"; errorMessage +=
		 * "<td>"+currentRow.getCell(8)+"</td>"; errorMessage +=
		 * "<td>"+currentRow.getCell(9)+"</td>"; errorMessage +=
		 * "<td>"+currentRow.getCell(10)+"</td>"; errorMessage +=
		 * "<td>"+currentRow.getCell(11)+"</td>"; errorMessage +=
		 * "<td>"+currentRow.getCell(12)+"</td>"; errorMessage +=
		 * "<td>"+currentRow.getCell(19)+"</td>"; // errorMessage +=
		 * "<td>"+currentRow.getCell(9)+"</td>";
		 * 
		 * 
		 * errorMessage += "</tr> "; } errorMessage += "</table> ";
		 * System.out.println();
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

}
