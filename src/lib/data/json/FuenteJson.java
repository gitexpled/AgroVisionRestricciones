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

import lib.db.FuenteDB;
import lib.db.ProductorDB;
import lib.db.TipoProductoDB;
import lib.security.session;
import lib.struc.Fuente;
import lib.struc.Productor;
import lib.struc.TipoProducto;
import lib.struc.filterSql;
import lib.struc.mesajesJson;

@Controller
public class FuenteJson {

	@RequestMapping(value = "/fuente/view", method = { RequestMethod.POST, RequestMethod.GET })
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

		int start = Integer.parseInt(parameters.get("start")[0]);
		int length = Integer.parseInt(parameters.get("length")[0]);
		;

		ArrayList<Fuente> datas;
		try {
			datas = FuenteDB.getFuente(filter, "", start, length);

			Iterator<Fuente> f = datas.iterator();
			data.setRecordsFiltered(FuenteDB.getFuenteAll(filter));
			data.setRecordsTotal(FuenteDB.getFuenteAll(filter));

			while (f.hasNext()) {
				Fuente row = f.next();
				String[] d = { row.getNombre(),"",row.getIdFuente()+"" };

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}
	
	@RequestMapping(value = "/fuente/insertFuente" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertFuente(@RequestBody Fuente row,HttpSession httpSession) throws ParseException
	{
		boolean resp = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return resp;
		}
		resp = FuenteDB.insertFuente(row);
		return resp;		
	}
	
	@RequestMapping(value = "/fuente/{codigo}", method = { RequestMethod.GET })
	public @ResponseBody Fuente getFuenteId(@PathVariable String codigo,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			Fuente row=null;
			return row;
		}

		Fuente row = FuenteDB.getFuente(codigo);

		return row;

	}
	
	@RequestMapping(value = "/fuente/getAllOutFilter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<Fuente> getAllOutFilter(HttpServletRequest request, HttpSession httpSession) throws Exception
	{
		session ses = new session(httpSession);
		ArrayList<Fuente> fuentes = new ArrayList<Fuente>();
		if(ses.isValid())
		{
			fuentes = null;
			return fuentes;
		}
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		fuentes = FuenteDB.getFuente(filter, "", 0, 1000);
		return fuentes;
	}
	
	@RequestMapping(value = "/fuente/put", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody mesajesJson setTipoProducto(@RequestBody Fuente row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		mesajesJson mensaje = new mesajesJson();
		if (ses.isValid()) {
			mensaje.setEstado("error");
			mensaje.setMensaje("Session terminada");
			return mensaje;
		}
		System.out.println("PUT::::::::::::::::::::::::::::");

		FuenteDB.updateFuente(row);
		

		mensaje.setEstado("ok");
		mensaje.setMensaje("Guardado con exito");

		return mensaje;

	}
}
