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
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import lib.db.MercadoDB;
import lib.db.mailDB;
import lib.security.session;
import lib.struc.Mercado;
import lib.struc.filterSql;
import lib.struc.mail;





@Controller
public class informes {
	
	
	@RequestMapping("/adm/produccion_estadoProductor")
	public ModelAndView estadoProductorProContent(Model model, HttpSession httpSession) {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		ArrayList<Mercado> mer= null;
		try {
			ArrayList<filterSql> filter = new ArrayList<filterSql>();
			
			mer=MercadoDB.getMercado(filter, "idMercado", 0, 9999);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		List<Mercado> aa= mer;
		
		
		//ModelAndView model2 = new ModelAndView("content/informe/estadoProductor");
		//model2.addObject("th",mer);
		model.addAttribute("th",mer);
		
		
		
		
		return new ModelAndView("content/produccion/estadoProductor");
	}
	
		@RequestMapping("/adm/informe_estadoProductor")
		public ModelAndView estadoProductorContent(Model model, HttpSession httpSession) {
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return new ModelAndView("redirect:/webApp/login");
			}
			ArrayList<Mercado> mer= null;
			try {
				ArrayList<filterSql> filter = new ArrayList<filterSql>();
				
				mer=MercadoDB.getMercado(filter, "idMercado", 0, 9999);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			List<Mercado> aa= mer;
			
			
			//ModelAndView model2 = new ModelAndView("content/informe/estadoProductor");
			//model2.addObject("th",mer);
			model.addAttribute("th",mer);
			
			
			
			
			return new ModelAndView("content/informe/estadoProductor");
		}
		
		@RequestMapping("/adm/informe_exportarSap")
		public ModelAndView exportarSap(Model model, HttpSession httpSession) {
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return new ModelAndView("redirect:/webApp/login");
			}
		
			
			
			
			
			return new ModelAndView("content/informe/exportarSap");
		}
		
		
		
		@RequestMapping("/adm/informe_estadoProductor2")
		public ModelAndView estadoProductorContent2(Model model, HttpSession httpSession) {
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return new ModelAndView("redirect:/webApp/login");
			}
			ArrayList<Mercado> mer= null;
			try {
				ArrayList<filterSql> filter = new ArrayList<filterSql>();
				mer=MercadoDB.getMercado(filter, "idMercado", 0, 9999);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			List<Mercado> aa= mer;
			
			
			//ModelAndView model2 = new ModelAndView("content/informe/estadoProductor");
			//model2.addObject("th",mer);
			model.addAttribute("th",mer);
			
			
			
			
			return new ModelAndView("content/informe/estadoProductor2");
		}
		
		
	
		

}
