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

import lib.struc.ProductorCertificacion;
import lib.struc.TipoProducto;
import lib.struc.filterSql;

public class ProductorCertificacionDB {

	public static ProductorCertificacion getProdCert(int idCert) throws Exception {

		ProductorCertificacion o = null;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM productorCertificacion where idCert='" + idCert + "'";
			
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				o = new ProductorCertificacion();
				o.setIdCert(rs.getInt("idCert"));
				o.setCodProductor(rs.getString("codProductor"));
				o.setIdCertificacion(rs.getInt("idCertificacion"));
				o.setVigencia(rs.getString("vigencia"));
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
			throw new Exception("getTipoProducto: " + e.getMessage());
		} finally {
			db.close();
		}

		return o;
	}

	public static int getProductorCertificacionAll(ArrayList<filterSql> filter) throws Exception {

		int total = 0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT count(1) FROM productorCertificacion ";

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
			throw new Exception("getTipoProductoAll: " + e.getMessage());
		} finally {
			db.close();
		}

		return total;
	}

	public static ArrayList<ProductorCertificacion> getProductorCertificacion(ArrayList<filterSql> filter, String order, int start, int length)
			throws Exception {

		ArrayList<ProductorCertificacion> productoresCert = new ArrayList<ProductorCertificacion>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT p. *,certificacionescol FROM productorCertificacion p inner join  certificaciones c on (c.idCertificaciones=p.idCertificacion)";

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
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ProductorCertificacion o = new ProductorCertificacion();
				o.setIdCert(rs.getInt("idCert"));
				o.setCodProductor(rs.getString("codProductor"));
				o.setIdCertificacion(rs.getInt("idCertificacion"));
				o.setVigencia(rs.getString("vigencia"));
				o.setCertificado(rs.getString("certificacionescol"));
				productoresCert.add(o);
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

		return productoresCert;
	}
	
	public static boolean insertProductorCertificacion(ProductorCertificacion productorCert) throws ParseException
	{
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		boolean resp = true;
		String sql = "";
		try
		{
			sql = "INSERT INTO productorCertificacion(codProductor,idCertificacion,vigencia) Values ("+productorCert.getCodProductor()+","+productorCert.getIdCertificacion()+",'"+productorCert.getVigencia()+"')";
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
	public static void updateTipoProducto(ProductorCertificacion productorCert) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		ConnectionDB db = new ConnectionDB();
		try {
			db.conn.setAutoCommit(false);

			sql = "update  productorCertificacion set codProductor=?,idCertificacion=?,vigencia=? where idCert=" + productorCert.getIdCert()
					+ "";

			ps = db.conn.prepareStatement(sql);
			ps.setString(1, productorCert.getCodProductor());
			ps.setInt(2, productorCert.getIdCertificacion());
			ps.setString(3, productorCert.getVigencia());


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
	public static ProductorCertificacion getCertificacionVigente(String codProductor,String certificacion){
		Statement stmt = null;
		String sql = "";
		ProductorCertificacion pc = null;
		ConnectionDB db = new ConnectionDB();
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		try{
			sql = "SELECT * FROM productorCertificacion where codProductor="+codProductor+" and idCertificacion="+certificacion+" and vigencia >= '"+d+"'";
			stmt = db.conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				pc = new ProductorCertificacion();
				pc.setIdCert(rs.getInt("idCert"));
				pc.setCodProductor(rs.getString("codProductor"));
				pc.setIdCertificacion(rs.getInt("idCertificacion"));
				pc.setVigencia(rs.getString("vigencia"));
			}
			stmt.close();
			rs.close();
		}catch(Exception ex)
		{
			System.out.println("Error (getCertificacionVigente): "+ex.getMessage());
		}finally {
			db.close();
		}
		return pc;
	}
}
