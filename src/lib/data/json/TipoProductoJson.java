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
import lib.db.ProductorDB;
import lib.db.TipoProductoDB;
import lib.security.session;
import lib.struc.Diccionario;
import lib.struc.Productor;
import lib.struc.TipoProducto;
import lib.struc.filterSql;
import lib.struc.mesajesJson;

@Controller
public class TipoProductoJson {

	@RequestMapping(value = "/tipoProducto/view", method = { RequestMethod.POST, RequestMethod.GET })
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

		ArrayList<TipoProducto> datas;
		try {
			datas = TipoProductoDB.getTipoProducto(filter, "", start, length);

			Iterator<TipoProducto> f = datas.iterator();
			data.setRecordsFiltered(TipoProductoDB.getTipoProductoAll(filter));
			data.setRecordsTotal(TipoProductoDB.getTipoProductoAll(filter));

			while (f.hasNext()) {
				TipoProducto row = f.next();
				String[] d = { row.getTipoProducto(),"",row.getIdTipoProducto()+"" };

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}
	
	@RequestMapping(value = "/tipoProducto/insertTipoProducto" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertTipoProducto(@RequestBody TipoProducto row,HttpSession httpSession) throws ParseException
	{
		boolean resp = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return resp;
		}
		resp = TipoProductoDB.insertTipoProducto(row);
		return resp;		
	}
	
	@RequestMapping(value = "/tipoProducto/{codigo}", method = { RequestMethod.GET })
	public @ResponseBody TipoProducto getTipoProductoId(@PathVariable String codigo,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			TipoProducto row=null;
			return row;
		}

		TipoProducto row = TipoProductoDB.getTipoProducto(codigo);

		return row;

	}
	@RequestMapping(value = "/tipoProducto/getAllOutFilter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<TipoProducto> getAllOutFilter(HttpServletRequest request, HttpSession httpSession) throws Exception
	{
		session ses = new session(httpSession);
		ArrayList<TipoProducto> tiposProductos = new ArrayList<TipoProducto>();
		if(ses.isValid())
		{
			tiposProductos = null;
			return tiposProductos;
		}
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		tiposProductos = TipoProductoDB.getTipoProducto(filter, "", 0, 1000);
		return tiposProductos;
	}
	@RequestMapping(value = "/tipoProducto/put", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody mesajesJson setTipoProducto(@RequestBody TipoProducto row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		mesajesJson mensaje = new mesajesJson();
		if (ses.isValid()) {
			mensaje.setEstado("error");
			mensaje.setMensaje("Session terminada");
			return mensaje;
		}
		System.out.println("PUT::::::::::::::::::::::::::::");

		TipoProductoDB.updateTipoProducto(row);
		

		mensaje.setEstado("ok");
		mensaje.setMensaje("Guardado con exito");

		return mensaje;

	}
	
	@RequestMapping(value = "/tipoProducto/validaTp", method = {RequestMethod.GET,RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean validaTp(HttpServletRequest request,HttpSession httpSession) throws Exception {
		
		boolean resp = true;
		String name = request.getParameter("name");
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return resp;
		}
		if(TipoProductoDB.getTipoProductoByName(name)!=null)
		{
			resp = false;
		}
		return resp;

	}
}
