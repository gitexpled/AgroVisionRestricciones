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

import lib.struc.Turno;
import lib.struc.filterSql;



public class TurnoDB {
	public static String delete(String id) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		String resp = "No se pudo eliminar el registro";
		try {
			db.conn.setAutoCommit(false);

			sql = "DELETE t1 ";
			sql += "FROM restriciones t1 ";
			sql += "INNER JOIN turno t2 ON (t1.codProductor=t2.codProductor and t1.codParcela=t2.codParcela and t1.codTurno=t2.codTurno) ";
			sql += "WHERE  idTurno='" + id + "';";
			System.out.println("sql: " + sql);
			ps = db.conn.prepareStatement(sql);
			ps.executeUpdate();
			
			sql = "DELETE t1 ";
			sql += "FROM restriciones2 t1 ";
			sql += "INNER JOIN turno t2 ON (t1.codProductor=t2.codProductor and t1.codParcela=t2.codParcela and t1.codTurno=t2.codTurno) ";
			sql += "WHERE  idTurno='" + id + "';";
			System.out.println("sql: " + sql);
			ps = db.conn.prepareStatement(sql);
			ps.executeUpdate();
			
			sql = "DELETE t1 ";
			sql += "FROM parcelaVariedad t1 ";
			sql += "INNER JOIN turno t2 ON (t1.codProductor=t2.codProductor and t1.codParcela=t2.codParcela and t1.codTurno=t2.codTurno) ";
			sql += "WHERE  t2.idTurno='" + id + "';";
			System.out.println("sql: " + sql);
			ps = db.conn.prepareStatement(sql);
			ps.executeUpdate();
			
			
			
			sql = "delete  from turno  where idTurno='" + id + "';";
			System.out.println("sql: " + sql);
			ps = db.conn.prepareStatement(sql);
			ps.executeUpdate();
		
			db.conn.commit();
			db.conn.close();
			resp = "Se elimino el registro";

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			resp = "No se pudo eliminar el registro";
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("sepPfx: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			resp = "No se pudo eliminar el registro";
			System.out.println("Error2: " + e.getMessage());
			throw new Exception("sepPfx: " + e.getMessage());

		} finally {
			db.close();
		}
		
		return resp;

	}
	public static Turno get(String id) throws Exception {

		Turno o = null;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM turno where idTurno='" + id + "'";
			System.out.println("sql: " + sql);

			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				o = new Turno();
				o.setIdTurno(rs.getInt("idTurno"));
				o.setCodParcela(rs.getString("codParcela"));
				o.setCodProductor(rs.getString("codProductor"));
				o.setCodTurno(rs.getString("codTurno"));
				o.setNombre(rs.getString("nombreTurno"));
			
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
			throw new Exception("getUser: " + e.getMessage());
		} finally {
			db.close();
		}

		return o;
	}

	public static void update(Turno prod) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		ConnectionDB db = new ConnectionDB();
		try {
			db.conn.setAutoCommit(false);

			sql = "update  turno set nombre=?,codProductor=?,codParcela=?,codTurno=?, modificado='" + d
					+ "', idUser=? where codTurno='" + prod.getCodTurno() + "'";

			ps = db.conn.prepareStatement(sql);
			ps.setString(1, prod.getNombre());
			ps.setString(2, prod.getCodProductor());
			ps.setString(3, prod.getCodParcela());
			ps.setString(4, prod.getCodTurno());
			ps.setInt(5, prod.getIdUser());
		

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

			sql = "SELECT count(1) FROM turno ";

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
							if (row.getCampo().equals("codParcela"))
								sql += andSql + row.getCampo() + " = '" + row.getValue() + "'";
							else
								sql += andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
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

	public static ArrayList<Turno> getAll(ArrayList<filterSql> filter, String order, int start, int length)
			throws Exception {
		ArrayList<Turno> Parcelas = new ArrayList<Turno>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM turno ";

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
							if (row.getCampo().equals("codParcela"))
								sql += andSql + row.getCampo() + " = '" + row.getValue() + "'";
							else
								sql += andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
						}
						andSql = " and ";
					}
				}

			}
			if (!order.equals("")) {
				sql += " order by ";
			}

			if (length > 0) {
				sql += " limit " + start + "," + length + " ";
			}
			System.out.println("sql: " + sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Turno o = new Turno();
				
				o.setIdTurno(rs.getInt("idTurno"));
				o.setCodParcela(rs.getString("codParcela"));
				o.setCodProductor(rs.getString("codProductor"));
				o.setCodTurno(rs.getString("codTurno"));
				o.setNombre(rs.getString("nombreTurno"));
			
				o.setCreado(rs.getDate("creado"));
				o.setModificado(rs.getDate("modificado"));
				o.setIdUser(rs.getInt("idUser"));
			
			
				Parcelas.add(o);
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

		return Parcelas;
	}

	public static boolean insert(Turno o) throws ParseException {
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		boolean resp = true;
		String sql = "";
		try {

			sql = "INSERT INTO turno(codProductor,codParcela,codTurno,creado,modificado,idUser,nombreTurno) Values ('"
					+ o.getCodProductor() + "','" + o.getCodParcela() + "','" + o.getCodTurno()  + "','" + d + "','" + d + "',"
					+ o.getIdUser() + ",'"+o.getNombre()+"')";
			stmt = db.conn.createStatement();
			resp = stmt.execute(sql);
			stmt.close();
			//TemporadaDB.setCreateRestriciones();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			db.close();
		}
		return resp;
	}

}
