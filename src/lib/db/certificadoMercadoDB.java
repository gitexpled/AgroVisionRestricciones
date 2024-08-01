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
import lib.struc.certificado;

public class certificadoMercadoDB {
	//public static ConnectionDB db = null;
	public static certificado getCertificado(int id) throws Exception {

		certificado o = null;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM certificadoMercado where id='" + id + "'";
			System.out.println("sql: " + sql);
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				o = new certificado();
				o.setId(rs.getInt("id"));
				o.setIdcertificado(rs.getString("idCertificado"));
				o.setIdmercado(rs.getString("idMercado"));
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
			throw new Exception("getCertificado: " + e.getMessage());
		} finally {
			db.close();
		}

		return o;
	}

	public static void updateCertficado(certificado c) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			db.conn.setAutoCommit(false);

			sql = "update  certificadoMercado set idCertificado=?, idMercado =? where idcertificado='" + c.getId()
					+ "'";

			ps = db.conn.prepareStatement(sql);
			ps.setString(1, c.getIdcertificado());
			ps.setString(2, c.getIdmercado());
			System.out.println("sql: " + sql);
			ps.executeUpdate();
			db.conn.commit();
			db.conn.close();
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

	}

	public static int getCertificadosAll(ArrayList<filterSql> filter) throws Exception {

		int total = 0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT count(1) FROM certificado ";

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
			throw new Exception("getCertificadosAll: " + e.getMessage());
		} finally {
			db.close();
		}

		return total;
	}

	public static ArrayList<certificado> getCerificados(ArrayList<filterSql> filter, String order, int start, int length)
			throws Exception {

		ArrayList<certificado> certificados = new ArrayList<certificado>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT id, c.nombre certificado, m.mercado ";
			sql += " FROM certificadoMercado cm ";
			sql += " LEFT JOIN certificado c ON c.idcertificado = cm.idCertificado ";
			sql += " LEFT JOIN vw_mercados m ON m.idMercado = cm.idMercado  ";
			System.out.println("sql: " + sql);
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
				certificado row = new certificado();
				
				row.setId(rs.getInt("id"));
				row.setNombre(rs.getString("certificado"));
				row.setMercado(rs.getString("mercado"));
				certificados.add(row);
			}
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getCerificados: " + e.getMessage());
		} finally {
			db.close();
		}

		return certificados;
	}
	
	public static boolean insertCertificado(certificado cr) throws ParseException
	{
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		boolean resp = true;
		String sql = "";
		try
		{
			sql = "INSERT INTO certificadoMercado(idCertificado, idMercado) Values ('"+cr.getIdcertificado()+"','"+cr.getIdmercado()+"')";
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
	
	public static ArrayList<certificado> getAllCertificado()
	{
		ConnectionDB db = new ConnectionDB();
		ArrayList<certificado> users = new ArrayList<certificado>();
		Statement stmt = null;
		String sql = "";
		try
		{
			sql = "Select * from certificado";
			stmt = db.conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				certificado us = new certificado();
				us.setId(rs.getInt("idcertificado"));
				us.setNombre(rs.getString("nombre"));
				users.add(us);
			}
			stmt.close();
			rs.close();
		}catch(Exception ex)
		{
			System.out.println("getAllCertificado: "+ex.getMessage());
		}
		return users;
	}
	
}
