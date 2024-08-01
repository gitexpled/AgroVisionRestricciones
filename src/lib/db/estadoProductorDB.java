package lib.db;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import lib.struc.Fuente;
import lib.struc.alarmaComponente;
import lib.struc.filterSql;
import lib.struc.restriccion;

public class estadoProductorDB {

	public static ArrayList<String[]> getEstadoProductorPro(int idTemporada,int idEspecie, String productor,String nombre) throws Exception {
		ArrayList<String[]> data = new ArrayList<>();

		ConnectionDB db = new ConnectionDB();
		 Statement stmt = null;
		String sql = "";
		try {

			 stmt = db.conn.createStatement();

			sql = "{CALL sp_viewRestricionesPro("+idTemporada+","+idEspecie+",'"+productor+"','"+nombre+"') }";
			System.out.println(sql);
			// ResultSet rs = stmt.executeQuery(sql);
			ResultSet rs = stmt.executeQuery(sql);

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			int j=0;
			while (rs.next()) {

				String[] o = new String[columnCount];

				for (int i = 1; i <= columnCount; i++) {
					
					o[i-1] = rs.getString(i);
					
				}
				++j;
				data.add(o);
			}
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getEstadoProductor: " + e.getMessage());
		} finally {
			db.close();
		}

		return data;
	}
	
	
	public static ArrayList<String[]> getEstadoProductorA(int idTemporada,int idEspecie, String productor,String parcela,String turno,String variedad,Boolean titulo,String cliente) throws Exception {
		ArrayList<String[]> data = new ArrayList<>();

		ConnectionDB db = new ConnectionDB();
		 Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "{CALL sp_viewRestriciones("+idTemporada+","+idEspecie+",'"+variedad+"','"+productor+"','"+parcela+"','"+turno+"','"+cliente+"') }";
			System.out.println(sql);
			// ResultSet rs = stmt.executeQuery(sql);
			ResultSet rs = stmt.executeQuery(sql);

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			
			for (int i = 1; i <= columnCount; i++) {
				
			}

			int j=0;
			if (titulo)
			{
				String[] o = new String[columnCount];
				System.out.println("TITULOSSSSS");
				
				for (int x = 1; x <= columnCount; x++) {

					o[x - 1] = rsmd.getColumnLabel(x);

				}
				if (titulo)
					data.add(o);
			}
			
			
			while (rs.next()) {

				String[] o = new String[columnCount];

				for (int i = 1; i <= columnCount; i++) {

					o[i - 1] = rs.getString(i);

				}
				data.add(o);

				++j;

			}
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getEstadoProductor: " + e.getMessage());
		} finally {
			db.close();
		}

		return data;
	}
	
	public static ArrayList<String[]> getEstadoProductorB(int idTemporada,int idEspecie, String productor,String parcela,String variedad,Boolean titulo) throws Exception {
		ArrayList<String[]> data = new ArrayList<>();

		ConnectionDB db = new ConnectionDB();
		 Statement stmt = null;
		String sql = "";
		try {

			 stmt = db.conn.createStatement();

			 sql = "{CALL sp_viewRestricionesParcela("+idTemporada+","+idEspecie+",'"+variedad+"','"+productor+"','"+parcela+"') }";
			 System.out.println(sql);
			// ResultSet rs = stmt.executeQuery(sql);
			ResultSet rs = stmt.executeQuery(sql);

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			
			for (int i = 1; i <= columnCount; i++) {
				
			}

			int j=0;
			if (titulo)
			{
				String[] o = new String[columnCount];
				System.out.println("TITULOSSSSS");
				
				for (int x = 1; x <= columnCount; x++) {

					o[x - 1] = rsmd.getColumnLabel(x);

				}
				if (titulo)
					data.add(o);
			}
			
			
			while (rs.next()) {

				String[] o = new String[columnCount];

				for (int i = 1; i <= columnCount; i++) {

					o[i - 1] = rs.getString(i);

				}
				data.add(o);

				++j;

			}
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getEstadoProductor: " + e.getMessage());
		} finally {
			db.close();
		}

		return data;
	}
	
	public static String getRestriccionesExcel(int idTemporada,int idEspecie, String productor,String parcela,String variedad,Boolean titulo) throws Exception {
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		JSONObject json = new JSONObject();
		ArrayList<String> titulos2 = new ArrayList<>();
		JSONArray array = new JSONArray();
		try {
			stmt = db.conn.createStatement();
			sql = "{CALL sp_viewRestricionesExcel("+idTemporada+","+idEspecie+",'"+variedad+"','"+productor+"','"+parcela+"') }";
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			JSONArray columns = new JSONArray();
			for (int i = 1; i <= columnCount; i++) {
				columns.put(rsmd.getColumnLabel(i));
				titulos2.add(rsmd.getColumnLabel(i));
			}
			int c = 0;
			while (rs.next()) {
				JSONArray arrayItem = new JSONArray();
				for(int i = 0; i < titulos2.size(); i++){
					if(c == 0){
						System.out.println(titulos2.get(i));
					}
					arrayItem.put(rs.getObject(titulos2.get(i)));
				}
				array.put(arrayItem);
				c++;
			}
			while (rs.next()) {
				JSONObject ob = new JSONObject();
				for(int i = 0; i < titulos2.size(); i++){
					ob.put(titulos2.get(i), rs.getObject(titulos2.get(i)) == null ? JSONObject.NULL: rs.getObject(titulos2.get(i)));
				}
				array.put(ob);
			}
			json.put("columns", columns);
			System.out.println(columns);
			json.put("data", array);
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getEstadoProductor: " + e.getMessage());
		} finally {
			db.close();
		}

		return json.toString();
	}
	
	public static String getRestricionesParcelaTurnoExcel(int idTemporada,int idEspecie, String productor,String nombre,String cliente,Boolean titulo) throws Exception {
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		JSONObject json = new JSONObject();
		ArrayList<String> titulos2 = new ArrayList<>();
		JSONArray array = new JSONArray();
		try {
			stmt = db.conn.createStatement();
			sql = "{CALL sp_viewRestricionesParcelaTurnoExcel("+idTemporada+","+idEspecie+",'"+productor+"','"+nombre+"','"+cliente+"') }";
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			JSONArray columns = new JSONArray();
			for (int i = 1; i <= columnCount; i++) {
				columns.put(rsmd.getColumnLabel(i));
				titulos2.add(rsmd.getColumnLabel(i));
			}
			int c = 0;
			while (rs.next()) {
				JSONArray arrayItem = new JSONArray();
				for(int i = 0; i < titulos2.size(); i++){
					if(c == 0){
						System.out.println(titulos2.get(i));
					}
					arrayItem.put(rs.getObject(titulos2.get(i)));
				}
				array.put(arrayItem);
				c++;
			}
			while (rs.next()) {
				JSONObject ob = new JSONObject();
				for(int i = 0; i < titulos2.size(); i++){
					ob.put(titulos2.get(i), rs.getObject(titulos2.get(i)) == null ? JSONObject.NULL: rs.getObject(titulos2.get(i)));
				}
				array.put(ob);
			}
			json.put("columns", columns);
			System.out.println(columns);
			json.put("data", array);
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getEstadoProductor: " + e.getMessage());
		} finally {
			db.close();
		}

		return json.toString();
	}
	public static ArrayList<String[]> getEstadoProductorC(int idTemporada,int idEspecie, String productor,String nombre,String cliente,Boolean titulo) throws Exception {
		ArrayList<String[]> data = new ArrayList<>();

		ConnectionDB db = new ConnectionDB();
		 Statement stmt = null;
		String sql = "";
		try {

			 stmt = db.conn.createStatement();

			sql = "{CALL sp_viewRestricionesParcelaTurno("+idTemporada+","+idEspecie+",'"+productor+"','"+nombre+"','"+cliente+"') }";
			System.out.println(sql);
			// ResultSet rs = stmt.executeQuery(sql);
			ResultSet rs = stmt.executeQuery(sql);

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			
			for (int i = 1; i <= columnCount; i++) {
				
			}

			int j=0;
			if (titulo)
			{
				String[] o = new String[columnCount];
				System.out.println("TITULOSSSSS");
				
				for (int x = 1; x <= columnCount; x++) {

					o[x - 1] = rsmd.getColumnLabel(x);

				}
				if (titulo)
					data.add(o);
			}
			
			
			while (rs.next()) {

				String[] o = new String[columnCount];

				for (int i = 1; i <= columnCount; i++) {

					o[i - 1] = rs.getString(i);

				}
				data.add(o);

				++j;

			}
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getEstadoProductor: " + e.getMessage());
		} finally {
			db.close();
		}

		return data;
		
	}
	
	public static String getBlockProductor(int idTemporada,int idEspecie,String idVariedad, String Mercado,String productor,String parcela,String turno) throws Exception {
		
		String Estado="";
		ConnectionDB db = new ConnectionDB();
		 Statement stmt = null;
		String sql = "";
		try {

			 stmt = db.conn.createStatement();
			 System.out.println(idTemporada+"-"+idEspecie+"-"+productor);
			 
			String aa=VariedadDB.getByCod(idVariedad+"");
			sql = "SELECT * FROM vw_blockProductorMercado where  ";
			sql += "     idTemporada='"+idTemporada+"'";
			sql += " and idEspecie='"+idEspecie+"'";
			sql += " and idVariedad='"+aa+"'";
			sql += " and codProductor='"+productor+"'";
			sql += " and codParcela='"+parcela+"'";
			sql += " and idMercado='"+Mercado+"'";
			
			if (turno!=null)
				sql += " and codTurno='"+turno+"'";
			
			sql+="group by idEspecie,idMercado,idVariedad,codProductor,codParcela";
			if (turno!=null)
				sql += ",codTurno";
			
			System.out.println("sql: " + sql);
			ResultSet rs = stmt.executeQuery(sql);
			Estado="<table border=1>";
			Estado+="<tr><td>&nbsp;Parcela&nbsp;</td><td>&nbsp;Turno&nbsp;</td><td>&nbsp;Variedad&nbsp;</td><td>&nbsp;Estado&nbsp;</td></tr>";
			while (rs.next()) {
				String codParcelaStr=rs.getString("codParcela");
				String codTurnoStr=rs.getString("codTurno");
				String codVariedad=VariedadDB.getById(rs.getString("idVariedad"));
				
				
				Estado+="<tr><td>&nbsp;"+codParcelaStr+"&nbsp;</td><td>&nbsp;"+codTurnoStr+"&nbsp;</td><td>&nbsp;"+codVariedad+"&nbsp;</td><td>&nbsp;No Relacionada</td></tr>";
			}
			Estado+="</table>";
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getEstadoProductor: " + e.getMessage());
		} finally {
			db.close();
		}

		return Estado;
	}

	public static String getBlockMolecula(int idTemporada,int idEspecie,String idVariedad, String Mercado,String productor,String parcela,String turno) throws Exception {
		
		String Estado="";
		ConnectionDB db = new ConnectionDB();
		 Statement stmt = null;
		String sql = "";
		try {

			 stmt = db.conn.createStatement();
			 String aa=VariedadDB.getByCod(idVariedad+"");
			sql = "SELECT * FROM vw_blockMolecula where  ";
			sql += "     idTemporada='"+idTemporada+"'";
			sql += " and idEspecie='"+idEspecie+"'";
			sql += " and idVariedad='"+aa+"'";
			sql += " and codProductor='"+productor+"'";
			sql += " and codParcela='"+parcela+"'";
			sql += " and idMercado='"+Mercado+"'";
			
			if (turno!=null)
				sql += " and codTurno='"+turno+"'";
			
			
			System.out.println("sql: " + sql);
			ResultSet rs = stmt.executeQuery(sql);

			Estado="<table border=1>";
			Estado+="<tr><td>&nbsp;Parcela&nbsp;</td><td>&nbsp;Turno&nbsp;</td><td>&nbsp;Variedad&nbsp;</td><td>&nbsp;Moleculas&nbsp;</td></tr>";
			while (rs.next()) {
				String codParcelaStr=rs.getString("codParcela");
				String codTurnoStr=rs.getString("codTurno");
				String codVariedad=VariedadDB.getById(rs.getString("idVariedad"));
				String p=rs.getString("p");
				
				Estado+="<tr><td>&nbsp;"+codParcelaStr+"&nbsp;</td><td>&nbsp;"+codTurnoStr+"&nbsp;</td><td>&nbsp;"+codVariedad+"&nbsp;</td><td>&nbsp;"+p+"&nbsp;</td></tr>";
			}
			Estado+="</table>";
			
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getEstadoProductor: " + e.getMessage());
		} finally {
			db.close();
		}

		return Estado;
	}
	
	
	
public static String getBlockPorcentaje(int idTemporada,int idEspecie,String idVariedad, String Mercado,String productor,String parcela,String turno) throws Exception {
		
		String Estado="";
		ConnectionDB db = new ConnectionDB();
		 Statement stmt = null;
		String sql = "";
		try {

			 stmt = db.conn.createStatement();
			 String aa=VariedadDB.getByCod(idVariedad+"");
			sql = "SELECT * FROM vw_blockPorcentaje where  ";
			sql += "     idTemporada='"+idTemporada+"'";
			sql += " and idEspecie='"+idEspecie+"'";
			sql += " and idMercado='"+Mercado+"'";
			sql += " and idVariedad='"+aa+"'";
			sql += " and codProductor='"+productor+"'";
			sql += " and codParcela='"+parcela+"'";
			
			if (turno!=null)
				sql += " and codTurno='"+turno+"'";
			
			
			System.out.println("sql: " + sql);
			ResultSet rs = stmt.executeQuery(sql);

			Estado="<table border=1>";
			Estado+="<tr><td>&nbsp;Parcela&nbsp;</td><td>&nbsp;Turno&nbsp;</td><td>&nbsp;Variedad&nbsp;</td><td>&nbsp;Porcentaje&nbsp;</td></tr>";
			while (rs.next()) {
				String codParcelaStr=rs.getString("codParcela");
				String codTurnoStr=rs.getString("codTurno");
				String codVariedad=VariedadDB.getById(rs.getString("idVariedad"));
				DecimalFormat f = new DecimalFormat("##.##");
				String sump=f.format(rs.getDouble("sump")).replace(",", ".");
				
				Estado+="<tr><td>&nbsp;"+codParcelaStr+"&nbsp;</td><td>&nbsp;"+codTurnoStr+"&nbsp;</td><td>&nbsp;"+codVariedad+"&nbsp;</td><td>&nbsp;"+sump+"%&nbsp;</td></tr>";
			}
			Estado+="</table>";
			
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getEstadoProductor: " + e.getMessage());
		} finally {
			db.close();
		}

		return Estado;
	}
	

	public static String getBlockPorcentajeArfD(int idTemporada,int idEspecie,String idVariedad, String Mercado,String productor,String parcela,String turno) throws Exception {
		
		String Estado="";
		ConnectionDB db = new ConnectionDB();
		 Statement stmt = null;
		String sql = "";
		try {

			 stmt = db.conn.createStatement();
			 String aa=VariedadDB.getByCod(idVariedad+"");
			sql = "SELECT * FROM vw_blockPorcentajeArfD where  ";
			sql += "     idTemporada='"+idTemporada+"'";
			sql += " and idEspecie='"+idEspecie+"'";
			sql += " and idMercado='"+Mercado+"'";
			sql += " and idVariedad='"+aa+"'";
			sql += " and codProductor='"+productor+"'";
			sql += " and codParcela='"+parcela+"'";
			
			if (turno!=null)
				sql += " and codTurno='"+turno+"'";
			
			
			System.out.println("sql: " + sql);
			ResultSet rs = stmt.executeQuery(sql);

			Estado="<table border=1>";
			Estado+="<tr><td>&nbsp;Parcela&nbsp;</td><td>&nbsp;Turno&nbsp;</td><td>&nbsp;Variedad&nbsp;</td><td>&nbsp;Porcentaje ArfD&nbsp;</td></tr>";
			while (rs.next()) {
				String codParcelaStr=rs.getString("codParcela");
				String codTurnoStr=rs.getString("codTurno");
				String codVariedad=VariedadDB.getById(rs.getString("idVariedad"));
				DecimalFormat f = new DecimalFormat("##.##");
				String sump=f.format(rs.getDouble("sump")).replace(",", ".");
				
				Estado+="<tr><td>&nbsp;"+codParcelaStr+"&nbsp;</td><td>&nbsp;"+codTurnoStr+"&nbsp;</td><td>&nbsp;"+codVariedad+"&nbsp;</td><td>&nbsp;"+sump+"%&nbsp;</td></tr>";
			}
			Estado+="</table>";
			
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getEstadoProductor: " + e.getMessage());
		} finally {
			db.close();
		}

		return Estado;
	}
	
	
	
	
	public static String getEstadoProductor(int idTemporada,int idEspecie,String idVariedad, String Mercado,String productor,String parcela,String turno) throws Exception {
		
		String Estado="";
		ConnectionDB db = new ConnectionDB();
		 Statement stmt = null;
		String sql = "";
		try {

			 stmt = db.conn.createStatement();
			 System.out.println(idTemporada+"-"+idEspecie+"-"+productor);
			sql = "{CALL sp_viewRestriciones("+idTemporada+","+idEspecie+",'"+idVariedad+"','"+productor+"','"+parcela+"','"+turno+"','') }";
			System.out.println("sql: " + sql);
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {

				

				Estado=rs.getString(Mercado);
				System.out.println("sql: " + sql);
			}
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getEstadoProductor: " + e.getMessage());
		} finally {
			db.close();
		}

		return Estado;
	}
	public static String getEstadoProductor(int idTemporada,int idEspecie,int idVariedad, String Mercado,String productor,String parcela) throws Exception {
		
		String Estado="";
		ConnectionDB db = new ConnectionDB();
		 Statement stmt = null;
		String sql = "";
		try {

			 stmt = db.conn.createStatement();
			 System.out.println(idTemporada+"-"+idEspecie+"-"+productor);
			sql = "{CALL sp_viewRestricionesParcela("+idTemporada+","+idEspecie+","+idVariedad+",'"+productor+"','"+parcela+"') }";
			//sql = "{CALL sp_viewRestricionesParcela("+idTemporada+","+idEspecie+",'"+productor+"','"+nombre+"') }";
			System.out.println("sql: " + sql);
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {

				

				Estado=rs.getString(Mercado);
				System.out.println("sql: " + sql);
			}
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getEstadoProductor: " + e.getMessage());
		} finally {
			db.close();
		}

		return Estado;
	}
	
	
	
	
	public static int getAllcount(ArrayList<filterSql> filter) throws Exception {

		int total = 0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT count(1) FROM vw_bloqueados ";

			if (filter.size() > 0) {
				String andSql="";
				andSql += " WHERE ";
				Iterator<filterSql> f = filter.iterator();
				
				while (f.hasNext()) 
				{
					filterSql row = f.next();
					if (!row.getValue().equals(""))
					{
						if (row.getCampo().endsWith("_to"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 3)+" <='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else if(row.getCampo().endsWith("_from"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 5)+" >='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else
						sql+=andSql+row.getCampo()+" like'%"+row.getValue()+"%'";
						andSql=" and ";
					}
				}

			}

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				total = rs.getInt(1);
			}
			rs.close();
			stmt.close();
			db.conn.close();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getLimitesAll: " + e.getMessage());
		} finally {
			db.close();
		}
		return total;
	}
	
	public static ArrayList<restriccion> getAll(ArrayList<filterSql> filter, String order, int start, int length)
			throws Exception {

		ArrayList<restriccion> arr = new ArrayList<restriccion>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM vw_bloqueados ";

			if (filter.size() > 0) {
				String andSql="";
				andSql += " WHERE ";
				Iterator<filterSql> f = filter.iterator();

				while (f.hasNext()) 
				{
					filterSql row = f.next();
					
					if (!row.getValue().equals(""))
					{
						if (row.getCampo().endsWith("_to"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 3)+" <='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else if(row.getCampo().endsWith("_from"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 5)+" >='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else
						sql+=andSql+row.getCampo()+" like '%"+row.getValue()+"%'";
						andSql=" and ";
					}
				}

			}
			if (!order.equals("")) {
				sql += " order by "+order;
			}
			else
				sql += " order by codProductor ";

			if (length > 0) {
				sql += " limit " + start + "," + length + " ";
			}
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				restriccion o = new restriccion();
				
				//, fecha, codProducto, limite, mercado, especie, Automatica
				o.setCodProductor(rs.getString("codProductor"));
				o.setFecha(rs.getString("fecha"));
				o.setCodProducto(rs.getString("codProducto"));
				o.setLimite(rs.getString("limite"));
				
				o.setMercado(rs.getString("mercado"));
				o.setEspecie(rs.getString("especie"));
				o.setnMuestra(rs.getString("nMuestra"));
				o.setAutomatica(rs.getString("Automatica"));
				arr.add(o);
			}
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getLimite: " + e.getMessage());
		} finally {
			db.close();
		}

		return arr;
	}
	
	
	public static int getAllcountRest(ArrayList<filterSql> filter) throws Exception {

		int total = 0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT count(1) FROM vw_bloqueadosSinRest ";

			if (filter.size() > 0) {
				String andSql="";
				andSql += " WHERE ";
				Iterator<filterSql> f = filter.iterator();
				
				while (f.hasNext()) 
				{
					filterSql row = f.next();
					if (!row.getValue().equals(""))
					{
						if (row.getCampo().endsWith("_to"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 3)+" <='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else if(row.getCampo().endsWith("_from"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 5)+" >='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else
						sql+=andSql+row.getCampo()+" like'%"+row.getValue()+"%'";
						andSql=" and ";
					}
				}

			}

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				total = rs.getInt(1);
			}
			rs.close();
			stmt.close();
			db.conn.close();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getLimitesAll: " + e.getMessage());
		} finally {
			db.close();
		}
		return total;
	}
	
	public static ArrayList<restriccion> getAllRest(ArrayList<filterSql> filter, String order, int start, int length)
			throws Exception {

		ArrayList<restriccion> arr = new ArrayList<restriccion>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM vw_bloqueadosSinRest ";

			if (filter.size() > 0) {
				String andSql="";
				andSql += " WHERE ";
				Iterator<filterSql> f = filter.iterator();

				while (f.hasNext()) 
				{
					filterSql row = f.next();
					
					if (!row.getValue().equals(""))
					{
						if (row.getCampo().endsWith("_to"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 3)+" <='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else if(row.getCampo().endsWith("_from"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 5)+" >='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else
						sql+=andSql+row.getCampo()+" like '%"+row.getValue()+"%'";
						andSql=" and ";
					}
				}

			}
			if (!order.equals("")) {
				sql += " order by "+order;
			}
			else
				sql += " order by codProductor ";

			if (length > 0) {
				sql += " limit " + start + "," + length + " ";
			}
			System.out.println("sql: " + sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				restriccion o = new restriccion();
				
				//, fecha, codProducto, limite, mercado, especie, Automatica
				o.setCodProductor(rs.getString("codProductor"));
				o.setCodParcela(rs.getString("codParcela"));
				o.setCodTurno(rs.getString("codTurno"));
				o.setFecha(rs.getString("fecha"));
				o.setCodProducto(rs.getString("codProducto"));
				o.setLimite(rs.getString("limite").replace(".", ","));
				o.setNomProductor(rs.getString("nomProductor"));
				//o.setMercado(rs.getString("mercado"));
				o.setEspecie(rs.getString("especie"));
				o.setAutomatica(rs.getString("Automatica"));
				o.setnMuestra(rs.getString("nMuestra"));
				o.setVariedad(rs.getString("variedad"));
				o.setFechaCarga(rs.getString("fechaCarga"));
				arr.add(o);
			}
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getLimite: " + e.getMessage());
		} finally {
			db.close();
		}

		return arr;
	}
	
	
	
	public static int getAllcountZona(ArrayList<filterSql> filter) throws Exception {

		int total = 0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT  count(1) FROM (SELECT distinct z.codProductor,  d.codProducto, z.limite,  z.especie, z.Automatica, z.mail, z.nMuestra "
					+ " FROM vw_bloqueadosZona as z"
					+ " inner join diccionario as d on (z.codProducto=d.codRemplazo)"
					+ "and z.fechaIngreso BETWEEN '2019/07/01' AND '2020/06/30') as d ";

			if (filter.size() > 0) {
				String andSql="";
				andSql += " WHERE ";
				Iterator<filterSql> f = filter.iterator();
				
				while (f.hasNext()) 
				{
					filterSql row = f.next();
					if (!row.getValue().equals(""))
					{
						if (row.getCampo().endsWith("_to"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 3)+" <='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else if(row.getCampo().endsWith("_from"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 5)+" >='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else
						sql+=andSql+row.getCampo()+" like'%"+row.getValue()+"%'";
						andSql=" and ";
					}
				}

			}

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				total = rs.getInt(1);
			}
			rs.close();
			stmt.close();
			db.conn.close();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getLimitesAll: " + e.getMessage());
		} finally {
			db.close();
		}
		return total;
	}
	
	public static ArrayList<restriccion> getAllZona(ArrayList<filterSql> filter, String order, int start, int length)
			throws Exception {

		ArrayList<restriccion> arr = new ArrayList<restriccion>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT distinct z.codProductor,  d.codProducto, z.limite,  z.especie, z.Automatica, z.mail, z.nMuestra,z.fechaIngreso "
					+ " FROM vw_bloqueadosZona as z"
					+ " inner join diccionario as d on (z.codProducto=d.codRemplazo)";

			if (filter.size() > 0) {
				String andSql="";
				andSql += " WHERE ";
				Iterator<filterSql> f = filter.iterator();

				while (f.hasNext()) 
				{
					filterSql row = f.next();
					
					if (!row.getValue().equals(""))
					{
						if (row.getCampo().endsWith("_to"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 3)+" <='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else if(row.getCampo().endsWith("_from"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 5)+" >='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else
						sql+=andSql+row.getCampo()+" like '%"+row.getValue()+"%'";
						andSql=" and z.fechaIngreso BETWEEN '2019/07/01' AND '2020/06/30' AND ";
					}
				}

			}
			if (!order.equals("")) {
				sql += " order by "+order;
			}
			else
				sql += " order by codProductor ";

			if (length > 0) {
				sql += " limit " + start + "," + length + " ";
			}
			System.out.println("sql: " + sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				restriccion o = new restriccion();
				
				//, fecha, codProducto, limite, mercado, especie, Automatica
				o.setCodProductor(rs.getString("codProductor"));
				o.setFecha(rs.getString("fechaIngreso"));
				o.setCodProducto(rs.getString("codProducto"));
				o.setLimite(rs.getString("limite"));
				o.setnMuestra(rs.getString("nMuestra"));
				//o.setMercado(rs.getString("zona"));
				o.setEspecie(rs.getString("especie"));
				o.setAutomatica(rs.getString("Automatica"));
				arr.add(o);
			}
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getLimite: " + e.getMessage());
		} finally {
			db.close();
		}

		return arr;
	}


	
	
	public static int getAllcountZona2(ArrayList<filterSql> filter) throws Exception {

		int total = 0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT  count(distinct codProductor) FROM vw_bloqueadosZona ";
			sql = "SELECT count(distinct codProductor) FROM (select p.zona, p.codProductor,e.especie from  (select max(idRestriciones),idEspecie, codProductor  "
					+ "from restriciones where inicial='N' AND idTemporada = 2  group by codProductor,idEspecie) as r "
					+ "inner join productor p on (p.codProductor=r.codProductor ) "
					+ "inner join especie e on(e.idEspecie=r.idEspecie )) as mm";

			if (filter.size() > 0) {
				String andSql="";
				andSql += " WHERE ";
				Iterator<filterSql> f = filter.iterator();
				
				while (f.hasNext()) 
				{
					filterSql row = f.next();
					if (!row.getValue().equals(""))
					{
						if (row.getCampo().endsWith("_to"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 3)+" <='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else if(row.getCampo().endsWith("_from"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 5)+" >='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else
						sql+=andSql+row.getCampo()+" like'%"+row.getValue()+"%'";
						andSql=" and ";
					}
				}

			}
			//System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				total = rs.getInt(1);
			}
			rs.close();
			stmt.close();
			db.conn.close();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getLimitesAll: " + e.getMessage());
		} finally {
			db.close();
		}
		return total;
	}
	
	public static ArrayList<restriccion> getAllZona2(ArrayList<filterSql> filter, String order, int start, int length)
			throws Exception {

		ArrayList<restriccion> arr = new ArrayList<restriccion>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT distinct * FROM (select p.zona, p.codProductor,e.especie from  (select max(idRestriciones),idEspecie, codProductor  "
					+ "from restriciones where inicial='N' AND idTemporada = 2  group by codProductor,idEspecie) as r "
					+ "inner join productor p on (p.codProductor=r.codProductor ) "
					+ "inner join especie e on(e.idEspecie=r.idEspecie )) as mm";
			
			
			

			if (filter.size() > 0) {
				String andSql="";
				andSql += " WHERE ";
				Iterator<filterSql> f = filter.iterator();

				while (f.hasNext()) 
				{
					filterSql row = f.next();
					
					if (!row.getValue().equals(""))
					{
						if (row.getCampo().endsWith("_to"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 3)+" <='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else if(row.getCampo().endsWith("_from"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 5)+" >='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else
						sql+=andSql+row.getCampo()+" like '%"+row.getValue()+"%'";
						andSql=" and ";
					}
				}

			}
			if (!order.equals("")) {
				sql += " order by "+order;
			}
			else
				sql += " order by codProductor ";

			if (length > 0) {
				sql += " limit " + start + "," + length + " ";
			}
			System.out.println("sql: " + sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				restriccion o = new restriccion();
				
				//, fecha, codProducto, limite, mercado, especie, Automatica
				o.setCodProductor(rs.getString("codProductor"));
				//o.setFecha(rs.getString("nMuestra"));
				//o.setCodProducto(rs.getString("codProducto"));
				//o.setLimite(rs.getString("limite"));
				
				o.setMercado(rs.getString("zona"));
				o.setEspecie(rs.getString("especie"));
				
				o.setAutomatica("");//rs.getString("Automatica"));
				arr.add(o);
			}
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getLimite: " + e.getMessage());
		} finally {
			db.close();
		}

		return arr;
	}

	
	
	
	public static int getAllcountMercado(ArrayList<filterSql> filter) throws Exception {

		int total = 0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT  count(1) FROM (SELECT r.codProductor,m.mercado as nombre, IFNULL(bp.b,IFNULL(pc.b,r.bloqueado)) as valor "
					+ "FROM ( "
					+ "SELECT r.* FROM restriciones r   "
					+ "INNER JOIN (SELECT  MAX(idRestriciones) AS maxId,`codProductor`,idMercado,idTemporada,idEspecie FROM restriciones  "
					+ "where inicial='N'and carga!='L' and idEspecie=1 and idTemporada=2  GROUP BY `codProductor`,idMercado,idTemporada,idEspecie) rr  "
					+ "ON r.idEspecie=rr.idEspecie  and r.idTemporada=rr.idTemporada and rr.idTemporada=r.idTemporada and r.idMercado = rr.idMercado  "
					+ "and r.`codProductor`=rr.`codProductor` AND r.idRestriciones = rr.maxId) as r  "
					+ "left join productorBloqueo as bp on (bp.activo='Y' and bp.codProductor=r.codProductor and bp.idEspecie=r.idEspecie and bp.idTemporada=r.idTemporada and  bp.idMercado=r.idMercado)   "
					+ "inner join mercado m on (r.idMercado=m.idMercado)  "
					+ "inner join productor p on (r.codProductor=p.codProductor)  "
					+ "left join vw_productorCertificado pc on (r.codProductor=pc.codProductor) "
					+ "left join bloqueoOp bo on (bo.codProductor=r.codProductor and bo.idEspecie=r.idEspecie and bo.idTemporada=r.idTemporada  and  bo.idMercado=r.idMercado)"
					+ " where r.idEspecie=1  and r.idTemporada=2 and IF(bo.b='N',bo.b,IFNULL(bp.b,IFNULL(pc.b,r.bloqueado)))='N' "
					//+ " where r.idEspecie=1  and r.idTemporada=1 and r.bloqueado='N' "
					+ " ) as p ";

			if (filter.size() > 0) {
				String andSql="";
				andSql += " WHERE ";
				Iterator<filterSql> f = filter.iterator();
				
				while (f.hasNext()) 
				{
					filterSql row = f.next();
					if (!row.getValue().equals(""))
					{
						if (row.getCampo().endsWith("_to"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 3)+" <='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else if(row.getCampo().endsWith("_from"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 5)+" >='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else
						sql+=andSql+row.getCampo()+" like'%"+row.getValue()+"%'";
						andSql=" and ";
					}
				}

			}

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				total = rs.getInt(1);
			}
			rs.close();
			stmt.close();
			db.conn.close();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getLimitesAll: " + e.getMessage());
		} finally {
			db.close();
		}
		return total;
	}
	
	public static ArrayList<restriccion> getAllMercado(ArrayList<filterSql> filter, String order, int start, int length)
			throws Exception {

		ArrayList<restriccion> arr = new ArrayList<restriccion>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM (SELECT r.codProductor,m.mercado as nombre, IFNULL(bp.b,IFNULL(pc.b,r.bloqueado)) as valor,r.idEspecie "
					+ "FROM ( "
					+ "SELECT r.* FROM restriciones r   "
					+ "INNER JOIN (SELECT  MAX(idRestriciones) AS maxId,`codProductor`,idMercado,idTemporada,idEspecie FROM restriciones  "
					+ "where inicial='N'and carga!='L' and idEspecie=1 and idTemporada=2  GROUP BY `codProductor`,idMercado,idTemporada,idEspecie) rr  "
					+ "ON r.idEspecie=rr.idEspecie  and r.idTemporada=rr.idTemporada and rr.idTemporada=r.idTemporada and r.idMercado = rr.idMercado  "
					+ "and r.`codProductor`=rr.`codProductor` AND r.idRestriciones = rr.maxId) as r  "
					+ "left join productorBloqueo as bp on (bp.activo='Y' and bp.codProductor=r.codProductor and bp.idEspecie=r.idEspecie and bp.idTemporada=r.idTemporada and  bp.idMercado=r.idMercado)   "
					+ "inner join mercado m on (r.idMercado=m.idMercado)  "
					+ "inner join productor p on (r.codProductor=p.codProductor)  "
					+ "left join vw_productorCertificado pc on (r.codProductor=pc.codProductor) "
					+ "left join bloqueoOp bo on (bo.codProductor=r.codProductor and bo.idEspecie=r.idEspecie and bo.idTemporada=r.idTemporada  and  bo.idMercado=r.idMercado)"
					+ " where r.idEspecie=1  and r.idTemporada=2 and IF(bo.b='N',bo.b,IFNULL(bp.b,IFNULL(pc.b,r.bloqueado)))='N' "
					//+ " where r.idEspecie=1  and r.idTemporada=1 and r.bloqueado='N' "
					+ " ) as p ";


			if (filter.size() > 0) {
				String andSql="";
				andSql += " WHERE ";
				Iterator<filterSql> f = filter.iterator();

				while (f.hasNext()) 
				{
					filterSql row = f.next();
					
					if (!row.getValue().equals(""))
					{
						if (row.getCampo().endsWith("_to"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 3)+" <='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else if(row.getCampo().endsWith("_from"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 5)+" >='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else
						sql+=andSql+row.getCampo()+" like '%"+row.getValue()+"%'";
						andSql=" and ";
					}
				}

			}
			if (!order.equals("")) {
				sql += " order by "+order;
			}
			else
				sql += " order by codProductor ";

			if (length > 0) {
				sql += " limit " + start + "," + length + " ";
			}
			System.out.println("sql: " + sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				restriccion o = new restriccion();
				
				//, fecha, codProducto, limite, mercado, especie, Automatica
				o.setCodProductor(rs.getString("codProductor"));
				//o.setFecha(rs.getString("nMuestra"));
				//o.setCodProducto(rs.getString("codProducto"));
				//o.setLimite(rs.getString("limite"));
				
				o.setMercado(rs.getString("nombre"));
				if (rs.getString("idEspecie").equals("1"))
					o.setEspecie("ARANDANO");
				else
					o.setEspecie("CEREZA");
				o.setAutomatica("");
				arr.add(o);
			}
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getLimite: " + e.getMessage());
		} finally {
			db.close();
		}

		return arr;
	}

	
	
	
	//////////////
	public static int getAllcountZona5(ArrayList<filterSql> filter) throws Exception {

		int total = 0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT  count(1) FROM (SELECT distinct z.codProductor,  d.codProducto, z.limite,  z.especie, z.Automatica, z.mail, z.nMuestra "
					+ " FROM vw_bloqueadosZona as z"
					+ " inner join diccionario as d on (z.codProducto=d.codRemplazo and d.codProducto not in ('MULTI','MULTIE','MULTIN'))) as d ";

			if (filter.size() > 0) {
				String andSql="";
				andSql += " WHERE ";
				Iterator<filterSql> f = filter.iterator();
				
				while (f.hasNext()) 
				{
					filterSql row = f.next();
					if (!row.getValue().equals(""))
					{
						if (row.getCampo().endsWith("_to"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 3)+" <='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else if(row.getCampo().endsWith("_from"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 5)+" >='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else
						sql+=andSql+row.getCampo()+" like'%"+row.getValue()+"%'";
						andSql=" and ";
					}
				}

			}

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				total = rs.getInt(1);
			}
			rs.close();
			stmt.close();
			db.conn.close();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getLimitesAll: " + e.getMessage());
		} finally {
			db.close();
		}
		return total;
	}
	
	public static ArrayList<restriccion> getAllZona5(ArrayList<filterSql> filter, String order, int start, int length)
			throws Exception {

		ArrayList<restriccion> arr = new ArrayList<restriccion>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT distinct z.codProductor,  d.codProducto, z.limite,  z.especie, z.Automatica, z.mail, z.nMuestra,z.fechaIngreso "
					+ " FROM vw_bloqueadosZona as z"
					+ " inner join diccionario as d on (z.codProducto=d.codRemplazo and d.codProducto not in ('MULTI','MULTIE','MULTIN'))";

			if (filter.size() > 0) {
				String andSql="";
				andSql += " WHERE ";
				Iterator<filterSql> f = filter.iterator();

				while (f.hasNext()) 
				{
					filterSql row = f.next();
					
					if (!row.getValue().equals(""))
					{
						if (row.getCampo().endsWith("_to"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 3)+" <='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else if(row.getCampo().endsWith("_from"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 5)+" >='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else
						sql+=andSql+row.getCampo()+" like '%"+row.getValue()+"%'";
						andSql=" and ";
					}
				}

			}
			if (!order.equals("")) {
				sql += " order by "+order;
			}
			else
				sql += " order by codProductor ";

			if (length > 0) {
				sql += " limit " + start + "," + length + " ";
			}
			System.out.println("sql: " + sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				restriccion o = new restriccion();
				
				//, fecha, codProducto, limite, mercado, especie, Automatica
				o.setCodProductor(rs.getString("codProductor"));
				o.setnMuestra(rs.getString("nMuestra"));
				o.setCodProducto(rs.getString("codProducto"));
				o.setLimite(rs.getString("limite"));
				o.setFecha(rs.getString("fechaIngreso"));
				//o.setMercado(rs.getString("zona"));
				o.setEspecie(rs.getString("especie"));
				o.setAutomatica(rs.getString("Automatica"));
				arr.add(o);
			}
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getLimite: " + e.getMessage());
		} finally {
			db.close();
		}

		return arr;
	}
	
	public static JSONArray getEstadoProductorSyncSAP(int idTemporada,int idEspecie, String productor,String nombre,Boolean titulo) throws Exception {
		JSONArray rows = null;
		ConnectionDB db = new ConnectionDB();
		 Statement stmt = null;
		String sql = "";
		try {

			 stmt = db.conn.createStatement();

			 sql = "{CALL sp_viewRestricionesSAPSync("+idTemporada+","+idEspecie+",'"+productor+"','','','"+nombre+"') }";
			 System.out.println(sql);
			// ResultSet rs = stmt.executeQuery(sql);
			ResultSet rs = stmt.executeQuery(sql);

			rows = ResultSet2Json.getJson(rs);
			
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getEstadoProductor: " + e.getMessage());
		} finally {
			db.close();
		}

		return rows;
	}

}
