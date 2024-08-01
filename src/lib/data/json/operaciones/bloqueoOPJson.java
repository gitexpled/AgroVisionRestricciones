package lib.data.json.operaciones;

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

import lib.data.json.dataTable;
import lib.db.*;
import lib.security.session;
import lib.struc.*;

@Controller
public class bloqueoOPJson {

	@RequestMapping(value = "/bloqueoOp/view", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable getView(HttpServletRequest request,HttpSession httpSession)  {
		
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

		ArrayList<bloqueo> datas;
		try {
			datas = bloqueadoOpDB.getAll(filter, "", start, length);

			Iterator<bloqueo> f = datas.iterator();

			data.setRecordsFiltered(bloqueadoOpDB.getAllcount(filter));
			data.setRecordsTotal(bloqueadoOpDB.getAllcount(filter));

			while (f.hasNext()) {
				bloqueo row = f.next();
				String[] d = { row.getIdBloqueo()+"",row.getCodProductor(),row.getMercado(),row.getEspecie(),row.getB() };

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}
	
	@RequestMapping(value = "/bloqueoOp/add" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean add(@RequestBody bloqueo row,HttpSession httpSession) throws ParseException
	{
		boolean resp = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return resp;
		}
		row.setIdUsuario(ses.getIdUser());
		row.setIdTemporada(ses.getIdTemporada());
		resp = bloqueadoDB.insert(row);
		
		
		
		return resp;		
	}
	
	@RequestMapping(value = "/bloqueoOp/{codigo}", method = { RequestMethod.GET })
	public @ResponseBody bloqueo getId(@PathVariable String codigo,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			bloqueo row=null;
			return row;
		}
		System.out.println("ID: "+codigo);
		bloqueo row = bloqueadoOpDB.getId(codigo);

		return row;

	}
	
	@RequestMapping(value = "/bloqueoOp/put", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody mesajesJson put(@RequestBody bloqueo row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		mesajesJson mensaje = new mesajesJson();
		if (ses.isValid()) {
			mensaje.setEstado("error");
			mensaje.setMensaje("Session terminada");
			return mensaje;
		}
		System.out.println("PUT::::::::::::::::::::::::::::idBloqueo: "+row.getIdBloqueo());

		row.setIdUsuario(ses.getIdUser());
		row.setIdTemporada(ses.getIdTemporada());
		bloqueadoOpDB.update(row);
		

		mensaje.setEstado("ok");
		mensaje.setMensaje("Guardado con exito");

		return mensaje;

	}
	
	@RequestMapping(value = "/bloqueoOp/getAllOutFilter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<Productor> getAllOutFilter(HttpServletRequest request, HttpSession httpSession) throws Exception
	{
		session ses = new session(httpSession);
		ArrayList<Productor> productores = new ArrayList<Productor>();
		if(ses.isValid())
		{
			productores = null;
			return productores;
		}
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		productores = ProductorDB.getProductor(filter, "", 0, 1000);
		return productores;
	}
}
