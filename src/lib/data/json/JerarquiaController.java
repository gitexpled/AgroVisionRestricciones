package lib.data.json;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lib.db.TurnoDB;
import lib.db.jerarquiaDB;
import lib.security.session;
import lib.struc.Turno;
import lib.struc.filterSql;
import lib.struc.jerarquia;

import org.json.JSONObject;
import org.springframework.http.MediaType;
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
    
    @RequestMapping(value = "/getCambioJerarquia/{desde}/{hasta}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<jerarquia> getCambioJerarquia(HttpServletRequest request, HttpSession httpSession,@PathVariable("desde") String desde,@PathVariable("hasta") String hasta) throws Exception
	{
		session ses = new session(httpSession);
		ArrayList<jerarquia> jer = new ArrayList<jerarquia>();
		if(ses.isValid())
		{
			jer = null;
			return jer;
		}
		
		jerarquiaDB db = new jerarquiaDB();
		jer = db.getCambios(desde,  hasta);
		return jer;
	}
}
