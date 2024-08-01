package lib.data.json;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.db.ParcelaDB;
import lib.db.VariedadDB;
import lib.db.ParcelaDB;
import lib.db.userDB;
import lib.security.session;
import lib.struc.Parcela;
import lib.struc.Variedad;
import lib.struc.Parcela;
import lib.struc.filterSql;
import lib.struc.mesajesJson;
import lib.struc.user;

@Controller
public class VariedadJson {

	
	
	@RequestMapping(value = "/variedad/getAllOutFilter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<Variedad> getSelectBox(HttpServletRequest request, HttpSession httpSession) throws Exception
	{
		session ses = new session(httpSession);
		ArrayList<Variedad> Variedades = new ArrayList<Variedad>();
		if(ses.isValid())
		{
			Variedades = null;
			return Variedades;
		}
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		Variedades =VariedadDB.getAll(filter, "", 0, 1000);
		return Variedades;
	}
	
	
	
	@RequestMapping(value = "/variedad/getAllByTurno/{codParcela}/{codTurno}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<Variedad> getAllByTurno(@PathVariable("codParcela") String codParcela,@PathVariable("codTurno") String codTurno,HttpServletRequest request, HttpSession httpSession) throws Exception
	{
		session ses = new session(httpSession);
		ArrayList<Variedad> Variedades = new ArrayList<Variedad>();
		if(ses.isValid())
		{
			Variedades = null;
			return Variedades;
		}
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		
		filterSql value= new filterSql();
		value.setCampo("codParcela");
		value.setValue(codParcela);
		filter.add(value);
		
		filterSql value3= new filterSql();
		value3.setCampo("codTurno");
		value3.setValue(codTurno);
		filter.add(value3);
		
		Variedades =VariedadDB.getAllTurno(filter, "", 0, 1000);
		return Variedades;
	}
	
	@RequestMapping(value = "/variedad/getAllByTurno/{codParcela}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<Variedad> getAllByParcela(@PathVariable("codParcela") String codParcela,HttpServletRequest request, HttpSession httpSession) throws Exception
	{
		session ses = new session(httpSession);
		ArrayList<Variedad> Variedades = new ArrayList<Variedad>();
		if(ses.isValid())
		{
			Variedades = null;
			return Variedades;
		}
		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		
		filterSql value= new filterSql();
		value.setCampo("codParcela");
		value.setValue(codParcela);
		filter.add(value);
		
		Variedades =VariedadDB.getAllTurno(filter, "", 0, 1000);
		return Variedades;
	}
}
