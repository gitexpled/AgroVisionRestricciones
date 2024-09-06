package lib.data.json.procesos;

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
public class cargaManualJson {

	@RequestMapping(value = "/cargaManual/view", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable getView(HttpServletRequest request,HttpSession httpSession)  {
		
		session ses = new session(httpSession);
		dataTable data = new dataTable();
//		if (ses.isValid()) {
//			
//			data.setDraw(0);
//			data.init();
//			return data;
//		}

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

		ArrayList<cargaManual> datas;
		try {
			datas = cargaManualDB.getAll(filter, "", start, length);

			Iterator<cargaManual> f = datas.iterator();

			data.setRecordsFiltered(cargaManualDB.getAllcount(filter));
			data.setRecordsTotal(cargaManualDB.getAllcount(filter));

			while (f.hasNext()) {
				cargaManual row = f.next();
				
				String[] d = { row.getIdCargaManual()+"",row.getCodProductor(), row.getEspecie(),row.getCodParcela(),row.getCodTurno(),row.getIdVariedad(),row.getFecha(),row.getLaboratorio(),row.getIdentificador() };

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}
	
	@RequestMapping(value = "/cargaManual/add" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean add(@RequestBody cargaManual row,HttpSession httpSession) throws ParseException
	{
		boolean resp = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return resp;
		}
		

			row.setIdUsuario(ses.getIdUser());
			row.setIdTemporada(ses.getIdTemporada());
			
			resp = cargaManualDB.insert(row);
	
		
		
		
		return resp;		
	}
	
	@RequestMapping(value = "/cargaManual/{codigo}", method = { RequestMethod.GET })
	public @ResponseBody cargaManual getId(@PathVariable String codigo,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			cargaManual row=null;
			return row;
		}

		cargaManual row = cargaManualDB.getId(codigo);

		return row;

	}
	
	@RequestMapping(value = "/cargaManual/put", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody mesajesJson put(@RequestBody Productor row,HttpSession httpSession) throws Exception {
		
	    session ses = new session(httpSession);
		mesajesJson mensaje = new mesajesJson();
		if (ses.isValid()) {
			mensaje.setEstado("error");
			mensaje.setMensaje("Session terminada");
			return mensaje;
		}

		ProductorDB.updateProductor(row);
		

		mensaje.setEstado("ok");
		mensaje.setMensaje("Guardado con exito");

		return mensaje;

	}
	
	@RequestMapping(value = "/cargaManual/getAllOutFilter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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
	
	@RequestMapping(value = "/cargaManual/updateAnalisis", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UpdateAnalisis(@RequestBody  cargaManual row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			return false;
		}
		return cargaManualDB.UpdateAnalisis(row);

	}
}

