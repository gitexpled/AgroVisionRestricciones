package lib.data.json.informes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.data.json.dataTable;
import lib.db.CertificacionDB;
import lib.db.estadoProductorDB;
import lib.db.exportarSapDB;
import lib.security.session;
import lib.struc.Certificacion;
import lib.struc.filterSql;
import lib.struc.restriccion;


@Controller
public class bloqueadoHoyJson {
	

	


	@RequestMapping(value = "/bloqueadoHoy/view", method = { RequestMethod.POST, RequestMethod.GET })
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

		ArrayList<restriccion> datas;
		try {
			datas = estadoProductorDB.getAll(filter, "", start, length);

			Iterator<restriccion> f = datas.iterator();
			data.setRecordsFiltered(estadoProductorDB.getAllcount(filter));
			data.setRecordsTotal(estadoProductorDB.getAllcount(filter));

			while (f.hasNext()) {
				restriccion row = f.next();
				String[] d = { row.getCodProductor(),row.getFecha(),row.getCodProducto(),row.getLimite(),row.getMercado(),row.getEspecie(),row.getnMuestra(),row.getAutomatica() };

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
		


	}
	@RequestMapping(value = "/restricciones/view", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable viewRestricciones(HttpServletRequest request,HttpSession httpSession)  {
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

		ArrayList<restriccion> datas;
		try {
			datas = estadoProductorDB.getAllRest(filter, "", start, length);

			Iterator<restriccion> f = datas.iterator();
			data.setRecordsFiltered(estadoProductorDB.getAllcountRest(filter));
			data.setRecordsTotal(estadoProductorDB.getAllcountRest(filter));

			while (f.hasNext()) {
				restriccion row = f.next();
				String[] d = { row.getCodProductor(),row.getNomProductor(), row.getCodParcela(),row.getCodTurno(),row.getFecha(),row.getFechaCarga(),row.getCodProducto(),row.getLimite(),row.getVariedad(),row.getEspecie(),row.getnMuestra(),row.getAutomatica() };

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
		


	}
	
	@RequestMapping(value = "/restZona/view", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable viewrestZona(HttpServletRequest request,HttpSession httpSession)  {
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

		ArrayList<restriccion> datas;
		try {
			datas = estadoProductorDB.getAllZona(filter, "", start, length);

			Iterator<restriccion> f = datas.iterator();
			data.setRecordsFiltered(estadoProductorDB.getAllcountZona(filter));
			data.setRecordsTotal(estadoProductorDB.getAllcountZona(filter));

			while (f.hasNext()) {
				restriccion row = f.next();
				String[] d = { row.getCodProductor(),row.getnMuestra(),row.getFecha().replace("00:00:00.0", ""),row.getCodProducto(),row.getLimite(),row.getEspecie(),row.getAutomatica() };

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
		


	}
	@RequestMapping(value = "/restZona5/view", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable viewrestZona5(HttpServletRequest request,HttpSession httpSession)  {
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

		ArrayList<restriccion> datas;
		try {
			datas = estadoProductorDB.getAllZona5(filter, "", start, length);

			Iterator<restriccion> f = datas.iterator();
			data.setRecordsFiltered(estadoProductorDB.getAllcountZona5(filter));
			data.setRecordsTotal(estadoProductorDB.getAllcountZona5(filter));

			while (f.hasNext()) {
				restriccion row = f.next();
				String[] d = { row.getCodProductor(),row.getnMuestra(),row.getFecha().replace("00:00:00.0", ""),row.getCodProducto(),row.getLimite(),row.getEspecie(),row.getAutomatica() };

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
		


	}
	
	
	
	@RequestMapping(value = "/restZona2/view", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable viewrestZona2(HttpServletRequest request,HttpSession httpSession)  {
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

		ArrayList<restriccion> datas;
		try {
			datas = estadoProductorDB.getAllZona2(filter, "", start, length);

			Iterator<restriccion> f = datas.iterator();
			data.setRecordsFiltered(estadoProductorDB.getAllcountZona2(filter));
			data.setRecordsTotal(estadoProductorDB.getAllcountZona2(filter));

			while (f.hasNext()) {
				restriccion row = f.next();
				String[] d = { row.getCodProductor(),row.getMercado(),row.getEspecie(),row.getAutomatica() };

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
		


	}
	@RequestMapping(value = "/restMercado/view", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable viewrestMercado(HttpServletRequest request,HttpSession httpSession)  {
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
		System.out.println("restricciones por mercsado");
		ArrayList<restriccion> datas;
		try {
			datas = estadoProductorDB.getAllMercado(filter, "", start, length);

			Iterator<restriccion> f = datas.iterator();
			data.setRecordsFiltered(estadoProductorDB.getAllcountMercado(filter));
			data.setRecordsTotal(estadoProductorDB.getAllcountMercado(filter));

			while (f.hasNext()) {
				restriccion row = f.next();
				String[] d = { row.getCodProductor(),row.getMercado(),row.getEspecie()};

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
		


	}
}
