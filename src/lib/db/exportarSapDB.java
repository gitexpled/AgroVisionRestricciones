package lib.db;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import lib.struc.Fuente;

public class exportarSapDB {

	public static ArrayList<String[]> view(int idTemporada) throws Exception {
		ArrayList<String[]> data = new ArrayList<>();

		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		String rest = "";
		int export = 0;
		try {

			stmt = db.conn.createStatement();

			sql = "{CALL sp_viewRestricionesSAP(" + idTemporada + ") }";

			// ResultSet rs = stmt.executeQuery(sql);
			ResultSet rs = stmt.executeQuery(sql);

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			int j = 0;
			while (rs.next()) {

				String[] o = new String[columnCount + 1];

				for (int i = 1; i <= columnCount; i++) {
					if (rs.getString(i) == null)
						o[i - 1] = "";
					else {
						o[i - 1] = rs.getString(i).toString().replace("  ", "").trim();
						
						/*if(i == 1) {
							export = getEspecies(Integer.parseInt(rs.getString(i).toString().replace("  ", "").trim()));
						}*/
						
						//System.out.println(rs.getString(i).toString().replace("  ", "").trim());
						if (i == 2) {
							String[] tmp = rs.getString(i).toString().split(" ");
							Arrays.sort(tmp);
							StringBuilder builder = new StringBuilder();
							for (String value : tmp) {
								rest=value.trim();
								/*if(export == 0) {
									rest=value.trim();
								}else if(export == 1) {
									if(value.trim().startsWith("B")) {
										rest=value.trim();
									}else {
										rest = "";
									}
								}else if(export == 2) {
									if(value.trim().startsWith("C")) {
										rest=value.trim();
									}else {
										rest = "";
									}
								}*/
								//String rest=value.trim();
								if (!rest.equals(""))
									//System.out.println(rest);
									builder.append(rest+",");
							}
							System.out.println(builder.toString());
							String text = builder.toString().trim();
							//AA,AB,AC,AE,AH,AJ,AN,AO,AT,AU 
							//AA,AB,AC,AD,AE,AH,AJ,AL,AM,AN,AO,AR,AT,AU 
							//CA,CB,CC,CD,CE,CH,CJ,CL,CM,CN,CO,CR,CT,CU
							//Al reemplazar el prefijo de ARANDANO por 'b' se cambio las siguientes
							//2 lineas de código y ademas se agrego el mercado UK 'BK' y 'CK'
							text=text.replace("BA,BB,BC,BD,BE,BH,BI,BJ,BK,BL,BM,BN,BO,BR,BT,BU", "BX");
							text=text.replace("CA,CB,CC,CD,CE,CH,CI,CJ,CK,CL,CM,CN,CO,CR,CT,CU", "CX");
							text=text.replace(",", " ").trim();
							//
							o[i - 1] = text.trim();
							//System.out.println(text.trim());
						}
					}

				}
				// System.out.println(Arrays.toString(o));
				++j;
				data.add(o);
			}
			//System.out.println(data.get(0));
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
	
	public static int getEspecies(int codProductor)
			throws Exception {

		int export = 0;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT arandano,cereza FROM productor\n" + 
					"WHERE codProductor = "+codProductor+
					" AND bloqueado = 'N'";

			ResultSet rs = stmt.executeQuery(sql);

			if(rs.next()) {
				
				if(rs.getString("cereza") == null) {
					if(rs.getString("arandano").equalsIgnoreCase("Y") && rs.getString("cereza") == null) {
						export = 1;
					}
				}else {
					if(rs.getString("arandano").equalsIgnoreCase("Y") && rs.getString("cereza").equalsIgnoreCase("Y")) {
						export = 0;
					}else if(rs.getString("arandano").equalsIgnoreCase("Y") && rs.getString("cereza").equalsIgnoreCase("N") ||  rs.getString("cereza").equalsIgnoreCase("")) {
						export = 1;
					}else if(rs.getString("arandano").equalsIgnoreCase("N") && rs.getString("cereza").equalsIgnoreCase("Y")) {
						export = 2;
					}else if(rs.getString("arandano").equalsIgnoreCase("N") && rs.getString("cereza").equalsIgnoreCase("N")) {
						export = 3;
					}
				}
			}
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getEspecies: " + e.getMessage());
		} finally {
			db.close();
		}

		return export;
	}
	
	public static String getEstadoProductor(int idTemporada, int idEspecie, String productor, String Mercado)
			throws Exception {

		String Estado = "";
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "{CALL sp_viewRestriciones(" + idTemporada + "," + idEspecie + ",'" + productor + "') }";

			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {

				Estado = rs.getString(Mercado);
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

}
