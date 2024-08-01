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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.db.ParcelaDB;
import lib.db.TurnoDB;
import lib.db.ParcelaDB;
import lib.db.userDB;
import lib.security.session;
import lib.struc.Parcela;
import lib.struc.Parcela;
import lib.struc.filterSql;
import lib.struc.mesajesJson;
import lib.struc.user;

@Controller
public class ParcelaJson {

	@RequestMapping(value = "/parcela/drop",  method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
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
		String mensaje=ParcelaDB.delete(id);

		msn.setEstado("OK");
		msn.setMensaje(mensaje);
		return msn;

	}

	@RequestMapping(value = "/parcela/view", method = { RequestMethod.POST, RequestMethod.GET })
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

		ArrayList<Parcela> datas;
		try {
			datas = ParcelaDB.getAll(filter, "", start, length);

			Iterator<Parcela> f = datas.iterator();

			data.setRecordsFiltered(ParcelaDB.getAll(filter));
			data.setRecordsTotal(ParcelaDB.getAll(filter));

			while (f.hasNext()) {
				Parcela row = f.next();
				String fsma = "NO";
				String habilitado = "NO";
				
				String[] d = { row.getCodigoProductor()+"",row.getCodigo()+"",row.getNombre(),row.getCreado()+"",row.getModificado()+"" };

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}
	
	@RequestMapping(value = "/parcela/insert" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insert(@RequestBody Parcela row,HttpSession httpSession) throws ParseException
	{
		boolean resp = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return resp;
		}
		resp = ParcelaDB.insert(row);
		return resp;		
	}
	
	@RequestMapping(value = "/parcela/{codigo}", method = { RequestMethod.GET })
	public @ResponseBody Parcela getById(@PathVariable String codigo,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			Parcela row=null;
			return row;
		}

		Parcela row = ParcelaDB.get(codigo);

		return row;

	}
	
	@RequestMapping(value = "/parcela/put", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody mesajesJson uodate(@RequestBody Parcela row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		mesajesJson mensaje = new mesajesJson();
		if (ses.isValid()) {
			mensaje.setEstado("error");
			mensaje.setMensaje("Session terminada");
			return mensaje;
		}
		System.out.println("PUT::::::::::::::::::::::::::::");

		ParcelaDB.update(row);
		

		mensaje.setEstado("ok");
		mensaje.setMensaje("Guardado con exito");

		return mensaje;

	}
	
	@RequestMapping(value = "/parcela/getAllOutFilter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<Parcela> getSelectBox(HttpServletRequest request, HttpSession httpSession) throws Exception
	{
		session ses = new session(httpSession);
		ArrayList<Parcela> Parcelaes = new ArrayList<Parcela>();
		if(ses.isValid())
		{
			Parcelaes = null;
			return Parcelaes;
		}
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		Parcelaes = ParcelaDB.getAll(filter, "", 0, 1000);
		return Parcelaes;
	}
	
	@RequestMapping(value = "/parcela/getAllByProductor/{codProductor}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<Parcela> getAllByProductor(HttpServletRequest request, HttpSession httpSession,@PathVariable("codProductor") String codProductor) throws Exception
	{
		session ses = new session(httpSession);
		ArrayList<Parcela> Parcelaes = new ArrayList<Parcela>();
		if(ses.isValid())
		{
			Parcelaes = null;
			return Parcelaes;
		}
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		filterSql value= new filterSql();
		value.setCampo("codProductor");
		value.setValue(codProductor);
		filter.add(value);
		Parcelaes = ParcelaDB.getAll(filter, "", 0, 1000);
		return Parcelaes;
	}
}
