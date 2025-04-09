package lib.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import lib.struc.filterSql;
import lib.struc.eliminaVariedad;

public class eliminaVariedadDB {

	public static eliminaVariedad get(String id) throws Exception {

		eliminaVariedad o = null;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM eliminaVariedad where id='" + id + "'";
			System.out.println("sql: " + sql);

			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				o = new eliminaVariedad();
				o.setId(rs.getInt("id"));
				
				o.setVariedad(rs.getString("variedad"));
				

				
				} else {
				o = null;
			}

			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getUser: " + e.getMessage());
		} finally {
			db.close();
		}

		return o;
	}

	public static void update(eliminaVariedad o) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		ConnectionDB db = new ConnectionDB();
		try {
			db.conn.setAutoCommit(false);

			sql = "update  eliminaVariedad set etapa=?,campo=?,turno=?,idVariedad=?, modificado='" + d	+ "' where id='" + o.getId() + "'";
			System.out.println("sql: " + sql);
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, o.getVariedad());
			
			

			ps.executeUpdate();
			db.conn.commit();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("sepPfx: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error2: " + e.getMessage());
			throw new Exception("sepPfx: " + e.getMessage());

		} finally {
			db.close();
		}

	}
	
	
	public static void delete(String id) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		ConnectionDB db = new ConnectionDB();
		try {
			db.conn.setAutoCommit(false);

			sql = "delete  from  eliminaVariedad where id=?";
			System.out.println("sql: " + sql);
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, id);
			
			//ps.setInt(3, o.getIdUser());
		

			ps.executeUpdate();
			db.conn.commit();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("sepPfx: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error2: " + e.getMessage());
			throw new Exception("sepPfx: " + e.getMessage());

		} finally {
			db.close();
		}

	}


	

	public static int getAll(ArrayList<filterSql> filter) throws Exception {

		int total = 0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT  count(1) FROM eliminaVariedad pv";


			if (filter.size() > 0) {
				String andSql = "";
				andSql += " WHERE ";
				Iterator<filterSql> f = filter.iterator();

				while (f.hasNext()) {
					filterSql row = f.next();
					if (!row.getValue().equals("")) {
						if (row.getCampo().endsWith("_to")) {
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='"
									+ sqlDate.format(formatter.parse(row.getValue())) + "'";
						} else if (row.getCampo().endsWith("_from")) {
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='"
									+ sqlDate.format(formatter.parse(row.getValue())) + "'";
						} else
						{
							
								sql += andSql + "pv."+row.getCampo() + " like '%" + row.getValue() + "%'";
						}
						andSql = " and ";
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
			throw new Exception("getUsersAll: " + e.getMessage());
		} finally {
			db.close();
		}

		return total;
	}

	public static ArrayList<eliminaVariedad> getAll(ArrayList<filterSql> filter, String order, int start, int length)
			throws Exception {
		ArrayList<eliminaVariedad> arrays = new ArrayList<eliminaVariedad>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT  * FROM eliminaVariedad pv";

			if (filter.size() > 0) {
				String andSql = "";
				andSql += " WHERE ";
				Iterator<filterSql> f = filter.iterator();

				while (f.hasNext()) {
					filterSql row = f.next();

					if (!row.getValue().equals("")) {
						if (row.getCampo().endsWith("_to")) {
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='"
									+ sqlDate.format(formatter.parse(row.getValue())) + "'";
						} else if (row.getCampo().endsWith("_from")) {
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='"
									+ sqlDate.format(formatter.parse(row.getValue())) + "'";
						} else
						{
							
								sql += andSql + "pv."+row.getCampo() + " like '%" + row.getValue() + "%'";
						}
						andSql = " and ";
					}
				}

			}
			if (order.contains(":")) {
				String[] ord=order.split(":");
				sql += " order by "+ord[0] +" "+ord[1];
			}

			if (length > 0) {
				sql += " limit " + start + "," + length + " ";
			}
			System.out.println("sql: " + sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				eliminaVariedad row = new eliminaVariedad();
				
				row.setId(rs.getInt("id"));
				row.setVariedad(rs.getString("variedad"));
				
			
				arrays.add(row);
			}
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getUsers: " + e.getMessage());
		} finally {
			db.close();
		}

		return arrays;
	}

	public static boolean insert(eliminaVariedad o) throws ParseException {
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		
		boolean resp = true;
		String sql = "";
		try {

			sql = "INSERT INTO eliminaVariedad(variedad) Values ('"+o.getVariedad().replace("@", "\\'")+ "')";
			System.out.println(sql);
			stmt = db.conn.createStatement();
			resp = stmt.execute(sql);
			stmt.close();
			

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			db.close();
		}
		return resp;
	}

}
