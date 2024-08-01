package cl.expled.web.layout;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import lib.db.ProductorDB;
import lib.db.TemporadaDB;
import lib.db.alarmaComponenteDB;
import lib.db.alarmaProductorDB;
import lib.io.config;
import lib.security.session;
import lib.struc.Temporada;
import lib.struc.filterSql;

@Controller
public class Element {
	@RequestMapping(value = "/layout_menu/{id:.+}", method = { RequestMethod.GET })
	public ModelAndView menu(Model model, @PathVariable String id, HttpSession httpSession) {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		id=id.replace(".jsp", "");
		System.out.println("PERFIL::::::::::::::::::::::::::::_" + id + "=" + ses.getIdPerfil());

	
		
		menu m= new menu();
		String[] strMenu= m.create(0,id,ses.getIdPerfil());
		
		model.addAttribute("menu", strMenu[1]);
		model.addAttribute("hola", "2");

		return new ModelAndView("layout/menu");
	}

	@RequestMapping("/layout_user")
	public ModelAndView menu_user(Model model, HttpSession httpSession) {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("nombre", ses.getValue("nombre"));
		return new ModelAndView("layout/user");
	}

	@RequestMapping("/layout_alert")
	public ModelAndView Alert(Model model, HttpSession httpSession) {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		try {
			int productor=0;//alarmaProductorDB.getAllcount(filter);
			int componente=0;//alarmaComponenteDB.getAllcount(filter);
			int bloqueDia= 0;//ProductorDB.getBloqueadosHoy(); -- este no funciona MVL: 20191122
			int total=0;//productor+componente+bloqueDia;
			
			model.addAttribute("alarmas", total+"");
			model.addAttribute("alarmaProductor", productor+"");
			model.addAttribute("alarmaComponente", componente+"");

			model.addAttribute("temporada",ses.getTemporada());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String message = "esto es una maldita prueba";
		return new ModelAndView("layout/alert", "message", message);
	}

	@RequestMapping("/layout_config")
	public ModelAndView Config(Model model, HttpSession httpSession) {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		//variable de configuracion de sitio web
		model.addAttribute("id", "jajajajaj :)");
		String message = "esto es una maldita prueba";
		return new ModelAndView("layout/config", "message", message);
	}
	@RequestMapping("/layout_javaScriptHeader")
	public ModelAndView javaScript(Model model, HttpSession httpSession) {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		//variable de configuracion de sitio web
		model.addAttribute("webPageApp", config.getProperty("webPageApp"));
		
		model.addAttribute("id", "jajajajaj :)");
		String message = "esto es una maldita prueba";
		return new ModelAndView("layout/javaScriptHeader", "message", message);
	}

}
