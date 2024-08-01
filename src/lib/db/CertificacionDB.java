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

import lib.struc.Certificacion;
import lib.struc.filterSql;

public class CertificacionDB {

	public static Certificacion getCertificacion(String idCertificacion) throws Exception {

		Certificacion o = null;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM certificaciones where idCertificaciones='" + idCertificacion + "'";
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				o = new Certificacion();
				o.setIdCertificaciones(rs.getInt("idCertificaciones"));
				o.setCertificacionesCol(rs.getString("certificacionescol"));
				o.setCreado(rs.getDate("creacion"));
				o.setModificado(rs.getDate("modificacion"));
				o.setIdUser(rs.getInt("idUser"));
				o.setPrefijo(rs.getString("prefijo"));
			} else {
				o= null;
			}

			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getCertificaciones: " + e.getMessage());
		} finally {
			db.close();
		}

		return o;
	}
	
	public static Certificacion getCertificacionStr(String idCertificacion) throws Exception {

		Certificacion o = null;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM certificaciones where certificacionescol='" + idCertificacion + "'";
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				o = new Certificacion();
				o.setIdCertificaciones(rs.getInt("idCertificaciones"));
				o.setCertificacionesCol(rs.getString("certificacionescol"));
				o.setCreado(rs.getDate("creacion"));
				o.setModificado(rs.getDate("modificacion"));
				o.setIdUser(rs.getInt("idUser"));
				o.setPrefijo(rs.getString("prefijo"));
			} else {
				o= null;
			}

			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getCertificaciones: " + e.getMessage());
		} finally {
			db.close();
		}

		return o;
	}

	public static void updateCertificacion(Certificacion certificacion) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		ConnectionDB db = new ConnectionDB();
		try {
			db.conn.setAutoCommit(false);

			sql = "update  certificaciones set idUser=?,certificacionescol=?,prefijo=?,  modificacion='"+d+"' where idCertificaciones='" +certificacion.getIdCertificaciones()
					+ "'";

			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, certificacion.getIdUser());
			ps.setString(2, certificacion.getCertificacionesCol());
			ps.setString(3, certificacion.getPrefijo());

			ps.executeUpdate();
			db.conn.commit();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("sepCertificacion: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error2: " + e.getMessage());
			throw new Exception("sepCertificacion: " + e.getMessage());

		} finally {
			db.close();
		}

	}

	public static int getCertificacionesAll(ArrayList<filterSql> filter) throws Exception {

		int total = 0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT count(1) FROM certificaciones ";

			if (filter.size() > 0) {
				String andSql="";
				andSql += " WHERE ";
				Iterator<filterSql> f = filter.iterator();
				
				while (f.hasNext()) 
				{
					filterSql row = f.next();
					if (!row.getValue().equals(""))
					{
						if (row.getCampo().endsWith("_to"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 3)+" <='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else if(row.getCampo().endsWith("_from"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 5)+" >='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else
						sql+=andSql+row.getCampo()+" like'%"+row.getValue()+"%'";
						andSql=" and ";
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
			throw new Exception("getCertificacionesAll: " + e.getMessage());
		} finally {
			db.close();
		}

		return total;
	}

	public static ArrayList<Certificacion> getCertificacion(ArrayList<filterSql> filter, String order, int start, int length)
			throws Exception {

		ArrayList<Certificacion> certificaciones = new ArrayList<Certificacion>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM certificaciones ";

			if (filter.size() > 0) {
				String andSql="";
				andSql += " WHERE ";
				Iterator<filterSql> f = filter.iterator();

				while (f.hasNext()) 
				{
					filterSql row = f.next();
					
					if (!row.getValue().equals(""))
					{
						if (row.getCampo().endsWith("_to"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 3)+" <='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else if(row.getCampo().endsWith("_from"))
						{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							 SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql+=andSql+row.getCampo().substring(0, row.getCampo().length() - 5)+" >='"+ sqlDate.format(formatter.parse(row.getValue()))+"'";
						}
						else
						sql+=andSql+row.getCampo()+" like '%"+row.getValue()+"%'";
						andSql=" and ";
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
				Certificacion o = new Certificacion();
				
				o.setIdCertificaciones(rs.getInt("idCertificaciones"));
				o.setCertificacionesCol(rs.getString("certificacionescol"));
				o.setPrefijo(rs.getString("prefijo"));
				o.setCreado(rs.getDate("creacion"));
				o.setModificado(rs.getDate("modificacion"));
				o.setIdUser(rs.getInt("idUser"));
				certificaciones.add(o);
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

		return certificaciones;
	}
	
	public static boolean insertCertificacion(Certificacion certificacion) throws ParseException
	{
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		boolean resp = true;
		String sql = "";
		try
		{
			sql = "INSERT INTO certificaciones(idUser,certificacionescol,prefijo,creacion,modificacion) Values ('"+certificacion.getIdUser()+"','"+certificacion.getCertificacionesCol()+"','"+certificacion.getPrefijo()+"','"+d+"','"+d+"')";
			System.out.println(sql);
			stmt = db.conn.createStatement();
			resp = stmt.execute(sql);
			stmt.close();
			
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}finally
		{
			db.close();
		}
		return resp;
	}
}
