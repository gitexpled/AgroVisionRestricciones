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

import lib.db.certificadoMercadoDB;
import lib.security.session;
import lib.struc.filterSql;
import lib.struc.mesajesJson;
import lib.struc.certificado;

@Controller
public class certificadoMercadoJson {
	 

	@RequestMapping(value = "/certificadoMercado/put", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)

	public @ResponseBody mesajesJson setUser(@RequestBody certificado row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		mesajesJson mensaje = new mesajesJson();
		if (ses.isValid()) {
			mensaje.setEstado("error");
			mensaje.setMensaje("Session terminada");
			return mensaje;
		}
		certificadoMercadoDB.updateCertficado(row);		

		mensaje.setEstado("ok");
		mensaje.setMensaje("Guardado con exito");

		return mensaje;

	}

	@RequestMapping(value = "/certificadoMercado/{id}", method = { RequestMethod.GET })
	public @ResponseBody certificado getUserId(@PathVariable int id,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			certificado row=null;
			return row;
		}

		certificado row = certificadoMercadoDB.getCertificado(id);

		return row;

	}

	@RequestMapping(value = "/certificadoMercado/view", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable getShopInJSON(HttpServletRequest request,HttpSession httpSession)  {
		System.out.println("view ");
		session ses = new session(httpSession);
		dataTable data = new dataTable();
		if (ses.isValid()) {
			
			data.setDraw(0);
			data.init();
			return data;
		}

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

		ArrayList<certificado> datas;
		try {
			datas = certificadoMercadoDB.getCerificados(filter, "", start, length);

			Iterator<certificado> f = datas.iterator();

			data.setRecordsFiltered(certificadoMercadoDB.getCertificadosAll(filter));
			data.setRecordsTotal(certificadoMercadoDB.getCertificadosAll(filter));

			while (f.hasNext()) {
				certificado row = f.next();
				String[] d = {row.getNombre(), row.getMercado(), row.getId()+""};

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}
	
	@RequestMapping(value = "/certificadoMercado/insertCertificado" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertUser(@RequestBody certificado row,HttpSession httpSession) throws ParseException
	{
		boolean resp = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return resp;
		}
		resp = certificadoMercadoDB.insertCertificado(row);
		return resp;		
	}
	
	
	
	
	@RequestMapping(value = "/certificadoMercado/getCertificados", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<certificado> getCerificados(HttpServletRequest request, HttpSession httpSession) throws Exception
	{
		ArrayList<certificado> certificados = null;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return certificados;
		}
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		certificados = certificadoMercadoDB.getAllCertificado();
		return certificados;
	}
	
}