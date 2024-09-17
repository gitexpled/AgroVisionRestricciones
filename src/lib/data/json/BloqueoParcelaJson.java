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
import lib.db.BloqueoParcelaDB;
import lib.db.userDB;
import lib.security.session;
import lib.struc.Parcela;
import lib.struc.Turno;
import lib.struc.BloqueoParcela;
import lib.struc.filterSql;
import lib.struc.mesajesJson;
import lib.struc.user;

@Controller
public class BloqueoParcelaJson {
	
	@RequestMapping(value = "/bloqueoParcela/drop",  method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody mesajesJson drop(HttpServletRequest request,HttpSession httpSession) throws Exception {
		String id = request.getParameter("id");
		session ses = new session(httpSession);
		mesajesJson msn = new mesajesJson();
		if (ses.isValid()) {
			
			msn.setEstado("NOK");
			msn.setMensaje("usuario invalido");
			return msn;
		}

		String mensaje=BloqueoParcelaDB.delete(id);

		msn.setEstado("OK");
		msn.setMensaje(mensaje);
		return msn;

	}
	

	@RequestMapping(value = "/bloqueoParcela/view", method = { RequestMethod.POST, RequestMethod.GET })
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

		ArrayList<BloqueoParcela> datas;
		try {
			datas = BloqueoParcelaDB.getAll(filter, "", start, length);

			Iterator<BloqueoParcela> f = datas.iterator();

			data.setRecordsFiltered(BloqueoParcelaDB.getAll(filter));
			data.setRecordsTotal(BloqueoParcelaDB.getAll(filter));

			while (f.hasNext()) {
				BloqueoParcela row = f.next();
				String fsma = "NO";
				String habilitado = "NO";

				
				String[] d = {row.getIdBloqueo()+"", row.getCodProductor(), row.getCodParcela(), row.getIdVariedad(), row.getIdMercado()+"",row.getComentario(),row.getCreado()+"",row.getEstado()+""};

				data.setData(d); 

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}
	
	@RequestMapping(value = "/bloqueoParcela/insert" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insert(@RequestBody BloqueoParcela row,HttpSession httpSession) throws ParseException
	{
		boolean resp = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return resp;
		}
		resp = BloqueoParcelaDB.insert(row);
		return resp;		
	}
	
	@RequestMapping(value = "/bloqueoParcela/{codigo}", method = { RequestMethod.GET })
	public @ResponseBody BloqueoParcela getById(@PathVariable String codigo,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			BloqueoParcela row=null;
			return row;
		}

		BloqueoParcela row = BloqueoParcelaDB.get(codigo);

		return row;

	}
	
	@RequestMapping(value = "/bloqueoParcela/put", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody mesajesJson uodate(@RequestBody Turno row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		mesajesJson mensaje = new mesajesJson();
		if (ses.isValid()) {
			mensaje.setEstado("error");
			mensaje.setMensaje("Session terminada");
			return mensaje;
		}
		System.out.println("PUT::::::::::::::::::::::::::::");

		BloqueoParcelaDB.update(row);
		

		mensaje.setEstado("ok");
		mensaje.setMensaje("Guardado con exito");

		return mensaje;

	}
	
	
	
	@RequestMapping(value = "/bloqueoParcela/getAllByParcela/{codProductor}/{codParcela}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<BloqueoParcela> getAllByProductor(HttpServletRequest request, HttpSession httpSession,@PathVariable("codProductor") String codProductor,@PathVariable("codParcela") String codParcela) throws Exception
	{
		session ses = new session(httpSession);
		ArrayList<BloqueoParcela> turnos = new ArrayList<BloqueoParcela>();
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
		
		turnos = BloqueoParcelaDB.getAll(filter, "", 0, 1000);
		return turnos;
	}
}
