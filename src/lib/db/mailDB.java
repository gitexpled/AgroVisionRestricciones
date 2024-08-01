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
import lib.struc.filterSql;
import lib.struc.user;

public class mailDB {
	
	
	public static String deleteTurnoVariedad(String codProducto) throws Exception {
		String respuesta="No se pudo eliminar";
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			db.conn.setAutoCommit(false);
			String[] ids=codProducto.split("_");
			sql = "update  mailExcel set estado=4  where ";
			
		
			sql+="  codProductor='"+ids[0]+"'";
			sql+=" and codParcela='"+ids[1]+"'";
			sql+=" and turno='"+ids[2]+"'";
			sql+=" and variedad='"+ids[3]+"'";
			sql+=" and REPLACE(proyecto, '/ ', '')='"+ids[4]+"'";
			System.out.println(sql);
			
		

			ps = db.conn.prepareStatement(sql);
			
			

			ps.executeUpdate();
			db.conn.commit();
			db.conn.close();
			respuesta="Registro eliminado";
			System.out.println("ok");
			System.out.println("ok");

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
		return respuesta;
	}
	
	public static String delete(String codProducto) throws Exception {
		String respuesta="No se pudo eliminar";
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			db.conn.setAutoCommit(false);

			sql = "update  mailExcel set estado=3  where codProducto=?";

			ps = db.conn.prepareStatement(sql);
			ps.setString(1,codProducto);
			

			ps.executeUpdate();
			db.conn.commit();
			db.conn.close();
			respuesta="Registro eliminado";
			System.out.println("ok");
			System.out.println("ok");

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
		return respuesta;
	}
	
	
	
	public static lib.struc.mail getMail(String id) throws Exception {

		lib.struc.mail o = null;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM mail where idGetMail='" + id + "'";
			
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				o = new lib.struc.mail();
				o.setFechaRecibido((rs.getString("fechaRecibido")));
				o.setFechaCarga(rs.getString("fechaCarga"));
				o.setArchivo(rs.getString("nombreArchivo"));
				o.setAsunto(rs.getString("asuntoMail"));
				o.setIdMail(rs.getInt("idMail"));
				o.setFile(null);
				
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
			throw new Exception("getUser: " + e.getMessage());
		} finally {
			db.close();
		}

		return o;
	}
	
	public static lib.struc.mail getMailFile(String id) throws Exception {

		lib.struc.mail o = null;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM mail where idMail='" + id + "'";
			
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				o = new lib.struc.mail();
				o.setFechaRecibido((rs.getString("fechaRecibido")));
				o.setFechaCarga(rs.getString("fechaCarga"));
				o.setArchivo(rs.getString("nombreArchivo"));
				o.setAsunto(rs.getString("asuntoMail"));
				o.setIdMail(rs.getInt("idMail"));
				o.setFile(rs.getBinaryStream("file"));
				
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
			throw new Exception("getUser: " + e.getMessage());
		} finally {
			db.close();
		}

		return o;
	}
	
	
	public static lib.struc.mail getMailMax() throws Exception {

		lib.struc.mail o = null;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM mail order by fechaCarga desc limit 1";
			
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				o = new lib.struc.mail();
				o.setFechaRecibido((rs.getString("fechaRecibido")));
				o.setFechaCarga(rs.getString("fechaCarga"));
				o.setArchivo(rs.getString("nombreArchivo"));
				o.setAsunto(rs.getString("asuntoMail"));
				o.setIdMail(rs.getInt("idMail"));
				o.setFile(rs.getBinaryStream("file"));
				
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
			throw new Exception("getUser: " + e.getMessage());
		} finally {
			db.close();
		}

		return o;
	}

	

	public static int getMailAll(ArrayList<filterSql> filter) throws Exception {

		int total = 0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT count(1) FROM mail ";

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
			throw new Exception("getUsersAll: " + e.getMessage());
		} finally {
			db.close();
		}

		return total;
	}

	public static ArrayList<lib.struc.mail> getMail(ArrayList<filterSql> filter, String order, int start, int length)
			throws Exception {
		ArrayList<lib.struc.mail> productores = new ArrayList<lib.struc.mail>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM mail ";

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
			}else
				sql += " order by fechaRecibido desc";

			if (length > 0) {
				sql += " limit " + start + "," + length + " ";
			}
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				lib.struc.mail row = new lib.struc.mail();
				
				
				row.setIdMail(rs.getInt("idMail"));
				row.setFechaRecibido((rs.getString("fechaRecibido")));
				row.setFechaCarga(rs.getString("fechaCarga"));
				row.setArchivo(rs.getString("nombreArchivo"));
				row.setAsunto(rs.getString("asuntoMail"));
				row.setLaboratorio(rs.getString("laboratorio"));
				row.setFile(null);
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
	
	
	
	
}
