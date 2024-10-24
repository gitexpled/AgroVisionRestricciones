package cl.expled.web.page;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import lib.db.TemporadaDB;
import lib.db.userDB;
import lib.struc.*;

/*
 * author: Crunchify.com
 * 
 */

@Controller
public class login {

	@RequestMapping(value = "/login", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView loginPage(Model model, HttpServletRequest request, HttpSession httpSession) {
		Map<String, String[]> parameters = request.getParameterMap();

		
		try {
			System.out.println("login IN--->"+parameters.get("username")[0] +","+ parameters.get("password")[0]);
			if ( userDB.validateUser(parameters.get("username")[0] , parameters.get("password")[0]) ) {
				System.out.println("login IN--->"+parameters.get("username")[0] +","+ parameters.get("password")[0]);
				
				lib.security.session  ses= new lib.security.session(httpSession);
				user u= userDB.getUserByUser(parameters.get("username")[0]);
				ses.init();
				
				ses.setIdPerfil(u.getIdPerfil());
				ses.setIdUser(u.getId());
				ses.setNombre(u.getNombre());
				
				
				
				Temporada t = TemporadaDB.getMaxTemprada();
				if (t != null) {
					ses.setIdTemporada(t.getIdTemporada());
					ses.setTemporada(t.getTemporada());
				}
				
				
				
				return new ModelAndView("redirect:/webApp/page/homePage");
			} else {
				return new ModelAndView("login");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			e.printStackTrace();
			model.addAttribute("alerta", "display-hide");
			return new ModelAndView("login");
		}
	}

	@RequestMapping("/index")
	public ModelAndView helloWorld2(Model model) {
		model.addAttribute("id", "jajajajaj :)");
		String message = "esto es una maldita prueba";
		return new ModelAndView("home", "message", message);
	}
	
	@RequestMapping("/exit")
	public ModelAndView exit(Model model, HttpSession httpSession) {
		lib.security.session  ses= new lib.security.session(httpSession);
		ses.close();
		return new ModelAndView("redirect:/webApp/login");
	}

}