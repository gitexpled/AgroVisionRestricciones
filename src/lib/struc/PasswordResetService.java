package lib.struc;
import java.util.Date;
import lib.db.userDB;
import lib.mail.MailService;

import org.springframework.stereotype.Service;

@Service
public class PasswordResetService {

    public String restablecerPassword(String correo, String claveProvisoria, String nuevaClave, String confirmarClave) {
        try {
            if (correo == null || claveProvisoria == null || nuevaClave == null || confirmarClave == null ||
                correo.isEmpty() || claveProvisoria.isEmpty() || nuevaClave.isEmpty() || confirmarClave.isEmpty()) {
                return "Todos los campos son obligatorios.";
            }
            if (!nuevaClave.equals(confirmarClave)) {
                return "Las contraseñas no coinciden.";
            }
            user u = userDB.getUserByMail(correo);
            if (u == null) {
                return "No se encontró un usuario con el correo ingresado.";
            }
            if (!claveProvisoria.equals(u.getPassTemporal())) {
                return "La clave temporal no es válida.";
            }

            Date ahora = new Date();
            Date fechaSolicitud = u.getFechaSolicitudModificacion();

            if (fechaSolicitud == null) {
                return "No se encontró una solicitud de recuperación activa.";
            }
			long diffMillis = ahora.getTime() - fechaSolicitud.getTime();
			long minutosPasados = diffMillis / (1000 * 60);

            if (minutosPasados > 20) {
                return "La clave temporal ha expirado.";
            }

            u.setPassword(nuevaClave);
            u.setEstado(1);
            u.setPassTemporal(null);
            u.setFechaSolicitudModificacion(null);
            userDB.updateUser(u);

            MailRequest mail = new MailRequest(correo, "Contraseña restablecida",
                "<p>Hola " + u.getNombre() + ", tu contraseña ha sido restablecida con éxito.</p>", true);
            new MailService().sendEmail(mail);

            return "Contraseña restablecida exitosamente.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error interno: " + e.getMessage();
        }
    }

    public Boolean validateMail(String mail) {
        try {
            user u = userDB.getUserByMail(mail);
            return u != null;
        } catch (Exception e) {
            System.out.println("Error interno: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
