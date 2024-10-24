package lib.db;


import java.sql.ResultSet;

import java.sql.Statement;


public class jerarquiaDB {

	public String getTurnos(String productor, String etapa, String campo, String variedad) {
		ConnectionDB db = new ConnectionDB();
		String data = "";
		Statement stmt = null;
		String sql = "";
		try {
			stmt = db.conn.createStatement();
			sql = "Select * from jerarquias where Productor='" + productor + "' and Etapa='" + etapa + "' and Campo='"
					+ campo + "' and VariedadDenomina='" + variedad + "'";
			ResultSet rs = stmt.executeQuery(sql);
			String coma="";
			while (rs.next()) {
				data += coma + rs.getString("Turno");
				coma=", ";

			}
			rs.close();
			stmt.close();
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		} finally {
			db.close();
		}
		return data;
	}

}
