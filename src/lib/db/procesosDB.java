package lib.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class procesosDB {
	
	
	public static void setRestriciones( int idTemporada)throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			
			db.conn.setAutoCommit(false);

			
			sql = "{ CALL sp_createRest("+idTemporada+") }";
					//,`file`)
			
			

			ps = db.conn.prepareStatement(sql);
			ps.executeUpdate();
			db.conn.commit();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("setRestriciones: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			throw new Exception("setRestriciones: " + e.getMessage());
		} finally {
			db.close();
		}

	}

}
