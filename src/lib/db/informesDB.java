package lib.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class informesDB {
	
	public ArrayList<ArrayList<String>> getLmrDetalle(int idTemporada,int idEspecie,String idVariedad, String Mercado,String productor,String etapa,String campo,String turno) throws Exception {
		ArrayList<ArrayList<String>> list = new ArrayList<>();

		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = " SELECT r.creado,r.entregado,r.code,  IF(r.producto LIKE '%fosetyl%','fosetil',IF(r.producto LIKE '%fosetil%','fosetil',r.producto)) AS codProducto, j.Turno,  ";
			sql += " r.lmr as resultado,format(l.limite,5) lmr,format(l.limite2,5) lmr2,CONCAT(format((( r.lmr * 100) / IFNULL(l.limite2, l.limite)),2),'%') as Porcentaje,'' as vigente,IF(r.lmr<l.limite or r.lmr=0 , 0, 1) as habilitado";
			sql += " FROM jerarquias j JOIN temporada t ";
			sql += " inner join  especie e on (j.Especie=e.pf)  ";
			sql += " inner join  resultadoDet r on (r.productor=j.Productor and r.etapa=j.Etapa and r.campo=j.Campo and r.turno=j.Turno and r.especie=e.codLab   and r.variedad=j.VariedadDenomina)  ";
			sql += " inner join diccionario d on (d.codRemplazo=r.producto) ";
			sql += " inner join vw_limite l on (d.codProducto=l.codProducto and e.idEspecie=l.idEspecie)   ";
			sql += " inner join vw_mercados m on (l.idMercado=m.idMercado)  ";
			sql += "where  ";
			sql += " t.idTemporada='"+idTemporada+"'  ";
			
			sql += "  and e.idEspecie='"+idEspecie+"' ";
			
			if (!productor.isEmpty())
			sql += " and j.Productor='"+productor+"' ";
			
			if (!etapa.isEmpty())
				sql += " and j.Etapa='"+etapa+"' ";
			
			if (!campo.isEmpty())
				sql += " and j.Campo='"+campo+"' ";
				
			if (!turno.isEmpty())
				sql += " and j.Turno='"+turno+"' ";
			
			if (!idVariedad.isEmpty())
				sql += " and j.VariedadDenomina='"+idVariedad+"' ";
			if (!Mercado.isEmpty())
				sql += " and m.idMercado='"+Mercado+"' ";
		
			//sql+="Order by Idmailexcel desc";

			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			ArrayList<String> j = new ArrayList<>();
			for (int i = 1; i <= columnCount; i++) {
				
			
				if (rsmd.getColumnLabel(i).equals("resultado"))
				{
					String htmlData2="Resultado (ppm)";
					j.add(htmlData2);
				}
				else
				{
					String htmlData  = rsmd.getColumnLabel(i); 
					htmlData = htmlData.substring(0,1).toUpperCase() + htmlData.substring(1).toLowerCase();
					j.add(htmlData);
					
				}
				
				
			}
			list.add(j);
			while (rs.next()) {
				ArrayList<String> l = new ArrayList<>();
				l.add(rs.getString(1).replace(" 00:00:00.0", ""));
				l.add(rs.getString(2).replace(" 00:00:00.0", ""));
				l.add(rs.getString(3));
				l.add(rs.getString(4));
				l.add(rs.getString(5));
				l.add(rs.getString(6));
				l.add(rs.getString(7));
				l.add(rs.getString(8));
				l.add(rs.getString(9));
				
				if (rs.getString(10).equals("1"))
					l.add("SI");
				else
					l.add("NO");
				if (rs.getString(11).equals("0"))
					l.add("SI");
				else
					l.add("NO");
				
				//l.add(rs.getString(12));
				

				list.add(l);
			}
			rs.close();
			stmt.close();
			db.close();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getLmr: " + e.getMessage());
		} finally {
			db.close();
		}

		return list;
	}
	public  String getDetalleRestriccion(int idtemporada,int idEspecie,String idVariedad, String mercado,String productor,String etapa,String campo,String turno) throws Exception {
		ArrayList<String[]> data = new ArrayList<>();

		ConnectionDB db = new ConnectionDB();
		 Statement stmt = null;
		String sql = "";
		String html="";
		try {

			

			 ArrayList<ArrayList<String>> dataLmr=getLmrDetalle(idtemporada, idEspecie, idVariedad, mercado, productor, etapa,campo, turno);
			 
			 html="<table border=1>";
			for (ArrayList<String> arrayList : dataLmr) {
				html+="<tr>";
				for (String arr : arrayList) {
					html+="<td>&nbsp;";
					
					if (arr==null)
						html+="";
					else
						html+=arr;
					html+="&nbsp;</td>";
				}
				html+="</tr>";
			}
			 html+="</table>";
			
			
		

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getEstadoProductor: " + e.getMessage());
		} finally {
			
		}

		return html;
	}
	
	
	public static String getDetalleRestriccionM(int idEspecie,String idVariedad, String mercado,String productor,String parcela,String turno) throws Exception {
		ArrayList<String[]> data = new ArrayList<>();

		ConnectionDB db = new ConnectionDB();
		 Statement stmt = null;
		String sql = "";
		String html="";
		try {

			 stmt = db.conn.createStatement();

			 sql = "{CALL sp_viewDetalleRestM("+idEspecie+",'"+idVariedad+"','"+mercado+"','"+productor+"','"+parcela+"','"+turno+"') }";
			 System.out.println(sql);
			// ResultSet rs = stmt.executeQuery(sql);
			ResultSet rs = stmt.executeQuery(sql);

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			int j=0;
			html="<table border=1>";
			
			html+="<tr>";
			for (int i = 1; i <= columnCount; i++) {
				html+="<td>&nbsp;";
			
				if (rsmd.getColumnLabel(i).equals("resultado"))
					html+="Resultado (ppm)";
				else
				{
					String htmlData  = rsmd.getColumnLabel(i); 
					htmlData = htmlData.substring(0,1).toUpperCase() + htmlData.substring(1).toLowerCase();
					html+=htmlData;
				}
					
				html+="&nbsp;</td>";
				
			}
			html+="</tr>";
			
			while (rs.next()) {

				String[] o = new String[columnCount];
				html+="<tr>";
				for (int i = 1; i <= columnCount; i++) {
					html+="<td>&nbsp;";
					o[i-1] = rs.getString(i);
					if (rs.getString(i)==null)
						html+="";
					else
						html+=o[i-1];
					html+="&nbsp;</td>";
					
				}
				html+="</tr>";
				++j;
				data.add(o);
			}
			 html+="</table>";
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

		return html;
	}
	
	
	public static String getDetalleRestriccion2(int idEspecie,String idVariedad, String mercado,String productor,String parcela) throws Exception {
		ArrayList<String[]> data = new ArrayList<>();

		ConnectionDB db = new ConnectionDB();
		 Statement stmt = null;
		String sql = "";
		String html="";
		try {

			 stmt = db.conn.createStatement();

			 sql = "{CALL sp_viewDetalleRest2("+idEspecie+",'"+idVariedad+"','"+mercado+"','"+productor+"','"+parcela+"') }";
			
			// ResultSet rs = stmt.executeQuery(sql);
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println(sql);

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			int j=0;
			html="<table border=1>";
			
			html+="<tr>";
			for (int i = 1; i <= columnCount; i++) {
				html+="<td>&nbsp;";
			
				if (rsmd.getColumnLabel(i).equals("resultado"))
					html+="Resultado (ppm)";
				else
				{
					String htmlData  = rsmd.getColumnLabel(i); 
					htmlData = htmlData.substring(0,1).toUpperCase() + htmlData.substring(1).toLowerCase();
					html+=htmlData;
				}
				html+="&nbsp;</td>";
				
			}
			html+="</tr>";
			
			while (rs.next()) {

				String[] o = new String[columnCount];
				html+="<tr>";
				for (int i = 1; i <= columnCount; i++) {
					html+="<td>&nbsp;";
					o[i-1] = rs.getString(i);
					if (rs.getString(i)==null)
						html+="";
					else
						html+=o[i-1];
					html+="&nbsp;</td>";
					
				}
				html+="</tr>";
				++j;
				data.add(o);
			}
			 html+="</table>";
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

		return html;
	}
	
	
	public static String getDetalleRestriccionM2(int idEspecie,String idVariedad, String mercado,String productor,String parcela) throws Exception {
		ArrayList<String[]> data = new ArrayList<>();

		ConnectionDB db = new ConnectionDB();
		 Statement stmt = null;
		String sql = "";
		String html="";
		try {

			 stmt = db.conn.createStatement();

			 sql = "{CALL sp_viewDetalleRestM2("+idEspecie+",'"+idVariedad+"','"+mercado+"','"+productor+"','"+parcela+"') }";
			
			// ResultSet rs = stmt.executeQuery(sql);
			ResultSet rs = stmt.executeQuery(sql);

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			int j=0;
			html="<table border=1>";
			
			html+="<tr>";
			for (int i = 1; i <= columnCount; i++) {
				html+="<td>&nbsp;";
			
				if (rsmd.getColumnLabel(i).equals("resultado"))
					html+="Resultado (ppm)";
				else
				{
					String htmlData  = rsmd.getColumnLabel(i); 
					htmlData = htmlData.substring(0,1).toUpperCase() + htmlData.substring(1).toLowerCase();
					html+=htmlData;
				}
					
				html+="&nbsp;</td>";
				
			}
			html+="</tr>";
			
			while (rs.next()) {

				String[] o = new String[columnCount];
				html+="<tr>";
				for (int i = 1; i <= columnCount; i++) {
					html+="<td>&nbsp;";
					o[i-1] = rs.getString(i);
					if (rs.getString(i)==null)
						html+="";
					else
						html+=o[i-1];
					html+="&nbsp;</td>";
					
				}
				html+="</tr>";
				++j;
				data.add(o);
			}
			 html+="</table>";
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

		return html;
	}
	
	
	public static String getDetalleExcel(String id) throws Exception {
		ArrayList<String[]> data = new ArrayList<>();

		ConnectionDB db = new ConnectionDB();
		 Statement stmt = null;
		String sql = "";
		String html="";
		try {

			 stmt = db.conn.createStatement();

			sql = "{CALL sp_viewDetalleExcel('"+id+"') }";
			
			// ResultSet rs = stmt.executeQuery(sql);
			ResultSet rs = stmt.executeQuery(sql);

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			int j=0;
			html="<table border=1>";
			
			html+="<tr>";
			for (int i = 1; i <= columnCount; i++) {
				html+="<td>";
			
				html+=rsmd.getColumnName(i);
				html+="</td>";
				
			}
			html+="</tr>";
			
			while (rs.next()) {

				String[] o = new String[columnCount];
				html+="<tr>";
				for (int i = 1; i <= columnCount; i++) {
					html+="<td>";
					o[i-1] = rs.getString(i);
					if (rs.getString(i)==null)
						html+="";
					else
						html+=o[i-1];
					html+="</td>";
					
				}
				html+="</tr>";
				++j;
				data.add(o);
			}
			 html+="</table>";
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

		return html;
	}

}
