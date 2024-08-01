package lib.data.json.informes;

import java.util.ArrayList;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.data.json.dataTable;

import lib.db.estadoProductorDB;
import lib.db.exportarSapDB;
import lib.security.session;

import lib.struc.filterSql;


@Controller
public class exportarSapJson {
	

	


	@RequestMapping(value = "/exportarSap/view", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable view(HttpServletRequest request,HttpSession httpSession)  {
	
		dataTable data = new dataTable();
		
		data.init();
		data.setDraw(0);
		session ses = new session(httpSession);
		
		
		System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
		Map<String, String[]> parameters = request.getParameterMap();
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		int idEspecie=1;
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
		System.out.println("ses.isValid()"+ses.isValid());
		System.out.println("asdadasdasd");
		if (!ses.isValid()) {
			
			data.setDraw(0);
			data.init();
			try {
				
				ArrayList<String[]> pp=exportarSapDB.view(ses.getIdTemporada());
				data.setDatas(pp);
				data.setRecordsFiltered(pp.size());
				data.setRecordsTotal(pp.size());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return data;
		


	}
}
