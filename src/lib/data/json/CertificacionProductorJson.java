package lib.data.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import lib.db.CertificacionDB;
import lib.db.ProductorCertificacionDB;
import lib.db.ProductorDB;
import lib.db.TipoProductoDB;
import lib.security.session;
import lib.struc.Certificacion;
import lib.struc.Productor;
import lib.struc.ProductorCertificacion;
import lib.struc.TipoProducto;
import lib.struc.filterSql;
import lib.struc.mesajesJson;
@Controller
public class CertificacionProductorJson {
	@RequestMapping(value = "/ProductorCertificacion/view", method = { RequestMethod.POST, RequestMethod.GET })
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

		ArrayList<ProductorCertificacion> datas;
		try {
			datas = ProductorCertificacionDB.getProductorCertificacion(filter, "", start, length);

			Iterator<ProductorCertificacion> f = datas.iterator();
			data.setRecordsFiltered(ProductorCertificacionDB.getProductorCertificacionAll(filter));
			data.setRecordsTotal(ProductorCertificacionDB.getProductorCertificacionAll(filter));

			while (f.hasNext()) {
				ProductorCertificacion row = f.next();
				String[] d = { row.getCodProductor()+"",row.getCertificado(),row.getVigencia(),row.getIdCert()+"" };

				data.setData(d);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}
	
	@RequestMapping(value = "/ProductorCertificacion/insertProductorCertificacion" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertTipoProducto(@RequestBody ProductorCertificacion row,HttpSession httpSession) throws ParseException
	{
		boolean resp = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return resp;
		}
		resp = ProductorCertificacionDB.insertProductorCertificacion(row);
		return resp;		
	}
	
	@RequestMapping(value = "/ProductorCertificacion/{codigo}", method = { RequestMethod.GET })
	public @ResponseBody ProductorCertificacion getProductorCertificacionID(@PathVariable String codigo,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			ProductorCertificacion row=null;
			return row;
		}
		ProductorCertificacion row = ProductorCertificacionDB.getProdCert(Integer.parseInt(codigo));

		return row;

	}
	@RequestMapping(value = "/ProductorCertificacion/putx", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody mesajesJson setProductorCertificacion(@RequestBody ProductorCertificacion row,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		mesajesJson mensaje = new mesajesJson();
		if (ses.isValid()) {
			mensaje.setEstado("error");
			mensaje.setMensaje("Session terminada");
			return mensaje;
		}
		System.out.println("PUT::::::::::::::::::::::::::::");

		ProductorCertificacionDB.updateTipoProducto(row);
		

		mensaje.setEstado("ok");
		mensaje.setMensaje("Guardado con exito");

		return mensaje;

	}
	
	@RequestMapping(value = "/ProductorCertificacion/validaCertificacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean validaCertificacion(HttpServletRequest request, HttpSession httpSession){
		boolean resp = true;
		String codProductor = request.getParameter("codProd");
		String idCertificacion = request.getParameter("idCertificacion");
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return resp;
		}
		if(ProductorCertificacionDB.getCertificacionVigente(codProductor,idCertificacion)!=null)
		{
			resp = false;
		}
		return resp;
	}
	
	
	@RequestMapping(value = "/ProductorCertificacion/put", method = { RequestMethod.POST })
	public @ResponseBody mesajesJson setPfx(MultipartHttpServletRequest request, HttpServletRequest request2, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		mesajesJson mensaje = new mesajesJson();
		if (ses.isValid()) {
			mensaje.setEstado("error");
			mensaje.setMensaje("Session terminada");
			return mensaje;
		}

		System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
		Iterator<String> itr = request.getFileNames();

		MultipartFile mpf = request.getFile(itr.next());
		System.out.println(mpf.getOriginalFilename() + " uploaded!");

		BufferedReader br = new BufferedReader(new InputStreamReader(mpf.getInputStream(), "ISO-8859-1"));
		
		Workbook workbook = new XSSFWorkbook(mpf.getInputStream());
		Sheet datatypeSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = datatypeSheet.iterator();
		
		int i = 0;
		
		
		System.out.println(datatypeSheet.getSheetName());
		
		while (iterator.hasNext()) {
			Row currentRow = iterator.next();
			if (i > 0) {
				try {
					;
					System.out.println(currentRow.getCell(0).toString().replace(".0", ""));
					System.out.println(currentRow.getCell(1).toString());
					System.out.println(currentRow.getCell(2).getDateCellValue());
					SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

					// m.setFechaIngreso(currentRow.getCell(10).getDateCellValue());
					try {
						 Date fm=currentRow.getCell(2).getDateCellValue();
						 System.out.println(ft.format(fm));
						 ProductorCertificacion row = new ProductorCertificacion();
						 int cod=Integer.parseInt(currentRow.getCell(0).toString().replace(".0", ""));
						 Productor pro= ProductorDB.getProductor(cod+"");
						 row.setCodProductor(currentRow.getCell(0).toString().replace(".0", ""));
						 Certificacion cer= CertificacionDB.getCertificacionStr(currentRow.getCell(1).toString().trim());
						 row.setIdCertificacion(cer.getIdCertificaciones());
						 row.setVigencia(ft.format(fm));
						if (cer == null || pro == null) {
							System.out.println("No se ha encontrado productor o certificazdo");
						} else {
							ProductorCertificacionDB.insertProductorCertificacion(row);
							System.out.println("Insertado");
						}
						
					} catch (Exception ee) {
						// Date fm=ft.parse("01/01/1900");
						// m.setFechaMuestra(fm);
						System.out.println("ERRORORORRO");
					}
					

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
