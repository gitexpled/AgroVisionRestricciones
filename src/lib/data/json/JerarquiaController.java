package lib.data.json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lib.db.jerarquiaDB;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Jerarquia")
public class JerarquiaController {

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/deleteJerarquia", method = RequestMethod.PUT, produces = "application/json")
    public String deleteJerarquia(@RequestBody String json, HttpServletRequest request, HttpServletResponse response) {
        JSONObject resp = new JSONObject();

        try {
            JSONObject req = new JSONObject(json);

            if (!req.has("id")) {
                resp.put("success", false);
                resp.put("message", "El campo 'id' es obligatorio");
                return resp.toString();
            }

            int id = req.getInt("id");
            int estado = req.getInt("estado");
            jerarquiaDB db = new jerarquiaDB();
            boolean updated = db.updateEstadoById(id,estado);

            if (updated) {
                resp.put("success", true);
                resp.put("message", "Jerarquía eliminada correctamente");
            } else {
                resp.put("success", false);
                resp.put("message", "No se encontró la jerarquía o no se pudo actualizar");
            }

        } catch (Exception ex) {
            resp.put("success", false);
            resp.put("message", "Error al procesar la solicitud: " + ex.getMessage());
        }

        return resp.toString();
    }
}
