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

import lib.struc.Productor;
import lib.struc.Temporada;
import lib.struc.filterSql;
import lib.struc.grafico;

public class ProductorDB {
	public static String delete(String id) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		String resp = "No se pudo eliminar el registro";
		try {
			db.conn.setAutoCommit(false);
			
			sql = "DELETE t1 ";
			sql += "FROM restriciones t1 ";
			sql += "WHERE  codProductor='" + id + "';";
			System.out.println("sql: " + sql);
			ps = db.conn.prepareStatement(sql);
			ps.executeUpdate();
			
			sql = "DELETE t1 ";
			sql += "FROM restriciones2 t1 ";
			sql += "WHERE  codProductor='" + id + "';";
			System.out.println("sql: " + sql);
			ps = db.conn.prepareStatement(sql);
			ps.executeUpdate();
			
			sql = "DELETE t1 ";
			sql += "FROM parcelaVariedad t1 ";
			sql += "WHERE  codProductor='" + id + "';";
			System.out.println("sql: " + sql);
			ps = db.conn.prepareStatement(sql);
			ps.executeUpdate();
			
			sql = "DELETE t1 ";
			sql += "FROM turno t1 ";
			sql += "WHERE  codProductor='" + id + "';";
			System.out.println("sql: " + sql);
			ps = db.conn.prepareStatement(sql);
			ps.executeUpdate();
			
			
			
			sql = "delete  from parcela ";
			sql += "WHERE  codProductor='" + id + "';";
			System.out.println("sql: " + sql);
			ps = db.conn.prepareStatement(sql);
			ps.executeUpdate();
			
			sql = "delete  from productor ";
			sql += "WHERE  codProductor='" + id + "';";
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
	public static Productor getProductor(String idProductor) throws Exception {

		Productor o = null;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM productor where codProductor='" + idProductor + "'";
			System.out.println(sql);

			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				o = new Productor();
				o.setCodigo((rs.getString("codProductor")));
				o.setNombre(rs.getString("nombre"));
				o.setCodSap(rs.getString("codSap"));
				
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

	public static void updateProductor(Productor prod) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		ConnectionDB db = new ConnectionDB();
		try {
			db.conn.setAutoCommit(false);

			sql = "update  productor set nombre=?, modificado='" + d
					+ "', idUser=?, codSap=? where codProductor='" + prod.getCodigo() + "'";

			ps = db.conn.prepareStatement(sql);
			ps.setString(1, prod.getNombre());
			ps.setInt(2, prod.getIdUser());
			ps.setString(3, prod.getCodSap());
			

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

	public static int getBloqueados(int temporada) throws Exception {

		int total = 0;
		
		try {

			ArrayList<String[]> pp=exportarSapDB.view(temporada);
			
			for (String[] object: pp) {
			    System.out.println(object[1]);
			    if (object[1].contains("AX"))
			    	++total;
			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());

			throw new Exception("getUsersAll: " + e.getMessage());
		} 

		return total;
	}
	

	public static int getBloqueados2(int temporada) throws Exception {

		int total=0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		Temporada temp=TemporadaDB.getMaxTemprada();
		int idTemp=temp.getIdTemporada();
		try {

			stmt = db.conn.createStatement();

			
			
			sql="select sum(muestrados) from ( "
					+ " SELECT i.* from ( select a.zona as nombre, a.valor cantidad,b.valor muestrados, "
					+ " FORMAT(IFNULL((b.valor*100)/a.valor,0),1, 'de_DE') as valor from "
					+ "("
					+ "select p.zona, sum(1)  as valor "
					+ "from productor as p "
					+ "group by zona) as a "
					+ "left join ( "
					+ "select p.zona, sum(1) as valor from  "
					+ "(select max(idRestriciones),idEspecie, codProductor from restriciones where inicial='N' "
					+ "and idEspecie=1 AND idTemporada = 2 group by codProductor,idEspecie) as r "
					+ "inner join productor p on (p.codProductor=r.codProductor) "
					+ "group by p.zona) b on (a.zona=b.zona)) i"
					+ " inner join zona z on (z.nombre=i.nombre)  ) as p";
			
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				total=rs.getInt(1);
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

		return total;
	}
	
	public static int getBloqueadosCereza(int temporada) throws Exception {

		int total=0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		Temporada temp=TemporadaDB.getMaxTemprada();
		int idTemp=temp.getIdTemporada();
		try {

			stmt = db.conn.createStatement();

			
			
			sql="select sum(muestrados) from ( "
					+ " SELECT i.* from ( select a.zona as nombre, a.valor cantidad,b.valor muestrados, "
					+ " FORMAT(IFNULL((b.valor*100)/a.valor,0),1, 'de_DE') as valor from "
					+ "("
					+ "select p.zona, sum(1)  as valor "
					+ "from productor as p "
					+ "group by zona) as a "
					+ "left join ( "
					+ "select p.zona, sum(1) as valor from  "
					+ "(select max(idRestriciones),idEspecie, codProductor from restriciones where inicial='N' "
					+ "and idEspecie=2 AND idTemporada = 2 group by codProductor,idEspecie) as r "
					+ "inner join productor p on (p.codProductor=r.codProductor) "
					+ "group by p.zona) b on (a.zona=b.zona)) i"
					+ " inner join zona z on (z.nombre=i.nombre)  ) as p";
			
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				total=rs.getInt(1);
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

		return total;
	}

	public static int getBloqueadosHoy() throws Exception {

		int total = 0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "select sum(p) "
					+ "from (SELECT count(DISTINCT codProductor) as p FROM restriciones where bloqueado='N' and fecha = sysdate() group by codProductor) as s ";

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

	public static int getProductoresAll(ArrayList<filterSql> filter) throws Exception {

		int total = 0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT count(1) FROM productor ";

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

	public static ArrayList<Productor> getProductor(ArrayList<filterSql> filter, String order, int start, int length)
			throws Exception {
		ArrayList<Productor> productores = new ArrayList<Productor>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM productor ";

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
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Productor row = new Productor();

				row.setCodigo((rs.getString("codProductor")));
				row.setNombre(rs.getString("nombre"));
				row.setCreado(rs.getDate("creado"));
				row.setModificado(rs.getDate("modificado"));
			
				productores.add(row);
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

		return productores;
	}

	public static boolean insertProductor(Productor productor) throws ParseException {
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		boolean resp = true;
		String sql = "";
		try {

			sql = "INSERT INTO productor(codProductor,nombre,creado,modificado,idUser,codSap) Values ('"
					+ productor.getCodigo() + "','" + productor.getNombre() + "','" + d + "','" + d + "',"
					+ productor.getIdUser() + ",'" + productor.getCodSap() + "')";
			stmt = db.conn.createStatement();
			resp = stmt.execute(sql);
			stmt.close();
			TemporadaDB.setCreateRestriciones();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			db.close();
		}
		return resp;
	}

}
