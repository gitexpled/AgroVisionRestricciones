package cl.expled.web.page;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lib.db.TemporadaDB;
import lib.db.userDB;
import lib.db.mailDB;
import lib.struc.*;

/*
 * author: Crunchify.com
 * 
 */

@Controller
public class login {
	
	mailDB mailDB = new mailDB();
	/*@RequestMapping(value = "/recupera", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView recupera(Model model, HttpServletRequest request, HttpSession httpSession) {
		Map<String, String[]> parameters = request.getParameterMap();
		model.addAttribute("mensaje", "se envio correo con inturcciones para restablecer contraseña");
		
		try {
			System.out.println("login IN--->"+parameters.get("email")[0]);
			for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			    String clave = entry.getKey();
			    String[] valores = entry.getValue();

			    System.out.print(clave + ": ");
			    for (String valor : valores) {
			        System.out.print(valor + " ");
			    }
			    System.out.println(); // salto de línea
			}
		    System.out.println(); // salto de línea
	
			
				return new ModelAndView("login");
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			e.printStackTrace();
			model.addAttribute("alerta", "display-hide");
			
			return new ModelAndView("login");
		}
	}*/

	@RequestMapping(value = "/login", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView loginPage(Model model, HttpServletRequest request, HttpSession httpSession) {
	    Map<String, String[]> parameters = request.getParameterMap();
	    try {
	        if (parameters.containsKey("username") && parameters.containsKey("password")) {
	            String username = parameters.get("username")[0];
	            String password = parameters.get("password")[0];

	            System.out.println("login IN--->" + username + "," + password);

	            if (userDB.validateUser(username, password)) {
	                lib.security.session ses = new lib.security.session(httpSession);
	                user u = userDB.getUserByUser(username);

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
	                model.addAttribute("mensaje", "Error en el ingreso de usuario/contraseña");
	                model.addAttribute("alerta", "");
	            }
	        }

	        return new ModelAndView("login");

	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("mensaje", "Error inesperado");
	        model.addAttribute("alerta", "");
	        return new ModelAndView("login");
	    }
	}

	@PostMapping("/recupera")
    public ModelAndView recupera(@RequestParam("email") String email, Model model) {
        try {
            String resultado = mailDB.generarClaveTemporal(email);

            if (resultado.startsWith("Correo enviado")) {
                model.addAttribute("mensaje", resultado);
            } else {
                model.addAttribute("alerta", resultado);
            }

            System.out.println("Resultado de generación: " + resultado);
            return new ModelAndView("login");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            model.addAttribute("alerta", "Error inesperado al procesar la solicitud.");
            return new ModelAndView("login");
        }
    }
	@GetMapping("/restorePass")
    public ModelAndView restorePass(@RequestParam("email") String email, Model model) {
        try {
            System.out.println("Resultado de generación: " + email);
            return new ModelAndView("restorePass");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            model.addAttribute("alerta", "Error inesperado al procesar la solicitud.");
            return new ModelAndView("restorePass");
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
		System.out.println("SALIENDO SISTEMA");
		return new ModelAndView("redirect:/webApp/login");
	}

}