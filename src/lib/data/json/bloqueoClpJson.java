package lib.data.json;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
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

import lib.db.bloqueoClpDB;



import lib.security.session;
import lib.struc.bloqueoClp;

import lib.struc.filterSql;
import lib.struc.mesajesJson;


@Controller
public class bloqueoClpJson {

	
	@RequestMapping(value = "/bloqueoClp/delete", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ ResponseBody mesajesJson delete(HttpServletRequest request, HttpSession httpSession) throws NumberFormatException, Exception
	{
		String id = request.getParameter("id");
		mesajesJson msn = new mesajesJson();
		session ses = new session(httpSession);
		if (ses.isValid()) {
			msn.setEstado("NOK");
			msn.setMensaje("usuario invalido");
			return msn;
		}
		bloqueoClpDB.delete(id);
		
		msn.setEstado("OK");
		msn.setMensaje("Usuario Modificado");
		return msn;
		
	}
	
	
	@RequestMapping(value = "/bloqueoClp/view", method = { RequestMethod.POST, RequestMethod.GET })
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


		case "0":colum = "pv.id";break;
		case "1":colum = "p.ProductorNombre";break;
		case "2":colum = "pv.etapa";break;
		case "3":colum = "pv.campo";break;
		case "4":colum = "pv.variedad";break;
		case "5":colum = "pv.habilitado";break;
		case "6":colum = "pv.creado";break;
		case "7":colum = "pv.modificado";break;
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

		ArrayList<bloqueoClp> datas;
		try {
			datas = bloqueoClpDB.getAll(filter, order, start, length);

			Iterator<bloqueoClp> f = datas.iterator();

			data.setRecordsFiltered(bloqueoClpDB.getAll(filter));
			data.setRecordsTotal(bloqueoClpDB.getAll(filter));

			while (f.hasNext()) {
				bloqueoClp row = f.next();
				
			
				String[] d = { row.getId()+"",row.getProductor(), row.getEtapa(), row.getCampo(), row.getIdVariedad(),row.getHabilitado(),row.getCreado()+"",row.getModificado()+""};

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}
	
	@RequestMapping(value = "/bloqueoClp/insert" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insert(@RequestBody bloqueoClp row,HttpSession httpSession) throws ParseException
	{
		boolean resp = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return resp;
		}
		try {
			resp = bloqueoClpDB.insert(row);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;		
	}
	
	@RequestMapping(value = "/bloqueoClp/{codigo}", method = { RequestMethod.GET })
	public @ResponseBody bloqueoClp getById(@PathVariable String codigo,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			bloqueoClp row=null;
			return row;
		}

		bloqueoClp row = bloqueoClpDB.get(codigo);

		return row;

	}
	
	@RequestMapping(value = "/bloqueoClp/put", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody mesajesJson update(@RequestBody bloqueoClp row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		mesajesJson mensaje = new mesajesJson();
		if (ses.isValid()) {
			mensaje.setEstado("error");
			mensaje.setMensaje("Session terminada");
			return mensaje;
		}
		System.out.println("PUT::::::::::::::::::::::::::::");
		System.out.println("INSERTO");
		bloqueoClpDB.update(row);
		

		mensaje.setEstado("ok");
		mensaje.setMensaje("Guardado con exito");

		return mensaje;

	}
	
	@RequestMapping(value = "/bloqueoClp/getAllOutFilter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<bloqueoClp> getSelectBox(HttpServletRequest request, HttpSession httpSession) throws Exception
	{
		session ses = new session(httpSession);
		ArrayList<bloqueoClp> arrays = new ArrayList<bloqueoClp>();
		if(ses.isValid())
		{
			arrays = null;
			return arrays;
		}
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		arrays = bloqueoClpDB.getAll(filter, "", 0, 1000);
		return arrays;
	}
}
