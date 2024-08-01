package lib.sap;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.DestinationDataProvider;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import lib.io.ConfigProperties;

public class Sync {
	private static JCoDestination destination;
	
	
	public static String send(JSONArray arr,String tipo,String tipoServicio)
	{
		String res="";
		String html = "";
		try {
			Properties connectProperties = new Properties();
//			connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "/H/3.221.4.47/S/3299/H/10.166.165.95");
			connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, 	ConfigProperties.getProperty("JCO_ASHOST"));
			connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, 	ConfigProperties.getProperty("JCO_SYSNR"));
			connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, 	ConfigProperties.getProperty("JCO_CLIENT"));
			connectProperties.setProperty(DestinationDataProvider.JCO_USER, 	ConfigProperties.getProperty("JCO_USER"));
			connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, 	ConfigProperties.getProperty("JCO_PASSWD"));
			connectProperties.setProperty(DestinationDataProvider.JCO_LANG, 	ConfigProperties.getProperty("JCO_LANG"));
			//File destCfg = new File("eDte/sap/HF" + ".jcoDestination");
			File destCfg = new File("HF" + ".jcoDestination");
			//File destCfg = new File("HF2021" + ".jcoDestination");
			
			System.out.println(destCfg.getAbsolutePath());
			
			
			
			try {
				FileOutputStream fos = new FileOutputStream(destCfg, false);
				connectProperties.store(fos, "for tests only !");
				fos.close();
			} catch (Exception ee) {
				throw new RuntimeException("Unable to create the   destination files", ee);
			}
			destination = JCoDestinationManager.getDestination("HF");
			//System.out.println();
			
			//destination = JCoDestinationManager.getDestination("HF2021");
				} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "ERROR DE CONEXION";
			
		}
		
		System.out.println(123);
		try {
			JCoFunction function = destination.getRepository().getFunction("Z_MF99_LMR_SAP_CARGA");
			if (function == null) {

				return "Z_MF99_LMR_SAP_CARGA  not found in SAP.";
				

			}
			res=function.getFunctionTemplate().toString();
			
			String tipoStr="IT_MERCADO";
			String mercado="IV_MERCADO";
			
			if (tipo.equals("2"))
			{
				mercado="IV_CLIENTE";
				tipoStr="IT_CLIENTE";
			}
			
			function.getImportParameterList().setValue(mercado, "X");
			
			//ZTT_EXCEL_CARGA_INOCUIDAD_CLI
			JCoTable row=null;
			row = function.getImportParameterList().getTable(tipoStr);
			for(int i = 0; i < arr.length(); i++)
			{
				row.appendRow();
			      JSONObject o = arr.getJSONObject(i);
			      Iterator<String> keys = o.keys();
			      while(keys.hasNext()) {
			    	    String key = keys.next();
			    	    Object keyvalue = o.get(key);
			    	    row.setValue(key,keyvalue.toString());
			      
			      }
			}
			
			JSONObject data= new JSONObject();
			function.getImportParameterList().setValue(tipoStr, row);
			
			function.execute(destination);
			
			data = XML.toJSONObject((function.toXML().toString()));
			
			JSONObject OUTPUT= data.getJSONObject("Z_MF99_LMR_SAP_CARGA").getJSONObject("OUTPUT");
			System.out.println(OUTPUT);
			if(OUTPUT.optJSONObject("ET_LOG_CARGA") == null){
				html = "<html>"
						+ "<head></head>"
						+ "<body>"
						+ "<table border='1'>"
						+ "<tbody><tr><th>MESSAGE SAP</th></tr>";
				for(int i=0;i<OUTPUT.optJSONObject("ET_LOG_VALIDA").getJSONArray("item").length();i++){
					JSONObject jsonSap = OUTPUT.optJSONObject("ET_LOG_VALIDA").getJSONArray("item").getJSONObject(i);
					System.out.println(jsonSap.getString("MESSAGE"));
					html+="<tr><td>"+jsonSap.getString("MESSAGE")+"</td></tr>";
				}
				html += "</table><br>";
				html += "</body></html>";
			}else{
				System.out.println(OUTPUT.optJSONObject("ET_LOG_VALIDA"));
				JSONObject formData2 = new JSONObject();
				JSONObject formData = new JSONObject();
				formData.put("tipo", tipoServicio);
				formData.put("data", arr);
				formData2.put("cmd", "add");
				formData2.put("formData", formData);
				OkHttpClient client = new OkHttpClient().newBuilder().build();
				MediaType mediaType = MediaType.parse("application/json");
				RequestBody body = RequestBody.create(mediaType, formData2.toString());
				Request request = new Request.Builder()
					  .url(ConfigProperties.getProperty("syncSapService"))
					  .method("POST", body)
					  .addHeader("Content-Type", "application/json")
					  .build();
				Call call = client.newCall(request);
			    Response res2 = call.execute();
			    String jsonData = res2.body().string();
				System.out.println(jsonData.toString());
				
				html = "<html>"
						+ "<head></head>"
						+ "<body>"
						+ "<table border='1'>"
						+ "<tbody><tr><th>MESSAGE SAP</th></tr>";
						

				JSONArray resJson = null;
				if(OUTPUT.optJSONObject("ET_LOG_CARGA").optJSONArray("item") != null) {
					resJson = OUTPUT.optJSONObject("ET_LOG_CARGA").optJSONArray("item");
				}else {
					resJson = new JSONArray();
					resJson.put(OUTPUT.optJSONObject("ET_LOG_CARGA").optJSONObject("item"));
				}
				for(int i = 0; i < resJson.length(); i++){
					JSONObject jsonSap = resJson.getJSONObject(i);
					html+="<tr><td>"+jsonSap.getString("MESSAGE")+"</td></tr>";
				}
				if(OUTPUT.optJSONObject("ET_LOG_VALIDA") != null) {
					for(int i=0;i<OUTPUT.optJSONObject("ET_LOG_VALIDA").getJSONArray("item").length();i++){
						JSONObject jsonSap = OUTPUT.optJSONObject("ET_LOG_VALIDA").getJSONArray("item").getJSONObject(i);
						System.out.println(jsonSap.getString("MESSAGE_V2"));
						html+="<tr><td>"+jsonSap.getString("MESSAGE_V2")+"</td></tr>";
					}
				}
				
				html += "</table><br>";
				
				
				html+=	 "<h2>Resultado Exitoso("+tipo+")"
							+ "</h2><table border='1'>";
				
			
				
				JSONObject jsonObject = new JSONObject(jsonData.toString());
				JSONArray jsonArray = jsonObject.getJSONArray("errores");
				
				html +="<tbody><tr><th>MESSAGE CALIDAD</th></tr>"
				+"<tr><td>"+jsonObject.getString("message")+"</td></tr>"
				+"<tr><td>Registros procesados "+arr.length()+"</td></tr>";
				html += "</table><br>";
				
				// si existen errores
	            if(jsonArray.length() >=1){
	            	html += "<table border='1'><th>N Registro</th><th>Respuesta</th>";
	            }
				for(int i=0;i<jsonArray.length();i++){
			       
					JSONObject json = jsonArray.getJSONObject(i);
					
			        html +="<tr><td>"+json.getInt("nregistro")+"</td><td>"+json.getJSONObject("registro")+"</td></tr>";
			    }
				
				if(jsonArray.length() >=1){
					html += "</table>";
		        }
				
				html += "</body></html>";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			return "Error:"+e.getMessage()+"\n"+html;
		}
		
		
		return html;
	}

}
