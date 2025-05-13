package lib.data.json;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lib.security.session;
import lib.servicios.ExportarExcel;

@Controller
public class ExportarExcelController {

    @RequestMapping(value = "/exportaExcel/{id}", method = RequestMethod.GET)
    public void exportaExcel(
            HttpServletResponse response,
            @PathVariable("id") String idTemporada,
            HttpSession httpSession
    ) throws Exception {
        session ses = new session(httpSession);
        if (ses.isValid()) {
            response.getOutputStream().write("Session terminada".getBytes("UTF-8"));
            return;
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"reporte.xlsx\"");
        ExportarExcel.exportarGeneral(response, idTemporada);
    }
}
