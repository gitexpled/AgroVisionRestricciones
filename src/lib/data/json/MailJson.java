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

import lib.db.mailDB;
import lib.db.userDB;
import lib.db.ProductorDB;
import lib.db.TipoProductoDB;
import lib.security.session;
import lib.struc.mail;
import lib.struc.Productor;
import lib.struc.TipoProducto;
import lib.struc.filterSql;
import lib.struc.mesajesJson;
import lib.struc.user;

@Controller
public class MailJson {

	@RequestMapping(value = "/mail/view", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable view(HttpServletRequest request,HttpSession httpSession)  {
		
		session ses = new session(httpSession);
		dataTable data = new dataTable();
		if (ses.isValid()) {
			
			data.setDraw(0);
			data.init();
			return data;
		}
	
		String order, colum = "", dir = "";
		Map<String, String[]> param = request.getParameterMap();
		for (String key : param.keySet()) {

			if (key.startsWith("order[0]")) {
				String[] vals = param.get(key);

				for (String val : vals) {
					if (key.contains("column"))
						colum = val;
					if (key.contains("dir"))
						dir = val;
				}

			}
		}
		switch (colum) {


		case "0":colum = "mail";break;
		case "1":colum = "mail";break;
	
		}
		
		order = colum + ":" + dir;
		System.out.println(order);

		System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
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

		ArrayList<mail> datas;
		try {
			datas = mailDB.getmail(filter, order, start, length);

			Iterator<mail> f = datas.iterator();
			data.setRecordsFiltered(mailDB.getmailAll(filter));
			data.setRecordsTotal(mailDB.getmailAll(filter));

			while (f.hasNext()) {
				mail row = f.next();
				String[] d = { row.getMail(),"",row.getIdMail()+"" };

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}
	@RequestMapping(value = "/mail/delete", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
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
		mailDB.delete(Integer.parseInt(id));
		msn.setEstado("OK");
		msn.setMensaje("Mail Eliminado");
		return msn;
		
	}
	
	@RequestMapping(value = "/mail/insertMail" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertMail(@RequestBody mail row,HttpSession httpSession) throws ParseException
	{
		boolean resp = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return resp;
		}
		resp = mailDB.insertmail(row);
		return resp;		
	}
	
	@RequestMapping(value = "/mail/{codigo}", method = { RequestMethod.GET })
	public @ResponseBody mail getmailId(@PathVariable String codigo,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			mail row=null;
			return row;
		}

		mail row = mailDB.getMail(codigo);

		return row;

	}
	
	@RequestMapping(value = "/mail/getAllOutFilter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<mail> getAllOutFilter(HttpServletRequest request, HttpSession httpSession) throws Exception
	{
		session ses = new session(httpSession);
		ArrayList<mail> mails = new ArrayList<mail>();
		if(ses.isValid())
		{
			mails = null;
			return mails;
		}
		
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		
		mails = mailDB.getmail(filter, "", 0, 1000);
		return mails;
	}
	
	@RequestMapping(value = "/mail/put", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody mesajesJson put(@RequestBody mail row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		mesajesJson mensaje = new mesajesJson();
		if (ses.isValid()) {
			mensaje.setEstado("error");
			mensaje.setMensaje("Session terminada");
			return mensaje;
		}
		System.out.println("PUT::::::::::::::::::::::::::::");

		mailDB.updatemail(row);
		

		mensaje.setEstado("ok");
		mensaje.setMensaje("Guardado con exito");

		return mensaje;

	}
}
