package lib.data.json.procesos;

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

import lib.data.json.dataTable;
import lib.db.ProductorDB;
import lib.db.mailDB;
import lib.security.session;
import lib.struc.Productor;
import lib.struc.filterSql;
import lib.struc.mail;
import lib.struc.mesajesJson;

@Controller
public class cargaAutomaticaJson {

	@RequestMapping(value = "/cargaAutomatica/view", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable getShopInJSON(HttpServletRequest request,HttpSession httpSession)  {
		
		session ses = new session(httpSession);
		dataTable data = new dataTable();
		if (ses.isValid()) {
			
			data.setDraw(0);
			data.init();
			return data;
		}

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


		return data;

	}
	
	@RequestMapping(value = "/productor2/insertProductor" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertProductor(@RequestBody Productor row,HttpSession httpSession) throws ParseException
	{
		boolean resp = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return resp;
		}
		resp = ProductorDB.insertProductor(row);
		return resp;		
	}
	
	@RequestMapping(value = "/productor2/{codigo}", method = { RequestMethod.GET })
	public @ResponseBody Productor getUserId(@PathVariable String codigo,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			Productor row=null;
			return row;
		}

		Productor row = ProductorDB.getProductor(codigo);

		return row;

	}
	
	@RequestMapping(value = "/productor2/put", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody mesajesJson setUser(@RequestBody Productor row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		mesajesJson mensaje = new mesajesJson();
		if (ses.isValid()) {
			mensaje.setEstado("error");
			mensaje.setMensaje("Session terminada");
			return mensaje;
		}
		System.out.println("PUT::::::::::::::::::::::::::::");

		ProductorDB.updateProductor(row);
		

		mensaje.setEstado("ok");
		mensaje.setMensaje("Guardado con exito");

		return mensaje;

	}
	
	@RequestMapping(value = "/productor2/getAllOutFilter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<Productor> getAllOutFilter(HttpServletRequest request, HttpSession httpSession) throws Exception
	{
		session ses = new session(httpSession);
		ArrayList<Productor> productores = new ArrayList<Productor>();
		if(ses.isValid())
		{
			productores = null;
			return productores;
		}
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		productores = ProductorDB.getProductor(filter, "", 0, 1000);
		return productores;
	}
}
