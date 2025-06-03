package lib.data.json.informes;
import java.io.IOException;
import java.io.OutputStream;

import java.nio.charset.Charset;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.data.json.dataTable;

import lib.db.MercadoDB;
import lib.db.ProductorDB;
import lib.db.TemporadaDB;

import lib.db.especieDB;
import lib.db.estadoProductorDB;
import lib.db.estadoProductorNewDB;
import lib.db.informesDB;
import lib.db.jerarquiaDB;

import lib.sap.Sync;
import lib.security.session;
import lib.struc.Mercado;
import lib.struc.Productor;

import lib.struc.especie;
import lib.struc.filterSql;


@Controller
public class estadoProductorJson {
	

	
	
	@RequestMapping(value = "/detalleRest/{mercado}/{especie}/{productor}/{etapa}/{campo}/{turno}/{variedad}", method = { RequestMethod.GET })
	public void getData(HttpServletResponse response, @PathVariable("mercado") String mercado,
			@PathVariable("especie") String especie, @PathVariable("productor") String productor,
			@PathVariable("etapa") String etapa, @PathVariable("campo") String campo,
			@PathVariable("turno") String turno,@PathVariable("variedad") String variedad, HttpSession httpSession)
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
		TemporadaDB temp=new TemporadaDB();
		int idTemporada=temp.getMaxTemprada(espe.getPf());
		Mercado m =MercadoDB.getMercadoByName(mercado);
		variedad=variedad.replace("@", "'");
		html+="<table  width='65%'><tr><td width='70%' valign='top'>";
		html+="<b style='font-size:18px'>"+productor+": "+p.getNombre() +"</b>";
		html+="<table>";
		
		
		
		
		
		html+="<tr><td width='120px'>Etapa</td><td> "+etapa+"</td></tr>";
		html+="<tr><td>Campo</td><td> "+campo+"</td></tr>";
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

		html+="<tr><td width='150px'>Regla Molecula</td><td>"+ rMoleculas+rMoleculasVal+"</td></tr>";
		html+="<tr><td width='150px'>Regla Productor</td><td>"+ rProductor+"</td></tr>";
		html+="<tr><td width='150px'>Regla Porcentaje</td><td>"+ rPorcentaje+rPorcentajeVal+"</td></tr>";
		html+="<tr><td width='150px'>Regla Porc. ArfD</td><td>"+ rPorcentajeArfD+rPorcentajeArfDVal+"</td></tr>";
		
		String exporta="Y";
		
		estadoProductorNewDB dataDB=new estadoProductorNewDB();
		exporta=dataDB.getEstadoProductor(idTemporada,espe.getIdEspecie(),variedad.trim(),mercado,productor,etapa,campo,turno);
		if (exporta.equals("SI") || exporta.equals("SI."))
			html+="<tr><td>Habilitado</td><td bgcolor='green' align='center'>SI</td></tr>";
		else
			html+="<tr><td>Habilitado</td><td bgcolor='red' align='center'>NO</td></tr>";
		
		
		
		
		html+="</table>";
		html+="</td></tr></table><br><br><br>";
		
		ArrayList<ArrayList<String>> habilitacionComercial= dataDB.getHabilitadoComercial(idTemporada, espe.getIdEspecie(), variedad.trim(), mercado, productor, etapa, campo, turno);
		
		if (!habilitacionComercial.isEmpty())
			html+="<b>Habilitacion Comercial</b><br>";
		
		ArrayList<ArrayList<String>> habilitacionManual= dataDB.getHabilitadoManual(idTemporada, espe.getIdEspecie(), variedad.trim(), mercado, productor, etapa, campo, turno);
		
		if (!habilitacionManual.isEmpty())
			html+="<b>Habilitacion Manual</b><br>";
		
		
		ArrayList<ArrayList<String>> bkCLP= dataDB.getBloqueoClp(idTemporada, espe.getIdEspecie(), variedad.trim(), mercado, productor, etapa, campo, turno);
		
		if (!bkCLP.isEmpty())
			html+="<b>Bloqueado CLP</b><br>";
		
		
		
		ArrayList<ArrayList<String>> parcela= dataDB.getBloackParcela(idTemporada, espe.getIdEspecie(), variedad.trim(), mercado, productor, etapa, campo, turno);
		
		if (!parcela.isEmpty())
			html+="<b>Bloqueo Etapa</b>";
		
		ArrayList<ArrayList<String>> bkMercado= dataDB.getBloackMercado(idTemporada, espe.getIdEspecie(), variedad.trim(), mercado, productor, etapa, campo, turno);
		
		if (!bkMercado.isEmpty())
			html+="<b>Bloqueo Mercado</b>";
		
		if (!rPorcentaje.equals("No"))
			html+=dataDB.getBlockPorcentaje(idTemporada, espe.getIdEspecie(), variedad.trim(), mercado, productor, etapa, campo, turno);
			html+="<br>";
		if (!rPorcentajeArfD.equals("No"))
			html+=estadoProductorDB.getBlockPorcentajeArfD(idTemporada,espe.getIdEspecie(),(variedad.trim()),m.getIdMercado()+"",productor,etapa,turno);
			html+="<br>";
		
		if (!rMoleculas.equals("No"))
		html+=dataDB.getBlockMolecula(idTemporada,espe.getIdEspecie(),variedad.trim(),mercado,productor,etapa,campo,turno);
		html+="<br>";
		
		
		
		informesDB info=new informesDB();
		
		html += "<div style='width:70%; text-align: center;'><b  style='font-size:18px'>Resultado de pesticidas("+idTemporada+")</b></div>";
		//html += "<b  style='font-size:18px'>Carga Automatica</b>";
		html+=info.getDetalleRestriccion(idTemporada,espe.getIdEspecie(),(variedad.trim()),m.getIdMercado()+"", productor,etapa,campo,turno,false);
		
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
	@RequestMapping(value = "/detalleRest/{mercado}/{especie}/{productor}/{etapa}/{campo}/{variedad}", method = { RequestMethod.GET })
	public void getDataParcela(HttpServletResponse response, @PathVariable("mercado") String mercado,
			@PathVariable("especie") String especie, @PathVariable("productor") String productor,
			@PathVariable("etapa") String etapa,@PathVariable("campo") String campo, 
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
		TemporadaDB temp=new TemporadaDB();
		
		Productor p=ProductorDB.getProductor(productor);
		especie espe= especieDB.getId(especie);
		
		int idTemporada=temp.getMaxTemprada(espe.getPf());
		
		Mercado m =MercadoDB.getMercadoByName(mercado);
		
		variedad=variedad.replace("@", "'");
		
		
		html+="<table  width='65%'><tr><td width='70%' valign='top'>";
		html+="<b style='font-size:18px'>"+productor+": "+p.getNombre() +"</b>";
		html+="<table>";
		
		
		
		
		
		html+="<tr><td width='120px'>Etapa</td><td> "+etapa+"</td></tr>";
		html+="<tr><td>Campo</td><td> "+campo+"</td></tr>";
		jerarquiaDB jerar=new jerarquiaDB();
		html+="<tr><td>Turnos</td><td> "+jerar.getTurnos(productor, etapa, campo, variedad)+"</td></tr>";
		
		html+="<tr><td>Variedad</td><td> "+variedad+"</td></tr>";
		html+="</table>";
		html+="</td><td  width='60%'>";
		html+="<table  width='350px'>";
		html+="<tr><td width='120px'>Mercado</td><td> "+mercado+"</td></tr>";
		html+="<tr><td>Especie</td><td width='220px'>"+ espe.getEspecie()+"</td></tr>";
		
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



		html+="<tr><td width='150px'>Regla Molecula</td><td>"+ rMoleculas+"</td></tr>";
		html+="<tr><td width='150px'>Regla Productor</td><td>"+ rProductor+"</td></tr>";
		html+="<tr><td width='150px'>Regla Porcentaje</td><td>"+ rPorcentaje+"</td></tr>";
		html+="<tr><td width='150px'>Regla Porc. ArfD</td><td>"+ rPorcentajeArfD+"</td></tr>";
		
		
		
		
		String exporta="Y";
		estadoProductorNewDB dataDB=new estadoProductorNewDB();
		
		exporta=dataDB.getEstadoProductor(idTemporada,espe.getIdEspecie(),variedad.trim(),m.getMercado(),productor,etapa, campo,"");
		if (exporta.equals("SI") || exporta.equals("SI."))
			html+="<tr><td>Habilitado</td><td bgcolor='green' align='center'>SI</td></tr>";
		else
			html+="<tr><td>Habilitado</td><td bgcolor='red' align='center'>NO</td></tr>";
		
		
		
		
		html+="</table>";
		html+="</td></tr></table><br><br><br>";
		
		
		ArrayList<ArrayList<String>> habilitacionComercial= dataDB.getHabilitadoComercial(idTemporada, espe.getIdEspecie(), variedad.trim(), mercado, productor, etapa, campo, "");
		
		if (!habilitacionComercial.isEmpty())
			html+="<b>Habilitacion Comercial</b><br>";
		
		
		ArrayList<ArrayList<String>> habilitacionManual= dataDB.getHabilitadoManual(idTemporada, espe.getIdEspecie(), variedad.trim(), mercado, productor, etapa, campo, "");
		
		if (!habilitacionManual.isEmpty())
			html+="<b>Habilitacion Manual</b><br>";
		
		
		ArrayList<ArrayList<String>> bkCLP= dataDB.getBloqueoClp(idTemporada, espe.getIdEspecie(), variedad.trim(), mercado, productor, etapa, campo, "");
		
		if (!bkCLP.isEmpty())
			html+="<b>Bloqueado CLP</b><br>";
		
		
		
		ArrayList<ArrayList<String>> parcela= dataDB.getBloackParcela(idTemporada, espe.getIdEspecie(), variedad.trim(), mercado, productor, etapa, campo, "");
		
		if (!parcela.isEmpty())
			html+="<b>Bloqueo Etapa</b>";
		
		ArrayList<ArrayList<String>> bkMercado= dataDB.getBloackMercado(idTemporada, espe.getIdEspecie(), variedad.trim(), mercado, productor, etapa, campo, "");
		
		if (!bkMercado.isEmpty())
			html+="<b>Bloqueo Mercado</b>";
		
		if (!rPorcentaje.equals("No"))
			html+=dataDB.getBlockPorcentaje(idTemporada, espe.getIdEspecie(), variedad.trim(), mercado, productor, etapa, campo, "");
			html+="<br>";
		if (!rPorcentajeArfD.equals("No"))
			html+=estadoProductorDB.getBlockPorcentajeArfD(idTemporada,espe.getIdEspecie(),(variedad.trim()),m.getIdMercado()+"",productor,etapa,"");
			html+="<br>";
		
		if (!rMoleculas.equals("No"))
		html+=dataDB.getBlockMolecula(idTemporada,espe.getIdEspecie(),variedad.trim(),mercado,productor,etapa,campo,"");
		html+="<br>";
		
		
		
		
		informesDB info=new informesDB();
		html += "<div style='width:70%; text-align: center;'><b  style='font-size:18px'>Resultado de pesticidas</b></div>";
		//html += "<b  style='font-size:18px'>Carga Automatica</b>";
		html+=info.getDetalleRestriccion(idTemporada,espe.getIdEspecie(),(variedad.trim()),m.getIdMercado()+"", productor,etapa,campo,"",true);
		
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

	@RequestMapping(value = "/estadoProductor/view/{especie}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable view(HttpServletRequest request,@PathVariable("especie") String idCultivo,HttpSession httpSession)  {
	
		dataTable data = new dataTable();
		
		data.init();
		data.setDraw(0);
		session ses = new session(httpSession);
		
		System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
		Map<String, String[]> parameters = request.getParameterMap();
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		int idEspecie=7;
		if (!idCultivo.isEmpty())
			idEspecie=Integer.parseInt(idCultivo);
		
		String productor = "";
		
		String etapa = "";
		String campo = "";
		String turno = "";
		String variedad = "";
		System.out.println("------------- ----------------- --------------- -------------- ------------------");
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
					if (key.equals("vw_etapa"))
					{
						try{
							etapa=val;
						}catch(Exception e)
						{}
					}
					if (key.equals("vw_campo"))
					{
						try{
							campo=val;
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
			estadoProductorNewDB estadoP=new estadoProductorNewDB();
			try {
				System.out.println("idEspecie: "+idEspecie+", etapa: "+etapa+", campo: "+campo+", turno: "+turno+"---productor: "+productor);
				ArrayList<String[]> pp=estadoP.getEstadoProductorA(ses.getIdTemporada(),idEspecie,productor,etapa,campo,turno,variedad,false,"");
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
	
	@RequestMapping(value = "/estadoProductor/view2/{especie}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable view2(HttpServletRequest request,@PathVariable("especie") String idCultivo,HttpSession httpSession)  {
	
		dataTable data = new dataTable();
		
		data.init();
		data.setDraw(0);
		session ses = new session(httpSession);
		
		
		System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
		Map<String, String[]> parameters = request.getParameterMap();
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		int idEspecie=7;
		if (!idCultivo.isEmpty())
			idEspecie=Integer.parseInt(idCultivo);
		String productor = "";
		String etapa = "";
		String campo = "";
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
					if (key.equals("vw_etapa"))
					{
						try{
							etapa=val;
						}catch(Exception e)
						{}
					}
					
					if (key.equals("vw_campo"))
					{
						try{
							campo=val;
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
		estadoProductorNewDB estadoP=new estadoProductorNewDB();
		if (!ses.isValid()) {
			
			data.setDraw(0);
			data.init();
			try {
				//System.out.println("idEspecie: "+idEspecie);
				ArrayList<String[]> pp=estadoP.getEstadoProductorB(ses.getIdTemporada(),idEspecie,productor,etapa,campo,variedad,false,"");
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
