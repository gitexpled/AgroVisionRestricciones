package lib.mail.templates;

import lib.io.ConfigProperties;

public class MailTemplateBuilder {
    public static String buildRecoveryTemplate(String mail, String claveTemporal) {
    	String baseUrl = ConfigProperties.getProperty("APP_BASE_URL");
        System.out.println("baseUrl===>"+baseUrl);
        if (baseUrl == null || baseUrl.isEmpty()) {
            baseUrl = "http://localhost:8080/AgroVisionRestricciones";
        }
        return "<h2>Recuperación de Contraseña</h2>" +
                "<p>Hola,</p>" +
                "<p>Hemos recibido una solicitud para restablecer tu contraseña asociada al correo: <strong>" + mail +
                "</strong>.</p>" +
                "<p>Solicitaste restablecer tu contraseña.</p>" +
                "<p>Tu clave temporal es: <strong>" + claveTemporal + "</strong></p>" +
                "<p>Esta clave es válida por 20 minutos.</p>" +
                "<p><a href=\"" + baseUrl + "/webApp/restorePass.jsp?email=" + mail +
                "\">Restablecer contraseña</a></p>";
    }

}