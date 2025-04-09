package cl.expled.web.page;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lib.db.ProductorDB;
import lib.db.alarmaComponenteDB;
import lib.db.alarmaProductorDB;
import lib.db.systemMenuDB;
import lib.security.session;
import lib.struc.filterSql;
import lib.struc.systemMenu;

@Controller
public class page {
	
	
	//constructor de toda la pagina
	@RequestMapping(value= "/page/{id:.+}", method = { RequestMethod.GET })
	public ModelAndView mainDefault(Model model, @PathVariable("id") String id, HttpSession httpSession) {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		systemMenu m = new systemMenu();
		try {
			m = systemMenuDB.getMenuUrl(id);
			model.addAttribute("proceso", m.getProceso());
			model.addAttribute("pagina", m.getMenu());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// home page

		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		try {
			filter = new ArrayList<filterSql>();
			filterSql pp= new filterSql();
			pp.setCampo("arandano");
			pp.setValue("Y");
			filter.add(pp);
			
			//MVL
			/*
			int countProductor= ProductorDB.getProductoresAll(filter);
			
			filter = new ArrayList<filterSql>();
			int componente = alarmaComponenteDB.getAllcount(filter);
			int productor = alarmaProductorDB.getAllcount(filter);
			//int bloqueados= ProductorDB.getBloqueados(ses.getIdTemporada());
			int bloqueados= ProductorDB.getBloqueados2(ses.getIdTemporada());
			
			
			pp= new filterSql();
			pp.setCampo("cereza");
			pp.setValue("Y");
			filter.add(pp);
			
			int countProductorC= ProductorDB.getProductoresAll(filter);
			
			int bloqueadosC= ProductorDB.getBloqueadosCereza(ses.getIdTemporada());
			
			
			int bloqueDia= ProductorDB.getBloqueadosHoy();
			int total = productor + componente;
			
			
			model.addAttribute("alerta", total);
			
			model.addAttribute("bloqueDia", bloqueDia);
			
			model.addAttribute("bloqueados", bloqueados);
			model.addAttribute("productores", countProductor);
			
			model.addAttribute("bloqueadosC", bloqueadosC);
			model.addAttribute("productoresC", countProductorC);
			
			
			*/
			// model.addAttribute("nada", 20);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		model.addAttribute("content", id);
		model.addAttribute("controller", "content/"+id);
		model.addAttribute("javaScriptPage", id.replace(".", "/"));
		model.addAttribute("rand", ThreadLocalRandom.current().nextInt(10000, 99999 + 1));
		
		
		
		return new ModelAndView("layout/_main");
	}
	//constructor de toda la pagina
		@RequestMapping(value= "/pageAdm/{id:.+}", method = { RequestMethod.GET })
		public ModelAndView mainAdm(Model model, @PathVariable("id") String id,@RequestParam(value = "id", required=false) String idEspecie, HttpSession httpSession) {
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return new ModelAndView("redirect:/webApp/login");
			}
			systemMenu m;
			try {
				m = systemMenuDB.getMenuUrl(id);
				model.addAttribute("proceso", m.getProceso());
				model.addAttribute("pagina", m.getMenu());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(id+":::idEspecie:"+idEspecie);
			if (idEspecie==null)
				idEspecie="7";
			
			model.addAttribute("content", id);
			model.addAttribute("controller", "adm/"+id.replace(".", "_")+"?id="+idEspecie);
			model.addAttribute("javaScriptPage", id.replace(".", "/"));
			model.addAttribute("rand", ThreadLocalRandom.current().nextInt(10000, 99999 + 1));
			
			
			return new ModelAndView("layout/_main");
		}
		
	@RequestMapping("/content/{id:.+}")
	public ModelAndView contentDefault(Model model, @PathVariable("id") String id, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("IDFILTER","aa");
		
		System.out.println("MVL-JSP:"+id);
		return new ModelAndView("content/"+id.replace(".jsp", "").replace(".", "/"));
	}
	
	@RequestMapping("/content/{id:.+}/{key}/{esp}")
	public ModelAndView contentDefaultData(Model model, @PathVariable("id") String id,@PathVariable("key") String key,@PathVariable("esp") String esp, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		
		System.out.println( "_");
		System.out.println( key);
		String decodedToUTF8 = new String(key.replace(".jsp", "").getBytes("ISO-8859-1"), "UTF-8");
		
		System.out.println( decodedToUTF8);
		
		
		
		model.addAttribute("IDFILTER", decodedToUTF8.replace(".jsp", ""));
		
		if (esp.replace(".jsp", "").equals("1"))
			model.addAttribute("ESPECIE", "ARANDANO");
		else
			model.addAttribute("ESPECIE", "CEREZA");
		

		return new ModelAndView("content/"+id.replace(".jsp", "").replace(".", "/"));
	}
	@RequestMapping("/content/{id:.+}/{key}/{esp}/{nmuestra}")
	public ModelAndView contentDefaultData2(Model model, @PathVariable("id") String id,@PathVariable("key") String key,@PathVariable("esp") String esp,@PathVariable("nmuestra") String nmuestra, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("IDFILTER", key.replace(".jsp", ""));
		
		
		
		model.addAttribute("NMUESTRA", nmuestra.replace(".jsp", ""));
		
		
		if (esp.replace(".jsp", "").equals("1"))
			model.addAttribute("ESPECIE", "ARANDANO");
		else
			model.addAttribute("ESPECIE", "CEREZA");
		

		return new ModelAndView("content/"+id.replace(".jsp", "").replace(".", "/"));
	}
	@RequestMapping(value= "/page2/{id:.+}", method = { RequestMethod.GET })
	public ModelAndView mainDefaultData(Model model, @PathVariable("id") String id,@RequestParam("key") String key,@RequestParam("especie") String especie, HttpSession httpSession) {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		systemMenu m = new systemMenu();
		try {
			m = systemMenuDB.getMenuUrl(id);
			model.addAttribute("proceso", m.getProceso());
			model.addAttribute("pagina", m.getMenu());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		model.addAttribute("content", id);
		model.addAttribute("controller", "content/"+id+"/"+key+"/"+especie);
		model.addAttribute("javaScriptPage", id.replace(".", "/"));
		model.addAttribute("rand", ThreadLocalRandom.current().nextInt(10000, 99999 + 1));
		
		
		
		
		
		return new ModelAndView("layout/_main");
	}
	@RequestMapping(value= "/page3/{id:.+}", method = { RequestMethod.GET })
	public ModelAndView mainDefaultData2(Model model, @PathVariable("id") String id,@RequestParam("key") String key,@RequestParam("especie") String especie,@RequestParam("muestra") String nmuestra, HttpSession httpSession) {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		systemMenu m = new systemMenu();
		try {
			m = systemMenuDB.getMenuUrl(id);
			model.addAttribute("proceso", m.getProceso());
			model.addAttribute("pagina", m.getMenu());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		model.addAttribute("content", id);
		model.addAttribute("controller", "content/"+id+"/"+key+"/"+especie+"/"+nmuestra);
		model.addAttribute("javaScriptPage", id.replace(".", "/"));
		model.addAttribute("rand", ThreadLocalRandom.current().nextInt(10000, 99999 + 1));
		
		
		
		
		
		return new ModelAndView("layout/_main");
	}

}
