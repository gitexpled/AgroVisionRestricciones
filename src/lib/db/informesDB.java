package lib.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class informesDB {
	
	public static String getDetalleRestriccion(int idEspecie,String idVariedad, String mercado,String productor,String parcela,String turno) throws Exception {
		ArrayList<String[]> data = new ArrayList<>();

		ConnectionDB db = new ConnectionDB();
		 Statement stmt = null;
		String sql = "";
		String html="";
		try {

			 stmt = db.conn.createStatement();

			 sql = "{CALL sp_viewDetalleRest("+idEspecie+",'"+idVariedad+"','"+mercado+"','"+productor+"','"+parcela+"','"+turno+"') }";
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
