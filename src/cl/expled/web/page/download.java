package cl.expled.web.page;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;


import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lib.data.json.dataTable;
//import lib.db.dteCompraDB;
//import lib.db.libroDB;
import lib.io.config;
import lib.security.session;
import lib.security.tripleDes;

@Controller
public class download {

//	@RequestMapping(value = "/download/{type}/{tipoDte}/{rute}/{rutm}/{folio}", method = RequestMethod.GET)
//	public void downloadFile(HttpServletResponse response, @PathVariable("type") String type,
//			@PathVariable("tipoDte") String tipoDte,@PathVariable("rute") String rute,@PathVariable("rutm") String rutm,@PathVariable("folio") String folio, HttpSession httpSession) throws Exception {
//
//		session ses = new session(httpSession);
//		
//		if (ses.isValid()) {
//
//			String errorMessage = "Session terminada ";
//			OutputStream outputStream = response.getOutputStream();
//			outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
//			outputStream.close();
//			return;
//		}
//
//		File file = null;
//		String contenedor=config.getProperty("rutaDteRecibidosNew");
//		
//		String[] arr = lib.db.dteCompraDB.getFileFolder(rutm, tipoDte + "", folio + "");
//		String path = contenedor+arr[1].toString() + "/" + arr[0].toString() ;
//		if (type.equals("pdf"))
//			file = new File(path + ".pdf");
//		else
//			file = new File(path + ".xml");
//
//		if (!file.exists()) {
//			String errorMessage = "Archivo no existe " + path;
//			System.out.println(errorMessage);
//			OutputStream outputStream = response.getOutputStream();
//			outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
//			outputStream.close();
//			return;
//		}
//
//		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
//		if (mimeType == null) {
//			System.out.println("mimetype is not detectable, will take default");
//			mimeType = "application/octet-stream";
//		}
//
//		System.out.println("mimetype : " + mimeType);
//
//		response.setContentType(mimeType);
//
//		/*
//		 * "Content-Disposition : inline" will show viewable types [like
//		 * images/text/pdf/anything viewable by browser] right on browser while
//		 * others(zip e.g) will be directly downloaded [may provide save as
//		 * popup, based on your browser setting.]
//		 */
//		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
//
//		/*
//		 * "Content-Disposition : attachment" will be directly download, may
//		 * provide save as popup, based on your browser setting
//		 */
//		// response.setHeader("Content-Disposition", String.format("attachment;
//		// filename=\"%s\"", file.getName()));
//
//		response.setContentLength((int) file.length());
//
//		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
//
//		// Copy bytes from source to destination(outputstream in this example),
//		// closes both streams.
//		FileCopyUtils.copy(inputStream, response.getOutputStream());
//	}
	
	
//	@RequestMapping(value = "/download/{type}/{id}", method = RequestMethod.GET)
//	public void downloadFileDte(HttpServletResponse response, @PathVariable("type") String type,@PathVariable("id") String id, HttpSession httpSession) throws Exception {
//
//		session ses = new session(httpSession);
//		
//		if (ses.isValid()) {
//
//			String errorMessage = "Session terminada ";
//			OutputStream outputStream = response.getOutputStream();
//			outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
//			outputStream.close();
//			return;
//		}
//
//		File file = null;
//		String contenedor=config.getProperty("rutaProcesados");
//		
//		String[] arr = lib.db.dteDB.getFileFolder(id);
//		String path = contenedor+arr[1].toString() + "/" + arr[0].toString() ;
//		if (type.equals("pdf"))
//			file = new File(path + ".pdf");
//		else
//			file = new File(path + ".xml");
//
//		if (!file.exists()) {
//			String errorMessage = "Archivo no existe " + path;
//			System.out.println(errorMessage);
//			OutputStream outputStream = response.getOutputStream();
//			outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
//			outputStream.close();
//			return;
//		}
//
//		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
//		if (mimeType == null) {
//			System.out.println("mimetype is not detectable, will take default");
//			mimeType = "application/octet-stream";
//		}
//
//		System.out.println("mimetype : " + mimeType);
//
//		response.setContentType(mimeType);
//
//		/*
//		 * "Content-Disposition : inline" will show viewable types [like
//		 * images/text/pdf/anything viewable by browser] right on browser while
//		 * others(zip e.g) will be directly downloaded [may provide save as
//		 * popup, based on your browser setting.]
//		 */
//		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
//
//		/*
//		 * "Content-Disposition : attachment" will be directly download, may
//		 * provide save as popup, based on your browser setting
//		 */
//		// response.setHeader("Content-Disposition", String.format("attachment;
//		// filename=\"%s\"", file.getName()));
//
//		response.setContentLength((int) file.length());
//
//		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
//
//		// Copy bytes from source to destination(outputstream in this example),
//		// closes both streams.
//		FileCopyUtils.copy(inputStream, response.getOutputStream());
//	}
//	
	
	
	
	public static String getDTE(int id,String rut) throws Exception {

		String urlSolicitud = "http://10.55.55.30:8080/wsQueryDte/services/query?wsdl";
		//urlSolicitud = "http://localhost:8080/wsQueryDte/services/query?wsdl";
		//urlSolicitud = "http://200.55.206.133/wsInsertDte/services/envio?wsdl";
		

		SOAPConnectionFactory scFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection con = scFactory.createConnection();
		MessageFactory factory = MessageFactory.newInstance();
		SOAPMessage message = factory.createMessage();
		SOAPPart soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("getXmlLibro", "http://wsQueryDte");

		
		SOAPBody body = envelope.getBody();

		SOAPElement dte = body.addChildElement("getXmlLibro", "getXmlLibro");

		SOAPElement Rut = dte.addChildElement("Rut");
		SOAPElement Clave = dte.addChildElement("Clave");
		
		SOAPElement idws = dte.addChildElement("id");
		
		Rut.setTextContent(rut);
		Clave.setTextContent("1234");
		idws.setTextContent(id+"");
	
		//message.writeTo(System.out);
		URL endpoint = new URL(urlSolicitud);
		SOAPMessage responseSII = con.call(message, endpoint);
		System.out.println("####################################");
		
		
		String mensaje="";

		try {
			SOAPEnvelope env = responseSII.getSOAPPart().getEnvelope();
			SOAPBody sb = env.getBody();

			org.w3c.dom.NodeList gloza = sb.getElementsByTagName("ax21:gloza");

			String sgloza = gloza.item(0).getTextContent();
			org.w3c.dom.NodeList estado = sb.getElementsByTagName("ax21:estado");

			String sestado = estado.item(0).getTextContent();
			System.out.println(sgloza);
			
			if (sestado.equals("NOK"))
			{
				throw new Exception("" + sgloza);
			}
			else
			{
				
				String sXml = sb.getElementsByTagName("ax21:xml").item(0).getTextContent();
				mensaje=sXml;
			}

			System.out.println(sestado);
		} catch (Exception e) {
			mensaje="Error ws: "+e.getMessage();
			throw new Exception("Error ws: "+e.getMessage());
		}

		return mensaje;

	}
	
	
	

}
