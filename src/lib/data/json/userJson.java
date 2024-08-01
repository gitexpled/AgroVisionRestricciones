package lib.data.json;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.db.userDB;
import lib.security.session;
import lib.struc.filterSql;
import lib.struc.mesajesJson;
import lib.struc.user;

@Controller
public class userJson {
	 

	@RequestMapping(value = "/user/put", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)

	public @ResponseBody mesajesJson setUser(@RequestBody user row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		mesajesJson mensaje = new mesajesJson();
		if (ses.isValid()) {
			mensaje.setEstado("error");
			mensaje.setMensaje("Session terminada");
			return mensaje;
		}
		userDB.updateUser(row);		

		mensaje.setEstado("ok");
		mensaje.setMensaje("Guardado con exito");

		return mensaje;

	}

	@RequestMapping(value = "/user/{id}", method = { RequestMethod.GET })
	public @ResponseBody user getUserId(@PathVariable int id,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			user row=null;
			return row;
		}

		user row = userDB.getUser(id);

		return row;

	}

	@RequestMapping(value = "/user/view", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable getShopInJSON(HttpServletRequest request,HttpSession httpSession)  {
		
		session ses = new session(httpSession);
		dataTable data = new dataTable();
		if (ses.isValid()) {
			
			data.setDraw(0);
			data.init();
			return data;
		}

		Map<String, String[]> parameters = request.getParameterMap();
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		for (String key : parameters.keySet()) {
			//System.out.println(key);
			if (key.startsWith("vw_")) {
				String[] vals = parameters.get(key);
				for (String val : vals) {
					System.out.println(key+" -> " + val);
					filterSql fil = new filterSql();
					fil.setCampo(key.substring(3));
					fil.setValue(val);
					filter.add(fil);
				}
			}
		}

		
		data.setDraw(0);
		data.init();

		int start = Integer.parseInt(parameters.get("start")[0]);
		int length = Integer.parseInt(parameters.get("length")[0]);
		;

		ArrayList<user> datas;
		try {
			datas = userDB.getUsers(filter, "", start, length);

			Iterator<user> f = datas.iterator();

			data.setRecordsFiltered(userDB.getUsersAll(filter));
			data.setRecordsTotal(userDB.getUsersAll(filter));

			while (f.hasNext()) {
				user row = f.next();
				String[] d = { row.getNombre(), row.getApellido(), row.getUser(),row.getMail(),
						row.getCreacion() + "", row.getBaja() + "", row.getEstado()+"", row.getId()+"", "", "" };

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}
	
	@RequestMapping(value = "/user/insertUser" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertUser(@RequestBody user row,HttpSession httpSession) throws ParseException
	{
		boolean resp = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return resp;
		}
		resp = userDB.insertUser(row);
		return resp;		
	}
	
	@RequestMapping(value = "/user/validaUserName", method = {RequestMethod.GET,RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean validaUserName(HttpServletRequest request, HttpSession httpSession) throws Exception
	{
		boolean resp = true;
		String user = request.getParameter("user");
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return resp;
		}
		if(userDB.getUserByUser(user)!=null)
		{
			resp = false;
		}
		return resp;
	}
	
	@RequestMapping(value = "/user/setStatus", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ ResponseBody mesajesJson setStatus(HttpServletRequest request, HttpSession httpSession) throws NumberFormatException, Exception
	{
		String id = request.getParameter("id");
		mesajesJson msn = new mesajesJson();
		session ses = new session(httpSession);
		if (ses.isValid()) {
			msn.setEstado("NOK");
			msn.setMensaje("usuario invalido");
			return msn;
		}
		user us = userDB.getUser(Integer.parseInt(id));
		if(us.getEstado()==0)
		{
			us.setEstado(1);
		}else
		{
			us.setEstado(0);
		}
		userDB.updateUser(us);
		msn.setEstado("OK");
		msn.setMensaje("Usuario Modificado");
		return msn;
		
	}
	
	@RequestMapping(value = "/user/getByUserPass",method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody user getUserByPass(HttpServletRequest request, HttpSession httpSession) throws Exception
	{
		String user = request.getParameter("user");
		String pass = request.getParameter("pass");
		user us = userDB.getUserLogin(user, pass);
		return us;
	}
	
	@RequestMapping(value = "/user/getUsers", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<user> getUsers(HttpServletRequest request, HttpSession httpSession) throws Exception
	{
		ArrayList<user> users = null;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return users;
		}
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		users = userDB.getAllUsers();
		return users;
	}
	
}
