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

import lib.db.ParcelaVariedadDB;
import lib.db.TurnoDB;
import lib.db.userDB;
import lib.security.session;
import lib.struc.Parcela;
import lib.struc.Turno;
import lib.struc.filterSql;
import lib.struc.mesajesJson;
import lib.struc.user;

@Controller
public class TurnoJson {
	
	@RequestMapping(value = "/turno/drop",  method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody mesajesJson drop(HttpServletRequest request,HttpSession httpSession) throws Exception {
		String id = request.getParameter("id");
		session ses = new session(httpSession);
		mesajesJson msn = new mesajesJson();
		if (ses.isValid()) {
			
			msn.setEstado("NOK");
			msn.setMensaje("usuario invalido");
			return msn;
		}

		String mensaje=TurnoDB.delete(id);

		msn.setEstado("OK");
		msn.setMensaje(mensaje);
		return msn;

	}
	

	@RequestMapping(value = "/turno/view", method = { RequestMethod.POST, RequestMethod.GET })
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

		ArrayList<Turno> datas;
		try {
			datas = TurnoDB.getAll(filter, "", start, length);

			Iterator<Turno> f = datas.iterator();

			data.setRecordsFiltered(TurnoDB.getAll(filter));
			data.setRecordsTotal(TurnoDB.getAll(filter));

			while (f.hasNext()) {
				Turno row = f.next();
				String fsma = "NO";
				String habilitado = "NO";
				
				String[] d = {row.getIdTurno()+"", row.getCodProductor(), row.getCodParcela(),row.getCodTurno(),row.getCreado()+"",row.getModificado()+""};

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}
	
	@RequestMapping(value = "/turno/insert" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insert(@RequestBody Turno row,HttpSession httpSession) throws ParseException
	{
		boolean resp = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return resp;
		}
		resp = TurnoDB.insert(row);
		return resp;		
	}
	
	@RequestMapping(value = "/turno/{codigo}", method = { RequestMethod.GET })
	public @ResponseBody Turno getById(@PathVariable String codigo,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			Turno row=null;
			return row;
		}

		Turno row = TurnoDB.get(codigo);

		return row;

	}
	
	@RequestMapping(value = "/turno/put", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody mesajesJson uodate(@RequestBody Turno row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		mesajesJson mensaje = new mesajesJson();
		if (ses.isValid()) {
			mensaje.setEstado("error");
			mensaje.setMensaje("Session terminada");
			return mensaje;
		}
		System.out.println("PUT::::::::::::::::::::::::::::");

		TurnoDB.update(row);
		

		mensaje.setEstado("ok");
		mensaje.setMensaje("Guardado con exito");

		return mensaje;

	}
	
	
	
	@RequestMapping(value = "/turno/getAllByParcela/{codProductor}/{codParcela}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<Turno> getAllByProductor(HttpServletRequest request, HttpSession httpSession,@PathVariable("codProductor") String codProductor,@PathVariable("codParcela") String codParcela) throws Exception
	{
		session ses = new session(httpSession);
		ArrayList<Turno> turnos = new ArrayList<Turno>();
		if(ses.isValid())
		{
			turnos = null;
			return turnos;
		}
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		filterSql value= new filterSql();
		value.setCampo("codParcela");
		value.setValue(codParcela);
		filter.add(value);
		
		filterSql value3= new filterSql();
		value3.setCampo("codProductor");
		value3.setValue(codProductor);
		filter.add(value3);
		
		turnos = TurnoDB.getAll(filter, "", 0, 1000);
		return turnos;
	}
}
