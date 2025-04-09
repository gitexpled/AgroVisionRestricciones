package lib.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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

public class TemporadaDB {
	
	public static Temporada getMaxTemprada() throws Exception {

		Temporada o = null;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM temporada order by idTemporada desc limit 1";
			
			
			
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				o = new Temporada();
				o.setIdTemporada(rs.getInt("idTemporada"));
				o.setTemporada(rs.getString("temporada"));
				o.setCreado(rs.getDate("creacion"));
				o.setIdUser(rs.getInt("idUser"));
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
			throw new Exception("getTemporadas: " + e.getMessage());
		} finally {
			db.close();
		}

		return o;
	}
	public  int getMaxTemprada(int especie) throws Exception {

		Temporada o = null;
		int tem=0;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM temporada t inner join  especie e on (t.idEspecie=e.pf)   where e.idEspecie='"+especie+"' order by idTemporada desc limit 1";
			
			
			
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				o = new Temporada();
				o.setIdTemporada(rs.getInt("idTemporada"));
				tem=rs.getInt("idTemporada");
				o.setTemporada(rs.getString("temporada"));
				o.setCreado(rs.getDate("creacion"));
				o.setIdUser(rs.getInt("idUser"));
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
			throw new Exception("getTemporadas: " + e.getMessage());
		} finally {
			db.close();
		}

		return tem;
	}
	public  int getMaxTemprada(String especie) throws Exception {

		Temporada o = null;
		int tem=0;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM temporada where idEspecie='"+especie+"' order by idTemporada desc limit 1";
			
			
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				o = new Temporada();
				o.setIdTemporada(rs.getInt("idTemporada"));
				tem=rs.getInt("idTemporada");
				o.setTemporada(rs.getString("temporada"));
				o.setCreado(rs.getDate("creacion"));
				o.setIdUser(rs.getInt("idUser"));
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
			throw new Exception("getTemporadas: " + e.getMessage());
		} finally {
			db.close();
		}

		return tem;
	}
	
	public static Temporada getTemporada(String idTemporada) throws Exception {

		Temporada o = null;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT t.*,u.user FROM temporada t left join user u on (u.idUser=t.idUser)  where idTemporada='" + idTemporada + "'";
			
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				o = new Temporada();
				o.setIdTemporada(rs.getInt("idTemporada"));
				o.setTemporada(rs.getString("temporada"));
				o.setIdEspecie(rs.getString("idEspecie"));
				o.setDesde(rs.getString("desde"));
				o.setHasta(rs.getString("hasta"));
				o.setCreado(rs.getDate("creacion"));
				o.setIdUser(rs.getInt("idUser"));
				o.setUsuario(rs.getString("user"));
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
			throw new Exception("getTemporadas: " + e.getMessage());
		} finally {
			db.close();
		}

		return o;
	}

	public static int getTemporadasAll(ArrayList<filterSql> filter) throws Exception {

		int total = 0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT count(1) FROM temporada ";

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
			throw new Exception("getTemporadasAll: " + e.getMessage());
		} finally {
			db.close();
		}

		return total;
	}

	public static ArrayList<Temporada> getTemporada(ArrayList<filterSql> filter, String order, int start, int length)
			throws Exception {

		ArrayList<Temporada> temporadas = new ArrayList<Temporada>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM temporada ";

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
			if (order.contains(":")) {
				String[] ord=order.split(":");
				sql += " order by "+ord[0] +" "+ord[1];
			}

			if (length > 0) {
				sql += " limit " + start + "," + length + " ";
			}
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Temporada o = new Temporada();
				
				o.setIdTemporada(rs.getInt("idTemporada"));
				o.setTemporada(rs.getString("temporada"));
				o.setCreado(rs.getDate("creacion"));
				o.setIdUser(rs.getInt("idUser"));
				o.setIdEspecie(rs.getString("idEspecie"));
				o.setDesde(rs.getString("desde"));
				o.setHasta(rs.getString("hasta"));
				temporadas.add(o);
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

		return temporadas;
	}
	
	public static boolean insertTemporada(Temporada t) throws ParseException
	{
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		boolean resp = true;
		String sql = "";
		try
		{
			sql = "INSERT INTO temporada(idUser,temporada,creacion, idEspecie, desde, hasta) Values ('"+t.getIdUser()+"','"+t.getTemporada()+"','"+d+"', '"+t.getIdEspecie()+"' , '"+t.getDesde()+"', '"+t.getHasta()+"')";
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
	public static void updateTemporada(Temporada temp) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		ConnectionDB db = new ConnectionDB();
		try {
			db.conn.setAutoCommit(false);

			sql = "update  temporada set temporada=?,  idEspecie=? , desde='"+temp.getDesde()+"',  hasta='"+temp.getHasta()+"'  where idTemporada='" + temp.getIdTemporada()+ "'";
			
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, temp.getTemporada());
			ps.setString(2, temp.getIdEspecie());
			


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
	
	
	
}
