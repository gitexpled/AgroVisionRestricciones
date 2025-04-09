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

import lib.struc.MercadoProductor;
import lib.struc.Parcela;
import lib.struc.ParcelaVariedad;
import lib.struc.Parcela;
import lib.struc.Temporada;
import lib.struc.filterSql;
import lib.struc.grafico;

public class MercadoProductorDB {

	public static MercadoProductor get(String id) throws Exception {

		MercadoProductor o = null;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM mercadoProductor where id='" + id + "'";
			System.out.println("sql: " + sql);

			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				o = new MercadoProductor();
				o.setId(rs.getInt("id"));
				o.setCodProductor(rs.getString("codProductor"));
				o.setCodEtapa(rs.getString("etapa"));
				o.setCodCampo(rs.getString("campo"));
				o.setCodTurno(rs.getString("turno"));
				o.setIdVariedad(rs.getString("idVariedad"));
				o.setIdMercado(rs.getString("idMercado"));
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

	public static void update(MercadoProductor o) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		ConnectionDB db = new ConnectionDB();
		try {
			db.conn.setAutoCommit(false);

			sql = "update  mercadoProductor set etapa=?,campo=?,turno=?,idVariedad=?, modificado='" + d	+ "' where id='" + o.getId() + "'";
			System.out.println("sql: " + sql);
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, o.getCodEtapa());
			ps.setString(2, o.getCodCampo());
			ps.setString(3, o.getCodTurno());
			ps.setString(4, o.getIdVariedad());
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
	
	
	public static void delete(String id) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		ConnectionDB db = new ConnectionDB();
		try {
			db.conn.setAutoCommit(false);

			sql = "delete  from  mercadoProductor where id=?";
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

			sql = "SELECT  count(1) FROM mercadoProductor pv"
					+ " inner join mercado m on (m.idMercado=pv.idMercado) "
					+ "   inner join  jerarquias r on (r.productor=pv.productor and   r.etapa=pv.etapa  and r.campo=pv.campo and r.Turno=pv.turno   and r.VariedadDenomina=pv.idVariedad) ";


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
							if (row.getCampo().equals("cod"))
								sql += andSql + "v.nombre like '%" + row.getValue() + "%'";
							else if (row.getCampo().equals("mercado"))
								sql += andSql + "m."+row.getCampo() + " like '%" + row.getValue() + "%'";
							else if (row.getCampo().equals("codVariedad"))
								sql += andSql + "v.nombre  like '%" + row.getValue() + "%'";
							else 
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

	public static ArrayList<MercadoProductor> getAll(ArrayList<filterSql> filter, String order, int start, int length)
			throws Exception {
		ArrayList<MercadoProductor> arrays = new ArrayList<MercadoProductor>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT  pv.*,m.mercado FROM mercadoProductor pv"
					+ " inner join mercado m on (m.idMercado=pv.idMercado)  "
					+ "   inner join  jerarquias r on (r.productor=pv.productor  and r.etapa=pv.etapa and r.Turno=pv.turno  and r.campo=pv.campo   and r.VariedadDenomina=pv.idVariedad) ";

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
							else if (row.getCampo().equals("mercado"))
								sql += andSql + "m."+row.getCampo() + " like '%" + row.getValue() + "%'";
							else if (row.getCampo().equals("codVariedad"))
								sql += andSql + "v.nombre  like '%" + row.getValue() + "%'";
							else 
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
				MercadoProductor row = new MercadoProductor();
				
				row.setId(rs.getInt("id"));
				row.setCodProductor(rs.getString("productor"));
				row.setCodEtapa(rs.getString("etapa"));
				row.setCodCampo(rs.getString("campo"));
				row.setCodTurno(rs.getString("turno"));
				row.setIdVariedad(rs.getString("idVariedad"));
				row.setCodVariedad(rs.getString("idVariedad"));
				row.setMercado(rs.getString("mercado"));
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
			throw new Exception("getUsers: " + e.getMessage());
		} finally {
			db.close();
		}

		return arrays;
	}

	public static boolean insert(MercadoProductor o) throws ParseException {
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		boolean resp = true;
		String sql = "";
		try {

			sql = "INSERT INTO mercadoProductor(productor,etapa,campo,turno,idVariedad,idMercado,creado,modificado,idUser) Values ('"
					+ o.getCodProductor()+ "','"+ o.getCodEtapa()+ "','" + o.getCodCampo()+ "','" + o.getCodTurno() + "','" + o.getIdVariedad()+ "','" + o.getIdMercado()+ "','" + d + "','" + d + "',"
					+ o.getIdUser() + ")";
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
