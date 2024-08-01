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

import lib.db.DiccionarioDB;
import lib.db.MercadoDB;
import lib.db.ProductorDB;
import lib.security.session;
import lib.struc.Diccionario;
import lib.struc.Productor;
import lib.struc.filterSql;
import lib.struc.mesajesJson;

@Controller
public class DiccionarioJson {

	@RequestMapping(value = "/diccionario/view", method = { RequestMethod.POST, RequestMethod.GET })
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

		ArrayList<Diccionario> datas;
		try {
			datas = DiccionarioDB.getDiccionario(filter, "", start, length);

			Iterator<Diccionario> f = datas.iterator();

			data.setRecordsFiltered(DiccionarioDB.getDiccionariosAll(filter));
			data.setRecordsTotal(DiccionarioDB.getDiccionariosAll(filter));

			while (f.hasNext()) {
				Diccionario row = f.next();
				String[] d = { row.getCodProducto(),row.getCodReemplazo(),row.getCreado()+"",row.getModificado()+"",row.getIdUser()+"" ,row.getIdDiccionario()+""};

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}
	
	@RequestMapping(value = "/diccionario/insertDiccionario" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertDiccionario(@RequestBody Diccionario row,HttpSession httpSession) throws ParseException
	{
		boolean resp = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return resp;
		}
		resp = DiccionarioDB.insertDiccionario(row);
		return resp;		
	}
	
	@RequestMapping(value = "/diccionario/{codigo}", method = { RequestMethod.GET })
	public @ResponseBody Diccionario getUserId(@PathVariable String codigo,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			Diccionario row=null;
			return row;
		}

		Diccionario row = DiccionarioDB.getDiccionario(codigo);

		return row;

	}
	
	@RequestMapping(value = "/diccionario/put", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody mesajesJson setUser(@RequestBody Diccionario row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		mesajesJson mensaje = new mesajesJson();
		if (ses.isValid()) {
			mensaje.setEstado("error");
			mensaje.setMensaje("Session terminada");
			return mensaje;
		}
		System.out.println("PUT::::::::::::::::::::::::::::");

		DiccionarioDB.updateDiccionario(row);
		

		mensaje.setEstado("ok");
		mensaje.setMensaje("Guardado con exito");

		return mensaje;

	}
	@RequestMapping(value = "/diccionario/getAllOutFilter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<Diccionario> getAllOutFilter(HttpServletRequest request, HttpSession httpSession) throws Exception
	{
		session ses = new session(httpSession);
		ArrayList<Diccionario> diccionarios = new ArrayList<Diccionario>();
		if(ses.isValid())
		{
			diccionarios = null;
			return diccionarios;
		}

		diccionarios = DiccionarioDB.getSelect();
		return diccionarios;
	}
	
	@RequestMapping(value = "/diccionario/validaDiccionario", method = {RequestMethod.GET,RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean validaDiccionarioName(HttpServletRequest request, HttpSession httpSession) throws Exception
	{
		boolean resp = true;
		String diccionario = request.getParameter("diccionario");
		System.out.println(diccionario);
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return resp;
		}
		if(DiccionarioDB.validaDiccionario(diccionario)!=null)
		{
			resp = false;
		}
		return resp;
	}
	/* para diccionario con igual nombres*/
	@RequestMapping(value = "/diccionario/viewSame", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable viewSame(HttpServletRequest request,HttpSession httpSession)  {
		
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

		ArrayList<Diccionario> datas;
		try {
			datas = DiccionarioDB.getDiccionarioEqual(filter, "", start, length);

			Iterator<Diccionario> f = datas.iterator();

			data.setRecordsFiltered(DiccionarioDB.getDiccionariosEqualAll(filter));
			data.setRecordsTotal(DiccionarioDB.getDiccionariosEqualAll(filter));

			while (f.hasNext()) {
				Diccionario row = f.next();
				String[] d = { row.getCodProducto(),row.getIdDiccionario()+""};

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}
	
	@RequestMapping(value = "/diccionario/getAllOutFilterEqual", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<Diccionario> getAllOutFilterEqual(HttpServletRequest request, HttpSession httpSession) throws Exception
	{
		session ses = new session(httpSession);
		ArrayList<Diccionario> diccionarios = new ArrayList<Diccionario>();
		if(ses.isValid())
		{
			diccionarios = null;
			return diccionarios;
		}

		diccionarios = DiccionarioDB.getSelectEqual();
		return diccionarios;
	}
}
