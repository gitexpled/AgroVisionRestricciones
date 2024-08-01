package lib.data.json.informes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.data.json.dataTable;
import lib.db.CertificacionDB;
import lib.db.MercadoDB;
import lib.db.ProductorCertificacionDB;
import lib.db.ProductorDB;
import lib.db.bloqueadoDB;
import lib.db.bloqueadoOpDB;
import lib.db.especieDB;
import lib.db.estadoProductorDB;
import lib.db.informesDB;
import lib.db.mailDB;
import lib.sap.Sync;
import lib.security.session;
import lib.struc.Mercado;
import lib.struc.Productor;
import lib.struc.ProductorCertificacion;
import lib.struc.bloqueo;
import lib.struc.especie;
import lib.struc.filterSql;
import lib.struc.mail;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Controller
public class estadoProductorJson {
	

	
	
	@RequestMapping(value = "/detalleRest/{mercado}/{especie}/{productor}/{parcela}/{turno}/{variedad}", method = { RequestMethod.GET })
	public void getData(HttpServletResponse response, @PathVariable("mercado") String mercado,
			@PathVariable("especie") String especie, @PathVariable("productor") String productor,
			@PathVariable("parcela") String parcela, @PathVariable("turno") String turno,
			@PathVariable("variedad") String variedad, HttpSession httpSession)
			throws Exception {
		
		session ses = new session(httpSession);

		if (ses.isValid()) {
			OutputStream outputStream2;
			try {
				outputStream2 = response.getOutputStream();
				outputStream2.write("session terminada".getBytes(Charset.forName("iso-8859-1")));
				outputStream2.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ;
		}
		
		String html="";
			
		Productor p=ProductorDB.getProductor(productor);
		especie espe= especieDB.getId(especie);
		Mercado m =MercadoDB.getMercadoByName(mercado);
		html+="<table  width='65%'><tr><td width='80%' valign='top'>";
		html+="<b style='font-size:18px'>"+productor+": "+p.getNombre() +"</b>";
		html+="<table>";
		
		
		
		
		
		html+="<tr><td width='120px'>Parcela</td><td> "+parcela+"</td></tr>";
		html+="<tr><td>Turno</td><td> "+turno+"</td></tr>";
		html+="<tr><td>Variedad</td><td> "+variedad+"</td></tr>";
		html+="</table>";
		html+="</td><td  width='60%'>";
		html+="<table  width='350px'>";
		html+="<tr><td width='120px'>Mercado</td><td> "+mercado+" "+m.getIdMercadoPadre()+"("+m.getPorcentaje()+")</td></tr>";
		html+="<tr><td>Especie</td><td width='220px'>"+ espe.getEspecie()+"</td></tr>";
		
		String rMoleculas = "No";
		String rProductor = "No";
		
		String rMoleculasVal="";
		String rPorcentajeVal="";
		String rPorcentajeArfDVal="";
		
		if (m.getProductor().equals("Y"))
			rProductor = "Si";
		if (m.getRetricionMolecula().equals("Y"))
		{
			rMoleculas = "Si";
			rMoleculasVal="("+m.getMolecula()+")";
		}
		String rPorcentaje = "No";
		if (m.getRestPorcentaje().equals("Y"))
		{
			rPorcentaje = "Si";
			rPorcentajeVal="("+m.getRestValor()+")";
		}
		String rPorcentajeArfD = "No";
		if (m.getRestSumArfD().equals("Y"))
		{
			rPorcentajeArfD = "Si";
			rPorcentajeArfDVal="("+m.getRestValorArfD()+")     ArfD Individual("+m.getRestPorArfD() +")";
		}

		html+="<tr><td >Regla Molecula</td><td>"+ rMoleculas+rMoleculasVal+"</td></tr>";
		html+="<tr><td width='120px'>Regla Productor</td><td>"+ rProductor+"</td></tr>";
		html+="<tr><td width='120px'>Regla Porcentaje</td><td>"+ rPorcentaje+rPorcentajeVal+"</td></tr>";
		html+="<tr><td width='120px'>Regla Por ArfD</td><td>"+ rPorcentajeArfD+rPorcentajeArfDVal+"</td></tr>";
		
		String exporta="Y";
		
		
		exporta=estadoProductorDB.getEstadoProductor(ses.getIdTemporada(),espe.getIdEspecie(),(variedad.trim()),mercado,productor,parcela,turno);
		if (exporta.equals("SI"))
			html+="<tr><td>Habilitado</td><td bgcolor='green' align='center'>SI</td></tr>";
		else
			html+="<tr><td>Habilitado</td><td bgcolor='red' align='center'>NO</td></tr>";
		
		
		
		
		html+="</table>";
		html+="</td></tr></table><br><br><br>";
		
		if (!rPorcentaje.equals("No"))
			html+=estadoProductorDB.getBlockPorcentaje(ses.getIdTemporada(),espe.getIdEspecie(),(variedad.trim()),m.getIdMercado()+"",productor,parcela,turno);
			html+="<br>";
		if (!rPorcentajeArfD.equals("No"))
			html+=estadoProductorDB.getBlockPorcentajeArfD(ses.getIdTemporada(),espe.getIdEspecie(),(variedad.trim()),m.getIdMercado()+"",productor,parcela,turno);
			html+="<br>";
		
		if (!rMoleculas.equals("No"))
		html+=estadoProductorDB.getBlockMolecula(ses.getIdTemporada(),espe.getIdEspecie(),(variedad.trim()),m.getIdMercado()+"",productor,parcela,turno);
		html+="<br>";
		
		if (!rProductor.equals("No"))
			html+=estadoProductorDB.getBlockProductor(ses.getIdTemporada(),espe.getIdEspecie(),(variedad.trim()),m.getIdMercado()+"",productor,parcela,turno);
		

		
		html += "<div style='width:70%; text-align: center;'><b  style='font-size:18px'>Resultado de pesticidas</b></div>";
		html += "<b  style='font-size:18px'>Carga Automatica</b>";
		html+=informesDB.getDetalleRestriccion(espe.getIdEspecie(),(variedad.trim()),m.getIdMercado()+"", productor,parcela,turno);
		html += "<br/><b  style='font-size:18px'>Carga Manual</b>";
		html+=informesDB.getDetalleRestriccionM(espe.getIdEspecie(),(variedad.trim()),m.getIdMercado()+"", productor,parcela,turno);
		html+="";
	OutputStream outputStream2;
	try {
		outputStream2 = response.getOutputStream();
		response.setCharacterEncoding("UTF-8");
		
		
		outputStream2.write(html.getBytes("UTF-8"));
		outputStream2.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return;
	}
	@RequestMapping(value = "/detalleRest/{mercado}/{especie}/{productor}/{parcela}/{variedad}", method = { RequestMethod.GET })
	public void getDataParcela(HttpServletResponse response, @PathVariable("mercado") String mercado,
			@PathVariable("especie") String especie, @PathVariable("productor") String productor,
			@PathVariable("parcela") String parcela, 
			@PathVariable("variedad") String variedad, HttpSession httpSession)
			throws Exception {
		
		session ses = new session(httpSession);

		if (ses.isValid()) {
			OutputStream outputStream2;
			try {
				outputStream2 = response.getOutputStream();
				outputStream2.write("session terminada".getBytes(Charset.forName("iso-8859-1")));
				outputStream2.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ;
		}
		
		String html="......";
			
		Productor p=ProductorDB.getProductor(productor);
		especie espe= especieDB.getId(especie);
		Mercado m =MercadoDB.getMercadoByName(mercado);
		html+="<table  width='65%'><tr><td width='80%' valign='top'>";
		html+="<b style='font-size:18px'>..."+productor+": "+p.getNombre() +"</b>";
		html+="<table>";
		
				
		html+="<tr><td width='80px'>Parcela</td><td> "+parcela+"</td></tr>";
		//html+="<tr><td>Turno</td><td> "+turno+"</td></tr>";
		html+="<tr><td>Variedad</td><td> "+variedad+"</td></tr>";
		html+="</table>";
		html+="</td><td  width='20%'>";
		html+="<table>";
		html+="<tr><td>Mercado</td><td> "+mercado+"</td></tr>";
		html+="<tr><td>Especie</td><td>"+ espe.getEspecie()+"</td></tr>";
		
		String rMoleculas = "No";
		String rProductor = "No";
		if (m.getProductor().equals("Y"))
			rProductor = "Si";
		if (m.getRetricionMolecula().equals("Y"))
			rMoleculas = "Si";
		
		String rPorcentaje = "No";
		if (m.getRestPorcentaje().equals("Y"))
			rPorcentaje = "Si";
		
		String rPorcentajeArfD = "No";
		if (m.getRestSumArfD().equals("Y"))
			rPorcentajeArfD = "Si";



		html+="<tr><td >Regla Molecula</td><td>"+ rMoleculas+"</td></tr>";
		html+="<tr><td width='120px'>Regla Productor</td><td>"+ rProductor+"</td></tr>";
		html+="<tr><td width='120px'>Regla Porcentaje</td><td>"+ rPorcentaje+"</td></tr>";
		html+="<tr><td width='120px'>Regla Porcentaje ArfD</td><td>"+ rPorcentajeArfD+"</td></tr>";
		
		
		
		
		String exporta="Y";
		
		
		exporta=estadoProductorDB.getEstadoProductor(ses.getIdTemporada(),espe.getIdEspecie(),variedad.trim(),m.getMercado(),productor,parcela, "");
		if (exporta.equals("SI"))
			html+="<tr><td>Habilitado</td><td bgcolor='green' align='center'>SI</td></tr>";
		else
			html+="<tr><td>Habilitado</td><td bgcolor='red' align='center'>NO</td></tr>";
		
		
		
		
		html+="</table>";
		html+="</td></tr></table><br><br><br>";
		
		if (!rPorcentaje.equals("No"))
			html+=estadoProductorDB.getBlockPorcentaje(ses.getIdTemporada(),espe.getIdEspecie(),(variedad.trim()),m.getIdMercado()+"",productor,parcela,null);
			html+="<br>";
		if (!rPorcentajeArfD.equals("No"))
				html+=estadoProductorDB.getBlockPorcentajeArfD(ses.getIdTemporada(),espe.getIdEspecie(),(variedad.trim()),m.getIdMercado()+"",productor,parcela,null);
				html+="<br>";
		
		if (!rMoleculas.equals("No"))
		html+=estadoProductorDB.getBlockMolecula(ses.getIdTemporada(),espe.getIdEspecie(),(variedad.trim()),m.getIdMercado()+"",productor,parcela,null);
		html+="<br>";
		if (!rProductor.equals("No"))
		html+=estadoProductorDB.getBlockProductor(ses.getIdTemporada(),espe.getIdEspecie(),(variedad.trim()),mercado,productor,parcela,null);
		
		
		
		

		
		html += "<div style='width:70%; text-align: center;'><b  style='font-size:18px'>Resultado de pesticidas</b></div>";
		html += "<b  style='font-size:18px'>Carga Automatica</b>";
		html+=informesDB.getDetalleRestriccion2(espe.getIdEspecie(),(variedad.trim()),m.getIdMercado()+"", productor,parcela);
		html += "<br/><b  style='font-size:18px'>Carga Manual</b>";
		html+=informesDB.getDetalleRestriccionM2(espe.getIdEspecie(),(variedad.trim()),m.getIdMercado()+"", productor,parcela);
		html+="";
	OutputStream outputStream2;
	try {
		outputStream2 = response.getOutputStream();
		response.setCharacterEncoding("UTF-8");
		
		
		outputStream2.write(html.getBytes("UTF-8"));
		outputStream2.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return;
	}
	@RequestMapping(value = "/cargaAutomatica/test", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable getTest(HttpServletRequest request,HttpSession httpSession)  {
		System.out.println("hola mundo cruel");
		dataTable data = new dataTable();
		
		data.init();
		data.setDraw(0);
		session ses = new session(httpSession);
		try {
			ArrayList<String[]> pp=estadoProductorDB.getEstadoProductorA(ses.getIdTemporada(),1,"","","","",false,"");
			data.setDatas(pp);
			data.setRecordsFiltered(pp.size());
			data.setRecordsTotal(pp.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}

	@RequestMapping(value = "/estadoProductor/view", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable view(HttpServletRequest request,HttpSession httpSession)  {
	
		dataTable data = new dataTable();
		
		data.init();
		data.setDraw(0);
		session ses = new session(httpSession);
		
		
		System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
		Map<String, String[]> parameters = request.getParameterMap();
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		int idEspecie=1;
		String productor = "";
		
		String parcela = "";
		String turno = "";
		String variedad = "";
		System.out.println("------------- ----------------- --------------- -------------- ------------------");
		for (String key : parameters.keySet()) {
			System.out.println(key);
			if (key.startsWith("vw_")) {
				String[] vals = parameters.get(key);
				for (String val : vals) {
					System.out.println(key+" -> " + val);
					filterSql fil = new filterSql();
					fil.setCampo(key.substring(3));
					fil.setValue(val);
					filter.add(fil);
					
					if (key.equals("vw_especie"))
					{
						try{
						idEspecie=Integer.parseInt(val);
						}catch(Exception e)
						{}
					}
					if (key.equals("vw_productor"))
					{
						try{
						productor=val;
						}catch(Exception e)
						{}
					}
					if (key.equals("vw_parcela"))
					{
						try{
							parcela=val;
						}catch(Exception e)
						{}
					}
					if (key.equals("vw_turno"))
					{
						try{
							turno=val;
						}catch(Exception e)
						{}
					}
					if (key.equals("vw_variedad"))
					{
						try{
							variedad=val;
						}catch(Exception e)
						{}
					}
				}
			}
		}
		System.out.println("ses.isValid()"+ses.isValid());
		
		if (!ses.isValid()) {
			
			data.setDraw(0);
			data.init();
			try {
				System.out.println("idEspecie: "+idEspecie+"---productor: "+productor);
				ArrayList<String[]> pp=estadoProductorDB.getEstadoProductorA(ses.getIdTemporada(),idEspecie,productor,parcela,turno,variedad,false,"");
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
	
	@RequestMapping(value = "/estadoProductor/view2", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable view2(HttpServletRequest request,HttpSession httpSession)  {
	
		dataTable data = new dataTable();
		
		data.init();
		data.setDraw(0);
		session ses = new session(httpSession);
		
		
		System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
		Map<String, String[]> parameters = request.getParameterMap();
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		int idEspecie=1;
		String productor = "";
		String parcela = "";
		String turno = "";
		String variedad = "";
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
					
					if (key.equals("vw_especie"))
					{
						try{
						idEspecie=Integer.parseInt(val);
						}catch(Exception e)
						{}
					}
					if (key.equals("vw_productor"))
					{
						try{
						productor=val;
						}catch(Exception e)
						{}
					}
					if (key.equals("vw_parcela"))
					{
						try{
							parcela=val;
						}catch(Exception e)
						{}
					}
					if (key.equals("vw_turno"))
					{
						try{
							turno=val;
						}catch(Exception e)
						{}
					}
					if (key.equals("vw_variedad"))
					{
						try{
							variedad=val;
						}catch(Exception e)
						{}
					}
				}
			}
		}
		System.out.println("ses.isValid()"+ses.isValid());
		
		if (!ses.isValid()) {
			
			data.setDraw(0);
			data.init();
			try {
				System.out.println("idEspecie: "+idEspecie);
				ArrayList<String[]> pp=estadoProductorDB.getEstadoProductorB(ses.getIdTemporada(),idEspecie,productor,parcela,variedad,false);
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
	
	
	
	@RequestMapping(value = "/estadoProductorPro/view", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable getProduccionPO(HttpServletRequest request,HttpSession httpSession)  {
	
		dataTable data = new dataTable();
		
		data.init();
		data.setDraw(0);
		session ses = new session(httpSession);
		
		
		System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
		Map<String, String[]> parameters = request.getParameterMap();
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		int idEspecie=1;
		String productor = "";
		String nombre = "";
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
					
					if (key.equals("vw_especie"))
					{
						try{
						idEspecie=Integer.parseInt(val);
						}catch(Exception e)
						{}
					}
					if (key.equals("vw_productor"))
					{
						try{
						productor=val;
						}catch(Exception e)
						{}
					}
					if (key.equals("vw_nombre"))
					{
						try{
						nombre=val;
						}catch(Exception e)
						{}
					}
				}
			}
		}
		System.out.println("ses.isValid()"+ses.isValid());
		
		if (!ses.isValid()) {
			
			data.setDraw(0);
			data.init();
			try {
				System.out.println("idEspecie: "+idEspecie);
				ArrayList<String[]> pp=estadoProductorDB.getEstadoProductorPro(ses.getIdTemporada(),idEspecie,productor,nombre);
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
	@RequestMapping(value = "/SyncSAP/{tipo}", method = { RequestMethod.GET })
	public void SyncSAP(HttpServletResponse response, @PathVariable("tipo") String tipo, HttpSession httpSession)
			throws Exception {
		
		session ses = new session(httpSession);
		String html="";
		if (ses.isValid()) {
			OutputStream outputStream2;
			try {
				outputStream2 = response.getOutputStream();
				outputStream2.write("session terminada".getBytes(Charset.forName("iso-8859-1")));
				outputStream2.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ;
		}
	    String tipoServicio = "mercado";
	    String cliente = "N";
	    if (tipo.equals("2")) {
	      cliente = "Y";
	      tipoServicio = "cliente";
	    } 
			
		JSONArray r=estadoProductorDB.getEstadoProductorSyncSAP(ses.getIdTemporada(), 1, "", cliente, false);
		
		//String html="";

		html=Sync.send(r,tipo,tipoServicio);

//		html=Sync.send(r,tipo);
//		JSONObject formData2 = new JSONObject();
//		JSONObject formData = new JSONObject();
//		formData.put("tipo", tipoServicio);
//		formData.put("data", r);
//		formData2.put("cmd", "add");
//		formData2.put("formData", formData);
//		OkHttpClient client = new OkHttpClient().newBuilder().build();
//		MediaType mediaType = MediaType.parse("application/json");
//		RequestBody body = RequestBody.create(mediaType, formData2.toString());
//		Request request = new Request.Builder()
//			  .url("http://hortiqa.hortifrut.com:8000/sap/xsmc/ag_managerLMR.xsjs")
//			  .method("POST", body)
//			  .addHeader("Content-Type", "application/json")
//			  .build();
//		Call call = client.newCall(request);
//	    Response res = call.execute();
//	    String jsonData = res.body().string();
//		System.out.println(jsonData.toString());

		
		for(int i = 0; i < r.length(); i++)
		{
		      JSONObject o = r.getJSONObject(i);
		      Iterator<String> keys = o.keys();
		      while(keys.hasNext()) {
		    	    String key = keys.next();
		    	    Object keyvalue = o.get(key);
		      //html+=key+"-->"+keyvalue+"\n";
		      
		      }
		}
		html+="";
	OutputStream outputStream2;
	try {
		outputStream2 = response.getOutputStream();
		response.setCharacterEncoding("UTF-8");
		
		
		outputStream2.write(html.getBytes("UTF-8"));
		outputStream2.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return;
	}
}
