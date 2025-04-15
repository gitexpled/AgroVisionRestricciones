package lib.data.json;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import lib.struc.PasswordResetService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RestorePassController {


    @RequestMapping(value = "/restorePassMail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map<String, String> restorePassMail(HttpServletRequest request, HttpSession httpSession) {
        Map<String, String> response = new HashMap<>();

        try {
            String email = request.getParameter("email");
            String claveProvisoria = request.getParameter("claveProvisoria");
            String nuevaClave = request.getParameter("nuevaClave");
            String confirmarClave = request.getParameter("confirmarClave");
            PasswordResetService passwordResetService = new PasswordResetService();
            String resultado = passwordResetService.restablecerPassword(email, claveProvisoria, nuevaClave, confirmarClave);

            if ("Contraseña restablecida exitosamente.".equals(resultado)) {
                response.put("estado", "OK");
                response.put("mensaje", resultado);
            } else {
                response.put("estado", "NOK");
                response.put("mensaje", resultado);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.put("estado", "NOK");
            response.put("mensaje", "Error inesperado al procesar la solicitud.");
        }

        return response;
    }
}
