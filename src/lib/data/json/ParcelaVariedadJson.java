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

import lib.db.ParcelaVariedadDB;

import lib.db.userDB;
import lib.security.session;

import lib.struc.ParcelaVariedad;
import lib.struc.filterSql;
import lib.struc.mesajesJson;
import lib.struc.user;

@Controller
public class ParcelaVariedadJson {

	@RequestMapping(value = "/parcelaVariedad/view", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable view(HttpServletRequest request,HttpSession httpSession)  {
		
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

		ArrayList<ParcelaVariedad> datas;
		try {
			datas = ParcelaVariedadDB.getAll(filter, "", start, length);

			Iterator<ParcelaVariedad> f = datas.iterator();

			data.setRecordsFiltered(ParcelaVariedadDB.getAll(filter));
			data.setRecordsTotal(ParcelaVariedadDB.getAll(filter));

			while (f.hasNext()) {
				ParcelaVariedad row = f.next();
				
			
				String[] d = { row.getId()+"",row.getCodProductor(), row.getCodParcela(),row.getCodTurno(),row.getCodVariedad(),row.getCreado()+"",row.getModificado()+""};

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}
	
	@RequestMapping(value = "/parcelaVariedad/insert" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insert(@RequestBody ParcelaVariedad row,HttpSession httpSession) throws ParseException
	{
		boolean resp = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return resp;
		}
		resp = ParcelaVariedadDB.insert(row);
		return resp;		
	}
	
	@RequestMapping(value = "/parcelaVariedad/{codigo}", method = { RequestMethod.GET })
	public @ResponseBody ParcelaVariedad getById(@PathVariable String codigo,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			ParcelaVariedad row=null;
			return row;
		}

		ParcelaVariedad row = ParcelaVariedadDB.get(codigo);

		return row;

	}
	@RequestMapping(value = "/parcelaVariedad/drop",  method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
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

		ParcelaVariedadDB.delete(id);

		msn.setEstado("OK");
		msn.setMensaje("Usuario Modificado");
		return msn;

	}
	
	@RequestMapping(value = "/parcelaVariedad/put", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody mesajesJson uodate(@RequestBody ParcelaVariedad row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		mesajesJson mensaje = new mesajesJson();
		if (ses.isValid()) {
			mensaje.setEstado("error");
			mensaje.setMensaje("Session terminada");
			return mensaje;
		}
		System.out.println("PUT::::::::::::::::::::::::::::");
		System.out.println("INSERTO");
		ParcelaVariedadDB.update(row);
		

		mensaje.setEstado("ok");
		mensaje.setMensaje("Guardado con exito");

		return mensaje;

	}
	
	@RequestMapping(value = "/parcelaVariedad/getAllOutFilter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<ParcelaVariedad> getSelectBox(HttpServletRequest request, HttpSession httpSession) throws Exception
	{
		session ses = new session(httpSession);
		ArrayList<ParcelaVariedad> parcelaVariedades = new ArrayList<ParcelaVariedad>();
		if(ses.isValid())
		{
			parcelaVariedades = null;
			return parcelaVariedades;
		}
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		parcelaVariedades = ParcelaVariedadDB.getAll(filter, "", 0, 1000);
		return parcelaVariedades;
	}
}
