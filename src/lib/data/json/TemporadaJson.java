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

import lib.db.LimiteDB;
import lib.db.TemporadaDB;
import lib.security.session;
import lib.struc.Limite;
import lib.struc.Temporada;
import lib.struc.filterSql;
import lib.struc.mesajesJson;

@Controller
public class TemporadaJson {

	@RequestMapping(value = "/temporada/view", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable getShopInJSON(HttpServletRequest request,HttpSession httpSession)  {
		
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


		case "0":colum = "temporada";break;
		case "1":colum = "creacion";break;
		case "2":colum = "idEspecie";break;
		case "3":colum = "desde";break;
		case "4":colum = "hasta";break;

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

		ArrayList<Temporada> datas;
		try {
			datas = TemporadaDB.getTemporada(filter, order, start, length);

			Iterator<Temporada> f = datas.iterator();
			data.setRecordsFiltered(TemporadaDB.getTemporadasAll(filter));
			data.setRecordsTotal(TemporadaDB.getTemporadasAll(filter));

			while (f.hasNext()) {
				Temporada row = f.next();
				String[] d = { row.getTemporada(),row.getCreado()+"", row.getIdEspecie()+"", row.getDesde()+"", row.getHasta()+"" ,row.getIdTemporada()+""};

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}
	
	@RequestMapping(value = "/temporada/insertTemporada" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertTemporada(@RequestBody Temporada row,HttpSession httpSession) throws ParseException
	{
		boolean resp = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return resp;
		}
		row.setIdUser(ses.getIdUser());
		resp = TemporadaDB.insertTemporada(row);
		
		Temporada t;
		try {
			t = TemporadaDB.getMaxTemprada();
			ses.setIdTemporada(t.getIdTemporada());
			ses.setTemporada(t.getTemporada());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return resp;		
	}
	
	@RequestMapping(value = "/temporada/{codigo}", method = { RequestMethod.GET })
	public @ResponseBody Temporada getTemporada(@PathVariable String codigo,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			Temporada row=null;
			return row;
		}

		Temporada row = TemporadaDB.getTemporada(codigo);

		return row;

	}
	
	@RequestMapping(value = "/temporada/put", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody mesajesJson setTemporada(@RequestBody Temporada row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		mesajesJson mensaje = new mesajesJson();
		if (ses.isValid()) {
			mensaje.setEstado("error");
			mensaje.setMensaje("Session terminada");
			return mensaje;
		}
		System.out.println("PUT::::::::::::::::::::::::::::");

		TemporadaDB.updateTemporada(row);
		

		mensaje.setEstado("ok");
		mensaje.setMensaje("Guardado con exito");

		return mensaje;

	}
	
}
