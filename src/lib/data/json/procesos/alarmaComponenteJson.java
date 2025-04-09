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
import lib.db.*;
import lib.security.session;
import lib.struc.*;

@Controller
public class alarmaComponenteJson {

	@RequestMapping(value = "/alarmaComponente/view", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable getView(HttpServletRequest request,HttpSession httpSession)  {
		
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


		case "0":colum = "codProducto";break;
		case "1":colum = "cantidad";break;
		
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

		ArrayList<alarmaComponente> datas;
		try {
			datas = alarmaComponenteDB.getAll(filter, order, start, length);

			Iterator<alarmaComponente> f = datas.iterator();

			data.setRecordsFiltered(alarmaComponenteDB.getAllcount(filter));
			data.setRecordsTotal(alarmaComponenteDB.getAllcount(filter));

			while (f.hasNext()) {
				alarmaComponente row = f.next();
				String[] d = { row.getCodProducto(),row.getCantidad()+"" };

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}
	
	
	@RequestMapping(value = "/alarmaComponente/{codigo}", method = { RequestMethod.GET })
	public @ResponseBody alarmaComponente getId(@PathVariable String codigo,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			alarmaComponente row=null;
			return row;
		}

		alarmaComponente row = alarmaComponenteDB.getId(codigo);

		return row;

	}
	@RequestMapping(value = "/alarmaComponente/drop",  method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody mesajesJson drop(HttpServletRequest request,HttpSession httpSession) throws Exception {
		String id = request.getParameter("id");
		session ses = new session(httpSession);
		boolean estado=false;
		mesajesJson msn = new mesajesJson();
		if (ses.isValid()) {
			
			msn.setEstado("NOK");
			msn.setMensaje("usuario invalido");
			return msn;
		}
		System.out.println("DROP::::::::::::::::::::::::::::"+id);
	
		
		msn.setEstado("OK");
		msn.setMensaje("");
		return msn;

	}
	
	@RequestMapping(value = "/alarmaComponente/put", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody mesajesJson put(@RequestBody alarmaComponente row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		mesajesJson mensaje = new mesajesJson();
		if (ses.isValid()) {
			mensaje.setEstado("error");
			mensaje.setMensaje("Session terminada");
			return mensaje;
		}
		System.out.println("PUT::::::::::::::::::::::::::::");

		alarmaComponenteDB.update(row);
		

		mensaje.setEstado("ok");
		mensaje.setMensaje("Guardado con exito");

		return mensaje;

	}
	
}
