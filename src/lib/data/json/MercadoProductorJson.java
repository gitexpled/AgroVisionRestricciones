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

import lib.db.MercadoProductorDB;



import lib.security.session;
import lib.struc.MercadoProductor;

import lib.struc.filterSql;
import lib.struc.mesajesJson;


@Controller
public class MercadoProductorJson {

	
	@RequestMapping(value = "/mercadoProductor/delete", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
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
		MercadoProductorDB.delete(id);
		
		msn.setEstado("OK");
		msn.setMensaje("Usuario Modificado");
		return msn;
		
	}
	
	
	@RequestMapping(value = "/mercadoProductor/view", method = { RequestMethod.POST, RequestMethod.GET })
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
		case "1":colum = "pv.productor";break;
		case "2":colum = "pv.etapa";break;
		case "3":colum = "pv.campo";break;
		case "4":colum = "pv.turno";break;
		case "5":colum = "pv.idVariedad";break;
		case "6":colum = "m.mercado";break;
		case "7":colum = "pv.creado";break;
		case "8":colum = "pv.modificado";break;
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

		ArrayList<MercadoProductor> datas;
		try {
			datas = MercadoProductorDB.getAll(filter, order, start, length);

			Iterator<MercadoProductor> f = datas.iterator();

			data.setRecordsFiltered(MercadoProductorDB.getAll(filter));
			data.setRecordsTotal(MercadoProductorDB.getAll(filter));

			while (f.hasNext()) {
				MercadoProductor row = f.next();
				
			
				String[] d = { row.getId()+"",row.getCodProductor(), row.getCodEtapa(), row.getCodCampo(),row.getCodTurno(),row.getCodVariedad(),row.getMercado(),row.getCreado()+"",row.getModificado()+""};

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}
	
	@RequestMapping(value = "/mercadoProductor/insert" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insert(@RequestBody MercadoProductor row,HttpSession httpSession) throws ParseException
	{
		boolean resp = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return resp;
		}
		resp = MercadoProductorDB.insert(row);
		return resp;		
	}
	
	@RequestMapping(value = "/mercadoProductor/{codigo}", method = { RequestMethod.GET })
	public @ResponseBody MercadoProductor getById(@PathVariable String codigo,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			MercadoProductor row=null;
			return row;
		}

		MercadoProductor row = MercadoProductorDB.get(codigo);

		return row;

	}
	
	@RequestMapping(value = "/mercadoProductor/put", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody mesajesJson update(@RequestBody MercadoProductor row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		mesajesJson mensaje = new mesajesJson();
		if (ses.isValid()) {
			mensaje.setEstado("error");
			mensaje.setMensaje("Session terminada");
			return mensaje;
		}
		System.out.println("PUT::::::::::::::::::::::::::::");
		System.out.println("INSERTO");
		MercadoProductorDB.update(row);
		

		mensaje.setEstado("ok");
		mensaje.setMensaje("Guardado con exito");

		return mensaje;

	}
	
	@RequestMapping(value = "/mercadoProductor/getAllOutFilter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<MercadoProductor> getSelectBox(HttpServletRequest request, HttpSession httpSession) throws Exception
	{
		session ses = new session(httpSession);
		ArrayList<MercadoProductor> arrays = new ArrayList<MercadoProductor>();
		if(ses.isValid())
		{
			arrays = null;
			return arrays;
		}
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		arrays = MercadoProductorDB.getAll(filter, "", 0, 1000);
		return arrays;
	}
}
