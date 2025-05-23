package lib.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lib.struc.Limite;
import lib.struc.LimiteExcel;
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
			sql = "Select * from limites where estado=1 and idLimites="+idLimite;
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
	public static boolean deleteLimite(String id) throws Exception {
	    PreparedStatement ps = null;
	    String sql = "";
	    String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	    ConnectionDB db = new ConnectionDB();

	    try {
	        db.conn.setAutoCommit(false);

	        sql = "UPDATE limites SET estado = 0, modificacion = ? WHERE idLimites = ?";
	        ps = db.conn.prepareStatement(sql);
	        ps.setString(1, d);
	        ps.setString(2, id);

	        int rowsAffected = ps.executeUpdate();
	        db.conn.commit();
	        return rowsAffected > 0;

	    } catch (SQLException e) {
	        db.conn.rollback();
	        System.out.println("Error: " + e.getMessage());
	        System.out.println("SQL: " + sql);
	        throw new Exception("updateLimite: " + e.getMessage());
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Error2: " + e.getMessage());
	        throw new Exception("updateLimite: " + e.getMessage());
	    } finally {
	        db.close();
	    }
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

			sql = "SELECT count(1) FROM limites l where estado=1";

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
					+ "inner join fuente f on (l.idFuente=f.idFuente) where l.estado=1";

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
	
	public static boolean insertLimite(Limite limite, int idUser) throws ParseException {
	    String sql = "INSERT INTO limites " +
	                 "(codProducto, idMercado, idTipoProducto, idFuente, limite, creado, modificacion, idEspecie, idUser) " +
	                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    String fechaActual = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

		ConnectionDB db = new ConnectionDB();
	    try (PreparedStatement ps = db.conn.prepareStatement(sql)) {

	        ps.setString(1, limite.getCodProducto());
	        ps.setInt(2, limite.getIdMercado());
	        ps.setInt(3, limite.getIdTipoProducto());
	        ps.setInt(4, limite.getIdFuente());
	        ps.setString(5, limite.getLimite());
	        ps.setString(6, fechaActual);
	        ps.setString(7, fechaActual);
	        ps.setInt(8, limite.getIdEspecie());
	        ps.setInt(9, idUser);

	        int rowsAffected = ps.executeUpdate();
	        return rowsAffected > 0;

	    } catch (SQLException ex) {
	        System.err.println("Error al insertar límite: " + ex.getMessage());
	        return false;
	    }
	}
	
	public static Limite validaLimite(Limite limite)
	{
		ConnectionDB db = new ConnectionDB();
		Limite limit = null;
		Statement stmt = null;
		String sql = "";
		try
		{
			sql = "SELECT * FROM limites where estado=1 and idMercado="+limite.getIdMercado()+" and codProducto='"+limite.getCodProducto()+"' and idEspecie ="+ limite.getIdEspecie()+" and idTipoProducto="+limite.getIdTipoProducto()+" and idFuente="+limite.getIdFuente()+" and idLimites!="+limite.getIdLimite();
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
			sql = "SELECT * FROM limites where estado=1 and idMercado="+limite.getIdMercado()+" and codProducto='"+limite.getCodProducto()+"' and idEspecie='"+limite.getIdEspecie()+"'";
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
			sql = "SELECT * FROM limites where estado=1 and idMercado='15' and codProducto='"+limite.getCodProducto()+"' and idEspecie='"+limite.getIdEspecie()+"'";
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
			sql = "SELECT * FROM limites where estado = 1 and (limite=0 ) and  idMercado="+limite.getIdMercado()+" "
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
			sql = "SELECT * FROM limites where estado = 1 and (limite>'"+limite.getLimite()+"' ) and  idMercado="+limite.getIdMercado()+" "
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

	public static boolean upsertBatchLimites(ArrayList<LimiteExcel> rows, int idUsuario) {

	    ConnectionDB db = new ConnectionDB();
	    Connection   conn = db.conn;
	    PreparedStatement ps = null;

	    try {
	        conn.setAutoCommit(false);
	        Set<String> nombresEsp  = new HashSet<>();
	        Set<String> nombresFte  = new HashSet<>();
	        Set<String> nombresMer  = new HashSet<>();

	        for (LimiteExcel r : rows) {
	            nombresEsp.add(r.getEspecies().trim().toUpperCase());
	            nombresFte.add(r.getFuentes() .trim().toUpperCase());
	            nombresMer.add(r.getMercados().trim().toUpperCase());
	        }

	        Map<String,Integer> idEspMap = bulkIds(conn,
	            "SELECT especie, idEspecie FROM especie WHERE UPPER(especie) IN ",
	            nombresEsp);

	        Map<String,Integer> idFteMap = bulkIds(conn,
	            "SELECT nombre,  idFuente  FROM fuente  WHERE UPPER(nombre)  IN ",
	            nombresFte);

	        Map<String,Integer> idMerMap = bulkIds(conn,
	            "SELECT mercado, idMercado FROM mercado WHERE UPPER(mercado) IN ",
	            nombresMer);

	        String findSql = "SELECT idLimites FROM limites WHERE codProducto=? AND idMercado=? AND idTipoProducto=? AND idFuente=?   AND idEspecie=? LIMIT 1";
	        PreparedStatement find = conn.prepareStatement(findSql);

	        String insSql = "INSERT INTO limites (codProducto,idMercado,idTipoProducto,idFuente,limite,creado,modificacion,idEspecie,idUser,estado) VALUES (?,?,?,?,?,NOW(),NOW(),?,?,1)";
	        PreparedStatement insert = conn.prepareStatement(insSql);

	        String updSql = "UPDATE limites SET limite=?, modificacion=NOW(), idUser=?, estado=1 WHERE idLimites=?";
	        PreparedStatement update = conn.prepareStatement(updSql);
	        int idTipoProd = 2;
	        for (LimiteExcel r : rows) {

	            Integer idEsp = idEspMap.get(r.getEspecies().trim().toUpperCase());
	            Integer idFte = idFteMap.get(r.getFuentes() .trim().toUpperCase());
	            Integer idMer = idMerMap.get(r.getMercados().trim().toUpperCase());

	            if (idEsp == null || idFte == null || idMer == null)
	                throw new SQLException("Catálogo inexistente en fila: "
	                        + r.getEspecies());
	            find.setString(1, r.getIngredienteActivo());
	            find.setInt   (2, idMer);
	            find.setInt   (3, idTipoProd);
	            find.setInt   (4, idFte);
	            find.setInt   (5, idEsp);

	            Integer idLim = null;
	            try (ResultSet rs = find.executeQuery()) {
	                if (rs.next()) idLim = rs.getInt(1);
	            }

	            if (idLim == null) {
	                insert.setString (1, r.getIngredienteActivo());
	                insert.setInt    (2, idMer);
	                insert.setInt    (3, idTipoProd);
	                insert.setInt    (4, idFte);
	                insert.setBigDecimal(5,new BigDecimal(r.getLmr()));
	                insert.setInt    (6, idEsp);
	                insert.setInt    (7, idUsuario);
	                insert.executeUpdate();
	            } else {
	                update.setBigDecimal(1,new BigDecimal(r.getLmr()));
	                update.setInt       (2, idUsuario);
	                update.setInt       (3, idLim);
	                update.executeUpdate();
	            }
	        }

	        conn.commit();
	        return true;

	    } catch (Exception ex) {
	        try { conn.rollback(); } catch (SQLException ignored) {}
	        ex.printStackTrace();
	        return false;
	    } finally {
	        try { if (ps != null) ps.close(); } catch (SQLException ignored) {}
	        db.close();
	    }
	}

	private static Map<String,Integer> bulkIds(Connection conn,
	                                           String prefixSql,
	                                           Set<String> nombres) throws SQLException {

	    if (nombres.isEmpty()) return Collections.emptyMap();

	    String placeholders = nombres.stream().map(s -> "?")
	                                 .collect(Collectors.joining(","));
	    String sql = prefixSql + " (" + placeholders + ")";

	    Map<String,Integer> map = new HashMap<>();
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        int i = 1;
	        for (String n : nombres) ps.setString(i++, n);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next())
	                map.put(rs.getString(1).trim().toUpperCase(), rs.getInt(2));
	        }
	    }
	    return map;
	}

}
