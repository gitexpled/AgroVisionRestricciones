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

import lib.struc.Parcela;
import lib.struc.Parcela;
import lib.struc.Temporada;
import lib.struc.Variedad;
import lib.struc.filterSql;
import lib.struc.grafico;

public class VariedadDB {
	
	public static String getByCod(String id) throws Exception {

		String idVariedad="";
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM variedad where cod='" + id + "'";
			System.out.println("sql: " + sql);

			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				idVariedad=rs.getString("idVariedad");
				
				} else {
					idVariedad = null;
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

		return idVariedad;
	}
	
	public static String getById(String id) throws Exception {

		String idVariedad="";
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM variedad where idVariedad='" + id + "'";
			System.out.println("sql: " + sql);

			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				idVariedad=rs.getString("cod");
				
				} else {
					idVariedad = null;
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

		return idVariedad;
	}
	
	
	

	public static Parcela get(String id) throws Exception {

		Parcela o = null;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM parcela where codParcela='" + id + "'";
			System.out.println("sql: " + sql);

			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				o = new Parcela();
				o.setCodigo((rs.getString("codParcela")));
				o.setCodigoProductor(rs.getString("codProductor"));
				o.setNombre(rs.getString("nombre"));
			
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

	public static void update(Parcela prod) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		ConnectionDB db = new ConnectionDB();
		try {
			db.conn.setAutoCommit(false);

			sql = "update  parcela set nombre=?,codProductor=?, modificado='" + d
					+ "', idUser=? where codParcela='" + prod.getCodigo() + "'";

			ps = db.conn.prepareStatement(sql);
			ps.setString(1, prod.getNombre());
			ps.setString(2, prod.getCodigoProductor());
			ps.setInt(3, prod.getIdUser());
		

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

	
	
	

	public static int getBloqueadosHoy() throws Exception {

		int total = 0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "select sum(p) "
					+ "from (SELECT count(DISTINCT codParcela) as p FROM restriciones where bloqueado='N' and fecha = sysdate() group by codParcela) as s ";

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

	public static int getAll(ArrayList<filterSql> filter) throws Exception {

		int total = 0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT count(1) FROM parcela ";

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
							sql += andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
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

	public static ArrayList<Variedad> getAll(ArrayList<filterSql> filter, String order, int start, int length)
			throws Exception {
		ArrayList<Variedad> Variedades = new ArrayList<Variedad>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM variedad ";

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
							sql += andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
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
			System.out.println("Aqui: "+sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Variedad row = new Variedad();
				
				row.setCod(rs.getString("cod"));
				row.setNombre(rs.getString("nombre"));
				row.setIdVariedad(rs.getString("idVariedad"));
			
			
				Variedades.add(row);
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

		return Variedades;
	}

	public static boolean insert(Parcela o) throws ParseException {
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		boolean resp = true;
		String sql = "";
		try {

			sql = "INSERT INTO parcela(codParcela,nombre,creado,modificado,idUser,codProductor) Values ('"
					+ o.getCodigo() + "','" + o.getNombre() + "','" + d + "','" + d + "',"
					+ o.getIdUser() + ",'"+ o.getCodigoProductor() +"')";
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
	
	
	
	
	public static ArrayList<Variedad> getAllTurno(ArrayList<filterSql> filter, String order, int start, int length)
			throws Exception {
		ArrayList<Variedad> Variedades = new ArrayList<Variedad>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT DISTINCT v.* FROM variedad v inner join parcelaVariedad t on (v.cod=t.idVariedad)";

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
							sql += andSql +"t."+ row.getCampo() + " = '" + row.getValue() + "'";
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
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Variedad row = new Variedad();
				
				row.setCod(rs.getString("cod"));
				row.setNombre(rs.getString("nombre"));
				row.setIdVariedad(rs.getString("idVariedad"));
			
			
				Variedades.add(row);
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

		return Variedades;
	}

}
