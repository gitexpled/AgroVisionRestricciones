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

import lib.db.ParcelaDB;
import lib.db.ProductorDB;
import lib.db.userDB;
import lib.security.session;
import lib.struc.Productor;
import lib.struc.filterSql;
import lib.struc.mesajesJson;
import lib.struc.user;

@Controller
public class ProductorJson {

	
	@RequestMapping(value = "/productor/drop",  method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody mesajesJson drop(HttpServletRequest request,HttpSession httpSession) throws Exception {
		String id = request.getParameter("id");
		session ses = new session(httpSession);
		mesajesJson msn = new mesajesJson();
		if (ses.isValid()) {
			
			msn.setEstado("NOK");
			msn.setMensaje("usuario invalido");
			return msn;
		}
		System.out.println(id);
		//String mensaje="Hola";
		String mensaje=ProductorDB.delete(id);

		msn.setEstado("OK");
		msn.setMensaje(mensaje);
		return msn;

	}
	
	
	@RequestMapping(value = "/productor/view", method = { RequestMethod.POST, RequestMethod.GET })
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


		case "0":colum = "codProductor";break;
		case "1":colum = "nombre";break;
		case "2":colum = "creado";break;
		case "3":colum = "modificado";break;
		
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

		ArrayList<Productor> datas;
		try {
			datas = ProductorDB.getProductor(filter,order, start, length);

			Iterator<Productor> f = datas.iterator();

			data.setRecordsFiltered(ProductorDB.getProductoresAll(filter));
			data.setRecordsTotal(ProductorDB.getProductoresAll(filter));

			while (f.hasNext()) {
				Productor row = f.next();
				
				
				String[] d = { row.getCodigo()+"",row.getNombre(),row.getCreado()+"",row.getModificado()+"" };

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}
	@RequestMapping(value = "/productor/view2", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable view2(HttpServletRequest request,HttpSession httpSession)  {
		
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


		case "0":colum = "codProductor";break;
		case "1":colum = "nombre";break;
		case "2":colum = "creado";break;
		case "3":colum = "modificado";break;
		
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

		ArrayList<Productor> datas;
		try {
			datas = ProductorDB.getProductorAll(filter, order, start, length);

			Iterator<Productor> f = datas.iterator();

			data.setRecordsFiltered(ProductorDB.getProductoresAll(filter));
			data.setRecordsTotal(ProductorDB.getProductoresAll(filter));

			while (f.hasNext()) {
				Productor row = f.next();
				
				
				String[] d = { row.getCodigo()+"",row.getNombre(),row.getCreado()+"",row.getModificado()+"" };

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}
	
	@RequestMapping(value = "/productor/insertProductor" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
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
	
	@RequestMapping(value = "/productor/{codigo}", method = { RequestMethod.GET })
	public @ResponseBody Productor getUserId(@PathVariable String codigo,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			Productor row=null;
			return row;
		}

		Productor row = ProductorDB.getProductor(codigo);

		return row;

	}
	
	@RequestMapping(value = "/productor/put", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
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
	
	@RequestMapping(value = "/productor/getAllOutFilter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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
