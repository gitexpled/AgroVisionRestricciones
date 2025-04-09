package lib.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import lib.struc.habilitacionManual;
import lib.struc.filterSql;


public class habilitacionManualDB {

	public static habilitacionManual get(String id) throws Exception {

		habilitacionManual o = null;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM habilitacionManual where id='" + id + "'";
			System.out.println("sql: " + sql);

			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				o = new habilitacionManual();
				o.setId(rs.getInt("id"));
				o.setProductor(rs.getString("productor"));
				o.setEtapa(rs.getString("etapa"));
				o.setCampo(rs.getString("campo"));
				o.setIdVariedad(rs.getString("variedad"));
				o.setMercado(rs.getString("mercado"));
				o.setHabilitado(rs.getString("habilitado"));
				o.setCreado(rs.getDate("creado"));
				o.setModificado(rs.getDate("modificado"));
				o.setIdUser(rs.getInt("idUser"));
			
				
				
				
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
			throw new Exception("get: " + e.getMessage());
		} finally {
			db.close();
		}

		return o;
	}

	public static void update(habilitacionManual o) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		ConnectionDB db = new ConnectionDB();
		try {
			db.conn.setAutoCommit(false);

			sql = "update  habilitacionManual set productor=?, etapa=?,campo=?,habilitado=?,variedad=?,mercado=?, modificado='" + d	+ "' where id='" + o.getId() + "'";
			System.out.println("sql: " + sql);
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, o.getProductor());
			ps.setString(2, o.getEtapa());
			ps.setString(3, o.getCampo());
			ps.setString(4, o.getHabilitado());
			ps.setString(5, o.getIdVariedad().replace("@", "'"));
			ps.setString(6, o.getMercado());
			
		

			ps.executeUpdate();
			db.conn.commit();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("update: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error2: " + e.getMessage());
			throw new Exception("update: " + e.getMessage());

		} finally {
			db.close();
		}

	}
	
	
	public static void delete(String id) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			db.conn.setAutoCommit(false);

			sql = "delete  from  habilitacionManual where id=?";
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
			throw new Exception("delete: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error2: " + e.getMessage());
			throw new Exception("delete: " + e.getMessage());

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

			sql = "SELECT  count(1) FROM habilitacionManual pv";

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
							System.out.println(row.getCampo());
							if (row.getCampo().equals("cod"))
								sql += andSql + "v.nombre like '%" + row.getValue() + "%'";
							else 
								sql += andSql +row.getCampo() + " like '%" + row.getValue() + "%'";
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
			throw new Exception("getAll : " + e.getMessage());
		} finally {
			db.close();
		}

		return total;
	}

	public static ArrayList<habilitacionManual> getAll(ArrayList<filterSql> filter, String order, int start, int length)
			throws Exception {
		ArrayList<habilitacionManual> arrays = new ArrayList<habilitacionManual>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT  * FROM habilitacionManual pv";


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
							System.out.println(row.getCampo());
							if (row.getCampo().equals("cod"))
								sql += andSql + "v.nombre like '%" + row.getValue() + "%'";
							else 
								sql += andSql +row.getCampo() + " like '%" + row.getValue() + "%'";
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
				habilitacionManual row = new habilitacionManual();
				
				row.setId(rs.getInt("id"));
				row.setProductor(rs.getString("productor"));
				row.setEtapa(rs.getString("etapa"));
				row.setCampo(rs.getString("campo"));
				row.setIdVariedad(rs.getString("variedad"));
				row.setMercado(rs.getString("mercado"));
				row.setHabilitado(rs.getString("habilitado"));
				row.setCreado(rs.getDate("creado"));
				row.setModificado(rs.getDate("modificado"));
				row.setIdUser(rs.getInt("idUser"));
			
				arrays.add(row);
			}
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getAll Arr: " + e.getMessage());
		} finally {
			db.close();
		}

		return arrays;
	}

	public static boolean insert(habilitacionManual o) throws Exception {
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		boolean resp = true;
		String sql = "";
		try {

			sql = "INSERT INTO habilitacionManual(productor,etapa,campo,habilitado,creado,modificado,idUser,variedad,mercado) Values ('"
					+ o.getProductor()+ "','"+ o.getEtapa()+ "','" + o.getCampo()+ "','" + o.getHabilitado() + "','" + d + "','" + d + "',"
					+ o.getIdUser() + ",'"+o.getIdVariedad().replace("@", "\\'")+"','"+o.getMercado()+"')";
			stmt = db.conn.createStatement();
			resp = stmt.execute(sql);
			stmt.close();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("insert: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error2: " + e.getMessage());
			throw new Exception("insert: " + e.getMessage());

		} finally {
			db.close();
		}

		return resp;
	}

}
