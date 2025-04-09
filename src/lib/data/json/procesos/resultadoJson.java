package lib.data.json.procesos;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lib.db.resultadoDB;
import lib.security.session;


@Controller
public class resultadoJson {

	@RequestMapping(value = "/resultado/viewPdf/{id}", method = { RequestMethod.POST, RequestMethod.GET },produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> getPdf(HttpServletRequest request, @PathVariable("id") String id,HttpSession httpSession)  {
		
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return null;
		}
		resultadoDB res= new resultadoDB();
		byte[] pdfData =res.getPdfId(id);
		System.out.println("JOLAAAA");
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(pdfData);
		

	}
	

}

