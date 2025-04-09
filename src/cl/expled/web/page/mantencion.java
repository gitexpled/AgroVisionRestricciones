package cl.expled.web.page;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lib.security.session;

@Controller
public class mantencion {

	
//
//	// ADMINISTRACION DE USUARIOS
//	@RequestMapping("/usuario")
//	public ModelAndView usuario(Model model, HttpSession httpSession) {
//		session ses = new session(httpSession);
//		if (ses.isValid()) {
//			return new ModelAndView("redirect:/webApp/login");
//		}
//		model.addAttribute("content", "usuario");
//		model.addAttribute("javaScriptPage", "mantencion-usuarios");
//		model.addAttribute("titulo","Mantenci&oacute;n");
//		model.addAttribute("subTitulo","Usuario");
//		return new ModelAndView("layout/_main");
//	}
//
//	@RequestMapping("/content/usuario")
//	public ModelAndView usuarioContent(Model model, HttpSession httpSession) {
//		session ses = new session(httpSession);
//		if (ses.isValid()) {
//			return new ModelAndView("redirect:/webApp/login");
//		}
//		return new ModelAndView("content/usuario");
//	}
//
//	//ADMINISTRACI�N DE PRODUCTORES
//	@RequestMapping("/productor")
//	public ModelAndView productor(Model model, HttpSession httpSession)
//	{
//		session ses = new session(httpSession);
//		if(ses.isValid())
//		{
//			return new ModelAndView("redirect:/webApp/login");
//		}
//		model.addAttribute("content", "productor");
//		model.addAttribute("javaScriptPage","mantencion-productor");
//		return new ModelAndView("layout/_main");
//	}
//	
//	@RequestMapping("/content/productor")
//	public ModelAndView contentProductor(Model mode, HttpSession httpSession)
//	{
//		session ses = new session(httpSession);
//		if(ses.isValid())
//		{
//			return new ModelAndView("redirect:/webApp/login");
//		}
//		return new ModelAndView("content/productor");
//	}
//	//ADMINISTRACION DE DICCIONARIOS
//	@RequestMapping("/diccionario")
//	public ModelAndView dicconario(Model model, HttpSession httpSession)
//	{
//		session ses = new session(httpSession);
//		if(ses.isValid())
//		{
//			return new ModelAndView("redirect:/webApp/login");
//		}
//		model.addAttribute("content", "diccionario");
//		model.addAttribute("javaScriptPage","mantencion-diccionario");
//		return new ModelAndView("layout/_main");
//	}
//	@RequestMapping("/content/diccionario")
//	public ModelAndView contentDiccionario(Model mode, HttpSession httpSession)
//	{
//		session ses = new session(httpSession);
//		if(ses.isValid())
//		{
//			return new ModelAndView("redirect:/webApp/login");
//		}
//		return new ModelAndView("content/diccionario");
//	}
//	//ADMINISTRACION DE MERCADOS
//	@RequestMapping("/mercado")
//	public ModelAndView mercado(Model model, HttpSession httpSession)
//	{
//		session ses = new session(httpSession);
//		if(ses.isValid())
//		{
//			return new ModelAndView("redirect:/webApp/login");
//		}
//		model.addAttribute("content", "mercado");
//		model.addAttribute("javaScriptPage", "mantencion-mercado");
//		return new ModelAndView("layout/_main");
//	}
//	@RequestMapping("/content/mercado")
//	public ModelAndView contentMercado(Model model, HttpSession httpSession)
//	{
//		session ses = new session(httpSession);
//		if(ses.isValid())
//		{
//			return new ModelAndView("redirect:/webApp/login");
//		}
//		return new ModelAndView("content/mercado");
//	}
//	//ADMINISTRACION DE CERTIFICACIONES
//	@RequestMapping("/certificaciones")
//	public ModelAndView certificaciones(Model model, HttpSession httpSession)
//	{
//		session ses = new session(httpSession);
//		if(ses.isValid())
//		{
//			return new ModelAndView("redirect:/webApp/login");
//		}
//		model.addAttribute("content", "certificaciones");
//		model.addAttribute("javaScriptPage", "mantencion-certificaciones");
//		return new ModelAndView("layout/_main");
//	}
//	@RequestMapping("/content/certificaciones")
//	public ModelAndView contentCertificaciones(Model model, HttpSession httpSession)
//	{
//		session ses = new session(httpSession);
//		if(ses.isValid())
//		{
//			return new ModelAndView("redirect:/webApp/login");
//		}
//		return new ModelAndView("content/certificaciones");
//	}
//	//ADMINISTRACION DE TEMPORADAS
//	@RequestMapping("/temporada")
//	public ModelAndView temporada(Model model, HttpSession httpSession)
//	{
//		session ses = new session(httpSession);
//		if(ses.isValid())
//		{
//			return new ModelAndView("redirect:/webApp/login");
//		}
//		model.addAttribute("content", "temporada");
//		model.addAttribute("javaScriptPage", "mantencion-temporada");
//		return new ModelAndView("layout/_main");
//	}
//	@RequestMapping("/content/temporada")
//	public ModelAndView contentTemporada(Model model, HttpSession httpSession)
//	{
//		session ses = new session(httpSession);
//		if(ses.isValid())
//		{
//			return new ModelAndView("redirect:/webApp/login");
//		}
//		return new ModelAndView("content/temporada");
//	}
//	//ADMINISTRACION DE TIPO DE PRODUCTO
//	@RequestMapping("/tipoProducto")
//	public ModelAndView tipoProducto(Model model, HttpSession httpSession)
//	{
//		session ses = new session(httpSession);
//		if(ses.isValid())
//		{
//			return new ModelAndView("redirect:/webApp/login");
//		}
//		model.addAttribute("content", "tipoProducto");
//		model.addAttribute("javaScriptPage", "mantencion-tipoProducto");
//		return new ModelAndView("layout/_main");
//	}
//	@RequestMapping("/content/tipoProducto")
//	public ModelAndView contentTipoProducto(Model model, HttpSession httpSession)
//	{
//		session ses = new session(httpSession);
//		if(ses.isValid())
//		{
//			return new ModelAndView("redirect:/webApp/login");
//		}
//		return new ModelAndView("content/tipoProducto");
//	}
//	//ADMINISTRACION DE FUENTE
//	@RequestMapping("/fuente")
//	public ModelAndView fuente(Model model, HttpSession httpSession)
//	{
//		session ses = new session(httpSession);
//		if(ses.isValid())
//		{
//			return new ModelAndView("redirect:/webApp/login");
//		}
//		model.addAttribute("content", "fuente");
//		model.addAttribute("javaScriptPage", "mantencion-fuente");
//		return new ModelAndView("layout/_main");
//	}
//	@RequestMapping("/content/fuente")
//	public ModelAndView contentFuente(Model model, HttpSession httpSession)
//	{
//		session ses = new session(httpSession);
//		if(ses.isValid())
//		{
//			return new ModelAndView("redirect:/webApp/login");
//		}
//		return new ModelAndView("content/fuente");
//	}
//	//ADMINISTRACION DE LIMITE
//	@RequestMapping("/limite")
//	public ModelAndView limite(Model model, HttpSession httpSession)
//	{
//		session ses = new session(httpSession);
//		if(ses.isValid())
//		{
//			return new ModelAndView("redirect:/webApp/login");
//		}
//		model.addAttribute("content", "limite");
//		model.addAttribute("javaScriptPage", "mantencion-limite");
//		return new ModelAndView("layout/_main");
//	}
//	@RequestMapping("/content/limite")
//	public ModelAndView contentLimite(Model model, HttpSession httpSession)
//	{
//		session ses = new session(httpSession);
//		if(ses.isValid())
//		{
//			return new ModelAndView("redirect:/webApp/login");
//		}
//		return new ModelAndView("content/limite");
//	}
//	

}