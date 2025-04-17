package lib.data.json;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.record.aggregates.MergedCellsTable;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import lib.db.CertificacionDB;
import lib.db.DiccionarioDB;
import lib.db.FuenteDB;
import lib.db.LimiteDB;
import lib.db.MercadoDB;
import lib.db.ProductorCertificacionDB;
import lib.db.ProductorDB;
import lib.db.TipoProductoDB;
import lib.db.especieDB;
import lib.security.session;
import lib.struc.Certificacion;
import lib.struc.Diccionario;
import lib.struc.Limite;
import lib.struc.LimiteExcel;
import lib.struc.Mercado;
import lib.struc.Productor;
import lib.struc.ProductorCertificacion;
import lib.struc.filterSql;
import lib.struc.mesajesJson;
import org.json.JSONArray;
import org.json.JSONObject;
@Controller
public class LimiteJson {
	private ArrayList<Mercado> listado = new ArrayList<Mercado>();
	@RequestMapping(value = "/limite/put", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)

	public @ResponseBody mesajesJson setLimite(@RequestBody Limite row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		mesajesJson mensaje = new mesajesJson();
		if (ses.isValid()) {
			mensaje.setEstado("error");
			mensaje.setMensaje("Session terminada");
			return mensaje;
		}
		LimiteDB.updateLimite(row);

		mensaje.setEstado("ok");
		mensaje.setMensaje("Guardado con exito");

		return mensaje;

	}

	@RequestMapping(value = "/limite/{id}", method = { RequestMethod.GET })
	public @ResponseBody Limite getLimiteId(@PathVariable String id,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			Limite row=null;
			return row;
		}

		Limite row = LimiteDB.getLimite(id);

		return row;

	}
	
	@RequestMapping(value = "/limite/delete/{id}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> deleteLMR(@PathVariable String id, HttpSession httpSession) {
	    Map<String, Object> response = new HashMap<>();
	    session ses = new session(httpSession);

	    if (ses.isValid()) {
	        response.put("success", false);
	        response.put("message", "Sesión no válida.");
	        return response;
	    }

	    try {
	        boolean eliminado = LimiteDB.deleteLimite(id);
	        if (eliminado) {
	            response.put("success", true);
	            response.put("message", "Límite eliminado correctamente.");
	        } else {
	            response.put("success", false);
	            response.put("message", "No se encontró el límite o ya estaba eliminado.");
	        }
	    } catch (Exception e) {
	        response.put("success", false);
	        response.put("message", "Error al eliminar el límite: " + e.getMessage());
	    }

	    return response;
	}


	@RequestMapping(value = "/limite/view", method = { RequestMethod.POST, RequestMethod.GET })
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


		case "0":colum = "m.mercado";break;
		case "1":colum = "idEspecie";break;
		case "2":colum = "limite";break;
		case "3":colum = "l.codProducto";break;
		case "4":colum = "f.nombre";break;
	
		case "5":colum = "l.creado";break;
		case "6":colum = "l.modificacion";break;
		}
		
		order = colum + ":" + dir;
		System.out.println(order);

		Map<String, String[]> parameters = request.getParameterMap();
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		for (String key : parameters.keySet()) {
			//System.out.println(key);
			if (key.startsWith("vw_")) {
				String[] vals = parameters.get(key);
				for (String val : vals) {
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

		ArrayList<Limite> datas;
		try {
			datas = LimiteDB.getLimites(filter, order, start, length);

			Iterator<Limite> f = datas.iterator();

			data.setRecordsFiltered(LimiteDB.getLimitesAll(filter));
			data.setRecordsTotal(LimiteDB.getLimitesAll(filter));
			
			while (f.hasNext()) {
				Limite row = f.next();
				String[] d = {row.getMercado() , 
						especieDB.getId(row.getIdEspecie()+"").getEspecie(),
						row.getLimite(), 
						row.getCodProducto(),
						
						row.getFuente() , 
						row.getCreado() + "", row.getModificado()+"", row.getIdLimite()+"", "" };

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}
	@RequestMapping(value = "/limite/cargaMasiva" , method= {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean cargaMasiva(@RequestBody ArrayList<LimiteExcel> rows,HttpSession httpSession) throws ParseException
	{
		boolean resp = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return resp;
		}
		 return LimiteDB.upsertBatchLimites(rows, ses.getIdUser());
	}
	
	@RequestMapping(value = "/limite/insertLimite" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertLimite(@RequestBody Limite row,HttpSession httpSession) throws ParseException
	{
		boolean resp = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return resp;
		}
		resp = LimiteDB.insertLimite(row,ses.getIdUser());
		return resp;		
	}
	
	private int getCountMergeCell(Sheet h, int p1, int p2)
	{
		int num=0;
		int nbrMergedRegions = h.getNumMergedRegions();
         
        for(int i = 0; i < nbrMergedRegions-1; i++){
             
          
             
            if(h.getMergedRegion(i) != null ){
            	CellRangeAddress mer= h.getMergedRegion(i);
            	//System.out.println(mer.getFirstColumn());
            	//System.out.println(mer.getFirstRow());
            	//System.out.println(mer.getLastColumn());
            	//System.out.println(mer.getLastRow());
                 
            	
            	if (mer.getFirstColumn()==p1 && mer.getFirstRow()==p2)
            	{
            		
            		//System.out.println(":::::::::::::::::::::::::"+mer.getNumberOfCells());
            		
            		num=mer.getNumberOfCells();
            		Row currentRow=h.getRow(mer.getFirstRow());
            		
            		//System.out.println(":::::::::::::::::::::::::"+currentRow.getCell(mer.getFirstColumn()).getStringCellValue());
            	}
             
            }
          
        }
        return num;
	}
	private  static String largo(String txt, int l)
	{
		return txt.substring(0, Math.min(l, txt.length()));
	}
	
	@RequestMapping(value = "/limite/validaLimite", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody mesajesJson validaLimite(@RequestBody Limite lim, HttpSession httpSession)
	{
		mesajesJson resp = new mesajesJson();
		resp.setEstado("OK");
		resp.setMensaje("");
		session ses = new session(httpSession);
		if(ses.isValid())
		{
			resp.setEstado("NOK");
			resp.setMensaje("Session caducada");
			return resp;
		}
		Limite limite = LimiteDB.validaLimite(lim);
		if(limite!=null)
		{
			resp.setEstado("NOK");
			resp.setMensaje("Ya existe un limite con caracteristicas similares para este mercado(limite"+limite.getLimite()+"), favor ingrese distintos valores");
		}
		return resp;
	}
	
	@RequestMapping(value = "/limite/put", method = { RequestMethod.POST })
	public @ResponseBody mesajesJson uploadExcelLimite(MultipartHttpServletRequest request, HttpServletRequest request2, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		mesajesJson mensaje = new mesajesJson();
		if (ses.isValid()) {
			mensaje.setEstado("error");
			mensaje.setMensaje("Session terminada");
			return mensaje;
		}

		System.out.println("GET:::::::::::::::::::::::::::::::::::::::: "+request2.getParameter("idEspecie").trim());
		Iterator<String> itr = request.getFileNames();

		MultipartFile mpf = request.getFile(itr.next());
		System.out.println(mpf.getOriginalFilename() + " uploaded!");

		BufferedReader br = new BufferedReader(new InputStreamReader(mpf.getInputStream(), "ISO-8859-1"));
		
		Workbook workbook = new XSSFWorkbook(mpf.getInputStream());
		Sheet datatypeSheet = workbook.getSheetAt(0);
		
		int numRead=getCountMergeCell(datatypeSheet,1,3);
		System.out.println(numRead);
		
		
		
		// LEER LARGO DEL EXCEL
		Row rowCod = datatypeSheet.getRow(4);
		
		for (int i = 6; i < 100; i++) {
			// LEEMOS EL ARCHIVO SEGUN NUMREAD
			System.out.println();
			Row row = datatypeSheet.getRow(i);
			
			int numFin=getCountMergeCell(datatypeSheet,1,i);
			if (numFin==numRead)
				break;
			if (numFin<2) {
				String  codProducto= row.getCell(1).getStringCellValue().trim();
				System.out.print(String.format("%-22s",largo(row.getCell(1).getStringCellValue(),20)));
				if (DiccionarioDB.getDiccionarioStr(row.getCell(1).getStringCellValue().trim())==null)
				{
					System.out.println("CREO: "+String.format("%-22s",largo(row.getCell(1).getStringCellValue(),20)));
					Diccionario o= new Diccionario();
					o.setCodProducto(row.getCell(1).getStringCellValue().trim());
					o.setCodReemplazo(row.getCell(1).getStringCellValue().trim());
					o.setCreado(new Date());
					o.setModificado(new Date());
					o.setIdUser(ses.getIdUser());
					DiccionarioDB.insertDiccionario(o);
				}
			//CRE
			
				for (int j = 2; j < numRead; j = j + 2) {
					String mercado = rowCod.getCell(j).getStringCellValue().trim();
					
					

					Mercado oMercado3 = MercadoDB.getMercadoStr(mercado);
					if (oMercado3 != null) {
						System.out.print(String.format("%-22s", largo(mercado, 20)));
						//CONSUMIMOS POR LISTADO
						listado = MercadoDB.getMercadoStrArray(mercado);
						for (Mercado oMercado : listado) {
							String value = row.getCell(j).getStringCellValue();
							value = value.replace(",", ".");
							value = value.replace("(a)", "");
							value = value.replace("(b)", "");
							value = value.replace("(c)", "");
							value = value.replace("(d)", "");
							value = value.replace("(e)", "");
							value = value.replace("TI", "");
							value = value.replace(" T", "");
							value = value.replace("P", "");
							value = value.replace("#", "");
							value = value.trim();
							value = value.replace("ST", "0");
							value = value.replace("EX", "0");
						
							// value=value.replace("()", "");
							System.out.print(" " + String.format("%-5s", largo(value, 5)));
							Limite lim = new Limite();
							lim.setCodProducto(codProducto);
							lim.setCreado(new Date());
							lim.setModificado(new Date());
							int idEspecie = Integer.parseInt(request2.getParameter("idEspecie").trim());
							lim.setIdEspecie(idEspecie);
							lim.setFuente(request2.getParameter("idFuente").trim());
							int idFuente = Integer.parseInt(request2.getParameter("idFuente").trim());
							lim.setIdFuente(idFuente);
							lim.setIdMercado(oMercado.getIdMercado());
							int tipo = Integer.parseInt(request2.getParameter("idTipoProducto").trim());
							lim.setIdTipoProducto(tipo);
							lim.setLimite(value);

							Limite limVal = LimiteDB.validaLimiteExcel(lim);
							if (limVal == null) {

								LimiteDB.insertLimite(lim,ses.getIdUser());
								if (lim.getIdMercado() == 1) {
									lim.setIdMercado(15);
									LimiteDB.insertLimite(lim,ses.getIdUser());

								}
							} else {

								Limite limVal2 = LimiteDB.validaLimiteExcel2(lim);
								if (limVal2 == null) {
									lim.setIdMercado(15);
									LimiteDB.insertLimite(lim,ses.getIdUser());

								} else {
									limVal2.setIdEspecie(idEspecie);
									limVal2.setLimite(value);
									limVal2.setModificado(new Date());
									LimiteDB.updateLimite(limVal2);
								}

								limVal.setIdEspecie(idEspecie);
								limVal.setLimite(value);
								limVal.setModificado(new Date());
								LimiteDB.updateLimite(limVal);
							}

						}
					} else if (mercado.startsWith("CHINA - PROPUESTAS CHINAS") || mercado.startsWith("CODEX")) {
						Mercado oMercado2 = MercadoDB.getMercadoStr("CHINA");

						System.out.print("China insert 2");

						

						String value = row.getCell(j).getStringCellValue();
						value = value.replace(",", ".");
						value = value.replace("(a)", "");
						value = value.replace("(b)", "");
						value = value.replace("(c)", "");
						value = value.replace("(d)", "");
						value = value.replace("(e)", "");
						value = value.replace("TI", "");
						value = value.replace(" T", "");
						value = value.replace("P", "");
						value = value.replace("#", "");
						value = value.trim();
						value = value.replace("ST", "0");
						value = value.replace("EX", "0");
						// value=value.replace("()", "");
						System.out.print(" " + String.format("%-5s", largo(value, 5)));
						Limite lim = new Limite();
						lim.setCodProducto(codProducto);
						lim.setCreado(new Date());
						lim.setModificado(new Date());
						int idEspecie = Integer.parseInt(request2.getParameter("idEspecie").trim());
						lim.setIdEspecie(idEspecie);
						lim.setFuente(request2.getParameter("idFuente").trim());
						int idFuente = Integer.parseInt(request2.getParameter("idFuente").trim());
						lim.setIdFuente(idFuente);
						lim.setIdMercado(oMercado2.getIdMercado());
						int tipo = Integer.parseInt(request2.getParameter("idTipoProducto").trim());
						lim.setIdTipoProducto(tipo);
						lim.setLimite(value);

						if (value.length() == 0)
							value = "0";

						if (!value.equals("0")) {

							Limite limVal = LimiteDB.validaLimiteExcelChina(lim);
							if (limVal != null) {
								limVal.setIdEspecie(idEspecie);
								limVal.setLimite(value);
								limVal.setModificado(new Date());
								LimiteDB.updateLimite(limVal);
							} else {
								Limite limVal2 = LimiteDB.validaLimiteExcelChinaMenor(lim);
								if (limVal2 != null) {
									limVal2.setIdEspecie(idEspecie);
									limVal2.setLimite(value);
									limVal2.setModificado(new Date());
									LimiteDB.updateLimite(limVal2);
								}
							}

						}
					}
				} // FOR producto
			}	
			
		}
		
		Iterator<Row> iterator = datatypeSheet.iterator();
		
		int i = 0;

		System.out.println(datatypeSheet.getSheetName());
		
		while (iterator.hasNext()) {
			Row currentRow = iterator.next();
			if (i > 0) {
				try {
					;
					
					
					SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

					// m.setFechaIngreso(currentRow.getCell(10).getDateCellValue());
					/*
					try {
						 Date fm=currentRow.getCell(2).getDateCellValue();
						 System.out.println(ft.format(fm));
						 ProductorCertificacion row = new ProductorCertificacion();
						 int cod=Integer.parseInt(currentRow.getCell(0).toString().replace(".0", ""));
						 row.setCodProductor(cod);
						 Certificacion cer= CertificacionDB.getCertificacionStr(currentRow.getCell(1).toString().trim());
						 row.setIdCertificacion(cer.getIdCertificaciones());
						 row.setVigencia(ft.format(fm));
						 
						 ProductorCertificacionDB.insertProductorCertificacion(row);
						 System.out.println("Insertado");
					} catch (Exception ee) {
						// Date fm=ft.parse("01/01/1900");
						// m.setFechaMuestra(fm);
						System.out.println("ERRORORORRO");
					}
					*/

				} catch (Exception e) {
					e.printStackTrace();
					return mensaje;
				}
			}
			++i;


		

		

		}
		mensaje.setEstado("ok");
		mensaje.setMensaje("Guardado con exito");

		return mensaje;
	}
	
	
}
