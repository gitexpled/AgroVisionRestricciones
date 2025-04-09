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

import lib.db.MercadoDB;
import lib.db.ProductorDB;
import lib.db.userDB;
import lib.security.session;
import lib.struc.Mercado;
import lib.struc.Productor;
import lib.struc.filterSql;
import lib.struc.mesajesJson;

@Controller
public class MercadoJson {
	
	@RequestMapping(value = "/mercado/view", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable view(HttpServletRequest request,HttpSession httpSession)  {
		
		session ses = new session(httpSession);
		dataTable data = new dataTable();
		if (ses.isValid()) {
			
			data.setDraw(0);
			data.init();
			return data;
		}
		String order, colum = "", dir = "";
		Map<String, String[]> param = request.getParameterMap();
		for (String key : param.keySet()) {

			if (key.startsWith("order[0]")) {
				String[] vals = param.get(key);

				for (String val : vals) {
					if (key.contains("column"))
						colum = val;
					if (key.contains("dir"))
						dir = val;
				}

			}
		}
		switch (colum) {


		case "0":colum = "mercado";break;
		case "1":colum = "mercado2";break;
		case "2":colum = "pf";break;
		case "3":colum = "regla";break;
		case "4":colum = "cliente";break;
		case "5":colum = "sap";break;
		case "6":colum = "creado";break;
		case "7":colum = "modificado";break;
		}
		
		order = colum + ":" + dir;
		System.out.println(order);
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

		ArrayList<Mercado> datas;
		try {
			datas = MercadoDB.getMercado(filter, order, start, length);

			Iterator<Mercado> f = datas.iterator();
			data.setRecordsFiltered(MercadoDB.getMercadosAll(filter));
			data.setRecordsTotal(MercadoDB.getMercadosAll(filter));

			while (f.hasNext()) {
				Mercado row = f.next();
				String[] d = { row.getMercado(),row.getMercado2(),row.getPf(),row.getRegla(),row.getCliente(),row.getSap() ,row.getCreado()+"",row.getModificado()+"",row.getIdUser()+"",row.getIdMercado()+"" };

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}
	
	@RequestMapping(value = "/mercado/insertMercado" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertMercado(@RequestBody Mercado row,HttpSession httpSession) throws ParseException
	{
		boolean resp = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return resp;
		}
		resp = MercadoDB.insertMercado(row);
		return resp;		
	}
	
	@RequestMapping(value = "/mercado/{codigo}", method = { RequestMethod.GET })
	public @ResponseBody Mercado getId(@PathVariable String codigo,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			Mercado row=null;
			return row;
		}

		Mercado row = MercadoDB.getMercado(codigo);

		return row;

	}
	
	@RequestMapping(value = "/mercado/put", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody mesajesJson update(@RequestBody Mercado row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		mesajesJson mensaje = new mesajesJson();
		if (ses.isValid()) {
			mensaje.setEstado("error");
			mensaje.setMensaje("Session terminada");
			return mensaje;
		}
		System.out.println("PUT::::::::::::::::::::::::::::");

		MercadoDB.updateMercado(row);
		

		mensaje.setEstado("ok");
		mensaje.setMensaje("Guardado con exito");

		return mensaje;

	}
	@RequestMapping(value = "/mercado/getAllOutFilter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<Mercado> getAllOutFilter(HttpServletRequest request, HttpSession httpSession) throws Exception
	{
		session ses = new session(httpSession);
		ArrayList<Mercado> mercados = new ArrayList<Mercado>();
		if(ses.isValid())
		{
			mercados = null;
			return mercados;
		}
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		mercados = MercadoDB.getMercado(filter, "", 0, 1000);
		return mercados;
	}
	
	
	@RequestMapping(value = "/mercado/getAllOutFilter2", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<Mercado> getAllOutFilter2(HttpServletRequest request, HttpSession httpSession) throws Exception
	{
		session ses = new session(httpSession);
		ArrayList<Mercado> mercados = new ArrayList<Mercado>();
		if(ses.isValid())
		{
			mercados = null;
			return mercados;
		}
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		filterSql fil=new filterSql();
		fil.setCampo("cliente");
		fil.setValue("N");
		filter.add(fil);
		mercados = MercadoDB.getMercado(filter, "", 0, 1000);
		return mercados;
	}
	@RequestMapping(value = "/mercado/soloClientes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<Mercado> soloClientes(HttpServletRequest request, HttpSession httpSession) throws Exception
	{
		session ses = new session(httpSession);
		ArrayList<Mercado> mercados = new ArrayList<Mercado>();
		if(ses.isValid())
		{
			mercados = null;
			return mercados;
		}
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		filterSql fil=new filterSql();
		fil.setCampo("cliente");
		fil.setValue("Y");
		filter.add(fil);
		mercados = MercadoDB.getMercado(filter, "", 0, 1000);
		return mercados;
	}
	
	
	@RequestMapping(value = "/mercado/validaMercadoName", method = {RequestMethod.GET,RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean validaMercadoName(HttpServletRequest request, HttpSession httpSession) throws Exception
	{
		boolean resp = true;
		String mercado = request.getParameter("mercado");
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return resp;
		}
		if(MercadoDB.getMercadoByName(mercado)!=null)
		{
			resp = false;
		}
		return resp;
	}
}
