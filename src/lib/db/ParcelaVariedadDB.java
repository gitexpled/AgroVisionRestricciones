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
import lib.struc.ParcelaVariedad;
import lib.struc.Parcela;
import lib.struc.Temporada;
import lib.struc.filterSql;
import lib.struc.grafico;

public class ParcelaVariedadDB {

	public static ParcelaVariedad get(String id) throws Exception {

		ParcelaVariedad o = null;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM parcelaVariedad where id='" + id + "'";
			System.out.println("sql: " + sql);

			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				o = new ParcelaVariedad();
				o.setId(rs.getInt("id"));
				o.setCodParcela(rs.getString("codParcela"));
				o.setIdVariedad(rs.getString("idVariedad"));
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

	public static void update(ParcelaVariedad o) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		ConnectionDB db = new ConnectionDB();
		try {
			db.conn.setAutoCommit(false);

			sql = "update  parcelaVariedad set codParcela=?,idVariedad=?, modificado='" + d + "' where id='" + o.getId() + "'";
			System.out.println("sql: " + sql);
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, o.getCodParcela());
			ps.setString(2, o.getIdVariedad());
			// ps.setInt(3, o.getIdUser());

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

	public static boolean delete(String id) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		boolean resp = true;
		try {
			db.conn.setAutoCommit(false);

			sql = "DELETE t1 ";
			sql += "FROM restriciones t1 ";
			sql += "inner join variedad v on (v.idVariedad=t1.idVariedad) ";
			sql += "INNER JOIN parcelaVariedad t2 ON (t1.codProductor=t2.codProductor and t1.codParcela=t2.codParcela and t1.codTurno=t2.codTurno and t2.idVariedad=v.cod) ";
			sql += "WHERE  id='" + id + "';";
			System.out.println("sql: " + sql);
			ps = db.conn.prepareStatement(sql);
			ps.executeUpdate();
			
			sql = "DELETE t1 ";
			sql += "FROM restriciones2 t1 ";
			sql += "inner join variedad v on (v.idVariedad=t1.idVariedad) ";
			sql += "INNER JOIN parcelaVariedad t2 ON (t1.codProductor=t2.codProductor and t1.codParcela=t2.codParcela and t1.codTurno=t2.codTurno and t2.idVariedad=v.cod) ";
			sql += "WHERE  id='" + id + "';";
			System.out.println("sql: " + sql);
			ps = db.conn.prepareStatement(sql);
			ps.executeUpdate();
			
			sql = "delete  from parcelaVariedad  where id='" + id + "';";
			System.out.println("sql: " + sql);
			ps = db.conn.prepareStatement(sql);
			ps.executeUpdate();
			
			db.conn.commit();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			resp = false;
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("sepPfx: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			resp = false;
			System.out.println("Error2: " + e.getMessage());
			throw new Exception("sepPfx: " + e.getMessage());

		} finally {
			db.close();
		}
		
		return resp;

	}

	public static int getBloqueadosHoy() throws Exception {

		int total = 0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "select sum(p) " + "from (SELECT count(DISTINCT codParcela) as p FROM restriciones where bloqueado='N' and fecha = sysdate() group by codParcela) as s ";

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

			sql = "SELECT count(1) FROM parcelaVariedad pv left join variedad v on (v.cod=pv.idVariedad) left join turno t on (pv.codParcela=t.codParcela and pv.codTurno=t.codTurno )";

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
							sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
						} else if (row.getCampo().endsWith("_from")) {
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
						} else {
							if (row.getCampo().equals("cod"))
								sql += andSql + "v." + row.getCampo() + " like '%" + row.getValue() + "%'";
							else if (row.getCampo().equals("codProductor"))
								sql += andSql + "t." + row.getCampo() + " like '%" + row.getValue() + "%'";
							else if (row.getCampo().equals("codVariedad"))
								sql += andSql + "pv.idVariedad" + " like '%" + row.getValue() + "%'";
							else
								sql += andSql + "pv." + row.getCampo() + " like '%" + row.getValue() + "%'";
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

	public static ArrayList<ParcelaVariedad> getAll(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
		ArrayList<ParcelaVariedad> Parcelaes = new ArrayList<ParcelaVariedad>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT pv.*,v.cod,t.codProductor FROM parcelaVariedad pv left join variedad v on (v.cod=pv.idVariedad) left join turno t on (pv.codParcela=t.codParcela and pv.codTurno=t.codTurno )";

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
							sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
						} else if (row.getCampo().endsWith("_from")) {
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
						} else {
							if (row.getCampo().equals("cod"))
								sql += andSql + "v." + row.getCampo() + " like '%" + row.getValue() + "%'";
							else if (row.getCampo().equals("codProductor"))
								sql += andSql + "t." + row.getCampo() + " like '%" + row.getValue() + "%'";
							else if (row.getCampo().equals("codVariedad"))
								sql += andSql + "pv.idVariedad" + " like '%" + row.getValue() + "%'";
							else
								sql += andSql + "pv." + row.getCampo() + " like '%" + row.getValue() + "%'";
						}
						andSql = " and ";
					}
				}

			}
				sql += " order by 1 desc ";

			if (length > 0) {
				sql += " limit " + start + "," + length + " ";
			}
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ParcelaVariedad row = new ParcelaVariedad();

				row.setId(rs.getInt("id"));
				row.setCodProductor(rs.getString("codProductor"));
				row.setCodParcela(rs.getString("codParcela"));
				row.setCodTurno(rs.getString("codTurno"));
				row.setIdVariedad(rs.getString("idVariedad"));
				row.setCodVariedad(rs.getString("cod"));
				row.setCreado(rs.getDate("creado"));
				row.setModificado(rs.getDate("modificado"));
				row.setIdUser(rs.getInt("idUser"));

				Parcelaes.add(row);
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

		return Parcelaes;
	}

	public static boolean insert(ParcelaVariedad o) throws ParseException {
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		boolean resp = true;
		String sql = "";
		try {

			sql = "INSERT INTO parcelaVariedad(codProductor,codParcela,codTurno,idVariedad,creado,modificado,idUser) Values ('" + o.getCodProductor() + "','" + o.getCodParcela() + "','" + o.getCodTurno() + "','"
					+ o.getIdVariedad() + "','" + d + "','" + d + "'," + o.getIdUser() + ")";
			System.out.println(sql);
			stmt = db.conn.createStatement();
			resp = stmt.execute(sql);
			stmt.close();
			

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		} finally {
			db.close();
		}
		return resp;
	}

}
