package lib.data.json;


import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.db.MantenedorBD;
import lib.security.session;

@Controller
public class Mantenedor {

	@RequestMapping(value = "/Replace", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String MANTENEDOR(@RequestBody  String row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return "";
		}
		String r = MantenedorBD.Insert(row);
		return r;
	}
	@RequestMapping(value = "/Update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String UPDATE(@RequestBody  String row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return "";
		}
		String r = MantenedorBD.Update(row);
		return r;
	}
	@RequestMapping(value = "/Delete", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String DELETE(@RequestBody  String row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return "";
		}
		String r = MantenedorBD.Delete(row);
		return r;
	}
	@RequestMapping(value = "/SELECT", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String SELECT(@RequestBody  String row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return "";
		}
		String r = MantenedorBD.Select(row);
		return r;
	}
	@RequestMapping(value = "/EXECSP", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String RECOTEC(@RequestBody  String row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return "0";
		}
		r = MantenedorBD.EXECSP(row); 
		return r;
	}
	@RequestMapping(value = "/CallSp", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = {"*"})
	public @ResponseBody String CALLSP(@RequestBody  String row, HttpSession httpSession) throws Exception {
		String r = MantenedorBD.CallSp(row);
		return r;
	}
}
