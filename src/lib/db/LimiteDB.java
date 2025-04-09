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

import lib.struc.Limite;
import lib.struc.filterSql;

public class LimiteDB {

	public static Limite getLimite(String idLimite)
	{
		ConnectionDB db = new ConnectionDB();
		Limite limite = null;
		Statement stmt = null;
		String sql = "";
		try
		{
			stmt = db.conn.createStatement();
			sql = "Select * from limites where idLimites="+idLimite;
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				limite = new Limite();
				limite.setIdLimite(rs.getInt("idLimites"));
				limite.setCodProducto(rs.getString("codProducto"));
				limite.setIdMercado(rs.getInt("idMercado"));
				limite.setIdTipoProducto(rs.getInt("idTipoProducto"));
				limite.setIdFuente(rs.getInt("idFuente"));
				limite.setLimite(rs.getString("limite"));
				limite.setCreado(rs.getDate("creado"));
				limite.setModificado(rs.getDate("modificacion"));
				limite.setIdEspecie(rs.getInt("idEspecie"));
			}
			rs.close();
			stmt.close();
		}catch(Exception ex)
		{
			System.out.println("Error: "+ex.getMessage());
		}finally {
			db.close();
		}
		return limite;
	}
	
	public static void updateLimite(Limite limite) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		ConnectionDB db = new ConnectionDB();
		try {
			db.conn.setAutoCommit(false);

			sql = "update  limites set codProducto=?,idMercado=?,idTipoProducto=?,idFuente=?,limite=?,  modificacion='"+d+"',idEspecie="+limite.getIdEspecie()+" where idLimites='" + limite.getIdLimite()
					+ "'";
			//System.out.println(limite.getCodProducto());
			//System.out.println(limite.getIdMercado());
			//System.out.println(limite.getIdTipoProducto());
			//System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.setString(1,limite.getCodProducto());
			ps.setInt(2, limite.getIdMercado());
			ps.setInt(3, limite.getIdTipoProducto());
			ps.setInt(4, limite.getIdFuente());
			ps.setString(5, limite.getLimite());


			ps.executeUpdate();
			db.conn.commit();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("updateLimite: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error2: " + e.getMessage());
			throw new Exception("updateLimite: " + e.getMessage());

		} finally {
			db.close();
		}
	}
	public static int getLimitesAll(ArrayList<filterSql> filter) throws Exception {

		int total = 0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT count(1) FROM limites l";

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
						if(row.getCampo().equals("l.codProducto")) {
							sql+=andSql+row.getCampo()+" like '"+row.getValue()+"' ";
							andSql=" and ";
						}else {
							sql+=andSql+row.getCampo()+" = "+row.getValue()+" ";
							andSql=" and ";
						}
						//sql+=andSql+row.getCampo()+" = "+row.getValue()+"";
						
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
			throw new Exception("getLimitesAll: " + e.getMessage());
		} finally {
			db.close();
		}
		return total;
	}
	
	public static ArrayList<Limite> getLimites(ArrayList<filterSql> filter, String order, int start, int length)
			throws Exception {

		ArrayList<Limite> limites = new ArrayList<Limite>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT l.*,m.mercado,t.tipoProducto, f.nombre as fuente FROM limites l "
					+ "inner join mercado m on (l.idMercado=m.idMercado) "
					+ "inner join tipoProducto t on (l.idTipoProducto=t.idTipoProducto) "
					+ "inner join fuente f on (l.idFuente=f.idFuente)";

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
						
						if(row.getCampo().equals("l.codProducto")) {
							sql+=andSql+row.getCampo()+" like '"+row.getValue()+"' ";
						}else {
							sql+=andSql+row.getCampo()+" = "+row.getValue()+" ";
						}
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
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Limite limite = new Limite();
				limite.setIdLimite(rs.getInt("idLimites"));
				limite.setCodProducto(rs.getString("codProducto"));
				limite.setIdMercado(rs.getInt("idMercado"));
				limite.setIdTipoProducto(rs.getInt("idTipoProducto"));
				limite.setIdFuente(rs.getInt("idFuente"));
				limite.setLimite(rs.getString("limite"));
				limite.setCreado(rs.getDate("creado"));
				limite.setModificado(rs.getDate("modificacion"));
				limite.setMercado(rs.getString("mercado"));
				limite.setTipoProducto(rs.getString("tipoProducto"));
				limite.setFuente(rs.getString("fuente"));
				limite.setIdEspecie(rs.getInt("idEspecie"));
				limites.add(limite);
			}
			System.out.println(limites.size());
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getLimite: " + e.getMessage());
		} finally {
			db.close();
		}

		return limites;
	}
	
	public static boolean insertLimite(Limite limite) throws ParseException
	{
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		boolean resp = true;
		String sql = "";
		try
		{
			sql = "INSERT INTO limites(codProducto,idMercado,idTipoProducto,idFuente,limite,creado,modificacion,idEspecie) Values ('"+limite.getCodProducto()+"',"+limite.getIdMercado()+","+limite.getIdTipoProducto()+","+limite.getIdFuente()+",'"+limite.getLimite()+"','"+d+"','"+d+"',"+limite.getIdEspecie()+")";
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
	
	public static Limite validaLimite(Limite limite)
	{
		ConnectionDB db = new ConnectionDB();
		Limite limit = null;
		Statement stmt = null;
		String sql = "";
		try
		{
			sql = "SELECT * FROM limites where idMercado="+limite.getIdMercado()+" and codProducto='"+limite.getCodProducto()+"' and idEspecie ="+ limite.getIdEspecie()+" and idTipoProducto="+limite.getIdTipoProducto()+" and idFuente="+limite.getIdFuente()+" and idLimites!="+limite.getIdLimite();
			System.out.println("limite: "+sql);
			stmt = db.conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				limit = new Limite();
				limit.setIdLimite(rs.getInt("idLimites"));
				limit.setCodProducto(rs.getString("codProducto"));
				limit.setIdMercado(rs.getInt("idMercado"));
				limit.setIdTipoProducto(rs.getInt("idTipoProducto"));
				limit.setIdFuente(rs.getInt("idFuente"));
				limit.setLimite(rs.getString("limite"));
				limit.setCreado(rs.getDate("creado"));
				limit.setModificado(rs.getDate("modificacion"));
				limite.setIdEspecie(rs.getInt("idEspecie"));
			}
			rs.close();
			stmt.close();
		}catch(Exception ex)
		{
			System.out.println("validaLimite Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return limit;
	}
	
	
	public static Limite validaLimiteExcel(Limite limite)
	{
		ConnectionDB db = new ConnectionDB();
		Limite limit = null;
		Statement stmt = null;
		String sql = "";
		try
		{
			sql = "SELECT * FROM limites where idMercado="+limite.getIdMercado()+" and codProducto='"+limite.getCodProducto()+"' and idEspecie='"+limite.getIdEspecie()+"'";
			//System.out.println(sql);
			stmt = db.conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				limit = new Limite();
				limit.setIdLimite(rs.getInt("idLimites"));
				limit.setCodProducto(rs.getString("codProducto"));
				limit.setIdMercado(rs.getInt("idMercado"));
				limit.setIdTipoProducto(rs.getInt("idTipoProducto"));
				limit.setIdFuente(rs.getInt("idFuente"));
				limit.setLimite(rs.getString("limite"));
				limit.setCreado(rs.getDate("creado"));
				limit.setModificado(rs.getDate("modificacion"));
				limite.setIdEspecie(rs.getInt("idEspecie"));
			}
			rs.close();
			stmt.close();
		}catch(Exception ex)
		{
			System.out.println("validaLimite Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return limit;
	}
	
	public static Limite validaLimiteExcel2(Limite limite)
	{
		ConnectionDB db = new ConnectionDB();
		Limite limit = null;
		Statement stmt = null;
		String sql = "";
		try
		{
			sql = "SELECT * FROM limites where idMercado='15' and codProducto='"+limite.getCodProducto()+"' and idEspecie='"+limite.getIdEspecie()+"'";
			//System.out.println(sql);
			stmt = db.conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				limit = new Limite();
				limit.setIdLimite(rs.getInt("idLimites"));
				limit.setCodProducto(rs.getString("codProducto"));
				limit.setIdMercado(rs.getInt("idMercado"));
				limit.setIdTipoProducto(rs.getInt("idTipoProducto"));
				limit.setIdFuente(rs.getInt("idFuente"));
				limit.setLimite(rs.getString("limite"));
				limit.setCreado(rs.getDate("creado"));
				limit.setModificado(rs.getDate("modificacion"));
				limite.setIdEspecie(rs.getInt("idEspecie"));
			}
			rs.close();
			stmt.close();
		}catch(Exception ex)
		{
			System.out.println("validaLimite Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return limit;
	}
	
	public static Limite validaLimiteExcelChina(Limite limite)
	{
		ConnectionDB db = new ConnectionDB();
		Limite limit = null;
		Statement stmt = null;
		String sql = "";
		try
		{
			sql = "SELECT * FROM limites where (limite=0 ) and  idMercado="+limite.getIdMercado()+" "
					+ " and codProducto='"+limite.getCodProducto()+"' and idEspecie='"+limite.getIdEspecie()+"'";
			//System.out.println(sql);
			stmt = db.conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				limit = new Limite();
				limit.setIdLimite(rs.getInt("idLimites"));
				limit.setCodProducto(rs.getString("codProducto"));
				limit.setIdMercado(rs.getInt("idMercado"));
				limit.setIdTipoProducto(rs.getInt("idTipoProducto"));
				limit.setIdFuente(rs.getInt("idFuente"));
				limit.setLimite(rs.getString("limite"));
				limit.setCreado(rs.getDate("creado"));
				limit.setModificado(rs.getDate("modificacion"));
				limite.setIdEspecie(rs.getInt("idEspecie"));
			}
			rs.close();
			stmt.close();
		}catch(Exception ex)
		{
			System.out.println("validaLimite Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return limit;
	}
	public static Limite validaLimiteExcelChinaMenor(Limite limite)
	{
		ConnectionDB db = new ConnectionDB();
		Limite limit = null;
		Statement stmt = null;
		String sql = "";
		try
		{
			sql = "SELECT * FROM limites where (limite>'"+limite.getLimite()+"' ) and  idMercado="+limite.getIdMercado()+" "
					+ " and codProducto='"+limite.getCodProducto()+"' and idEspecie='"+limite.getIdEspecie()+"'";
			//System.out.println(sql);
			stmt = db.conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				limit = new Limite();
				limit.setIdLimite(rs.getInt("idLimites"));
				limit.setCodProducto(rs.getString("codProducto"));
				limit.setIdMercado(rs.getInt("idMercado"));
				limit.setIdTipoProducto(rs.getInt("idTipoProducto"));
				limit.setIdFuente(rs.getInt("idFuente"));
				limit.setLimite(rs.getString("limite"));
				limit.setCreado(rs.getDate("creado"));
				limit.setModificado(rs.getDate("modificacion"));
				limite.setIdEspecie(rs.getInt("idEspecie"));
			}
			rs.close();
			stmt.close();
		}catch(Exception ex)
		{
			System.out.println("validaLimite Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return limit;
	}
}
