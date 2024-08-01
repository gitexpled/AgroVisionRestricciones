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

import lib.db.CertificacionDB;
import lib.db.TipoProductoDB;
import lib.security.session;
import lib.struc.Certificacion;
import lib.struc.TipoProducto;
import lib.struc.filterSql;
import lib.struc.mesajesJson;


@Controller
public class CertificacionJson {

	@RequestMapping(value = "/certificacion/view", method = { RequestMethod.POST, RequestMethod.GET })
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

		ArrayList<Certificacion> datas;
		try {
			datas = CertificacionDB.getCertificacion(filter, "", start, length);

			Iterator<Certificacion> f = datas.iterator();
			data.setRecordsFiltered(CertificacionDB.getCertificacionesAll(filter));
			data.setRecordsTotal(CertificacionDB.getCertificacionesAll(filter));

			while (f.hasNext()) {
				Certificacion row = f.next();
				String[] d = { row.getCertificacionesCol(),row.getPrefijo(),row.getCreado()+"",row.getModificado()+"",row.getIdUser()+"",row.getIdCertificaciones()+"" };

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}
	
	@RequestMapping(value = "/certificacion/insertCertificacion" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertCertificacion(@RequestBody Certificacion row,HttpSession httpSession) throws ParseException
	{
		boolean resp = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return resp;
		}
		resp = CertificacionDB.insertCertificacion(row);
		return resp;		
	}
	
	@RequestMapping(value = "/certificacion/{codigo}", method = { RequestMethod.GET })
	public @ResponseBody Certificacion getUserId(@PathVariable String codigo,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			Certificacion row=null;
			return row;
		}

		Certificacion row =CertificacionDB.getCertificacion(codigo);

		return row;

	}
	
	@RequestMapping(value = "/certificacion/put", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody mesajesJson setUser(@RequestBody Certificacion row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		mesajesJson mensaje = new mesajesJson();
		if (ses.isValid()) {
			mensaje.setEstado("error");
			mensaje.setMensaje("Session terminada");
			return mensaje;
		}
		System.out.println("PUT::::::::::::::::::::::::::::");

		CertificacionDB.updateCertificacion(row);
		

		mensaje.setEstado("ok");
		mensaje.setMensaje("Guardado con exito");

		return mensaje;

	}
	@RequestMapping(value = "/certificacion/getAllOutFilter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<Certificacion> getAllOutFilter(HttpServletRequest request, HttpSession httpSession) throws Exception
	{
		session ses = new session(httpSession);
		ArrayList<Certificacion> certificaciones = new ArrayList<Certificacion>();
		if(ses.isValid())
		{
			certificaciones = null;
			return certificaciones;
		}
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		certificaciones = CertificacionDB.getCertificacion(filter, "", 0, 1000);
		return certificaciones;
	}
}
