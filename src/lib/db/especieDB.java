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

import lib.struc.cargaManual;
import lib.struc.cargaManualDetalle;
import lib.struc.especie;
import lib.struc.filterSql;

public class especieDB {

	public static especie getId(String id)
	{
		ConnectionDB db = new ConnectionDB();
		especie o = new especie();
		Statement stmt = null;
		String sql = "";
		try
		{
			stmt = db.conn.createStatement();
			sql = "Select * from especie where idEspecie="+id;
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				
				
				o.setEspecie(rs.getString("especie"));
				o.setIdEspecie(rs.getInt("idEspecie"));
				
				
			}
			rs.close();
			stmt.close();
		}catch(Exception ex)
		{
			System.out.println("Error: "+ex.getMessage());
		}finally {
			db.close();
		}
		return o;
	}
	
	public static void update(cargaManual o) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		ConnectionDB db = new ConnectionDB();
		try {
			db.conn.setAutoCommit(false);

			sql = "update  limites set codProducto=?,idMercado=?,idTipoProducto=?,idFuente=?,limite=?,  "
					+ "modificacion='"+d+"' where idLimites='"
					+ "'";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.setString(1,o.getIdentificador());
	


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
	public static int getAllcount(ArrayList<filterSql> filter) throws Exception {

		int total = 0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT count(1) FROM cargaManual ";

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
			throw new Exception("getLimitesAll: " + e.getMessage());
		} finally {
			db.close();
		}
		return total;
	}
	
	public static ArrayList<especie> getAll(ArrayList<filterSql> filter, String order, int start, int length)
			throws Exception {

		ArrayList<especie> arr = new ArrayList<especie>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM especie ";

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
				sql += " order by "+order;
			}

			if (length > 0) {
				sql += " limit " + start + "," + length + " ";
			}
			ResultSet rs = stmt.executeQuery(sql);
//			especie o = new especie();
//			o.setIdEspecie(1);
//			o.setEspecie("ARANDANO");
//			o.setPf("B");
//		
//			arr.add(o);
			while (rs.next()) {
				especie o = new especie();
				System.out.println(rs.getString("especie"));
				o.setIdEspecie(rs.getInt("idEspecie"));
				o.setEspecie(rs.getString("especie"));
				o.setPf(rs.getString("pf"));
			
				arr.add(o);
			}
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

		return arr;
	}
	
	public static boolean insert(cargaManual o) throws ParseException
	{
		ConnectionDB db = new ConnectionDB();
		PreparedStatement ps = null;
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		boolean resp = true;
		String sql = "";
		try
		{
			sql = "INSERT INTO cargaManual(`fecha`,`idUsuario`,`creado`,`modificado`,`laboratorio`,`identificador`,codProductor) "
					+ "Values (now(),1,now(),now(),?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1,o.getLaboratorio());
			ps.setString(2,o.getIdentificador());
			ps.setString(3,o.getCodProductor());
			


			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			int resultado=0;
	        if (rs.next()){
	        	resultado=rs.getInt(1);
	        }
	        rs.close();
	        
			Iterator<cargaManualDetalle> f = o.getDetalle().iterator();

			while (f.hasNext()) {
				cargaManualDetalle row = f.next();
				
				System.out.println(row.getCodProducto());
				sql = "INSERT INTO cargaManualDetalle(`idCargaManual`,`codProducto`,`limite`) "
						+ "Values (?,?,?)";
				ps = db.conn.prepareStatement(sql);
				ps.setInt(1, resultado);
				ps.setString(2,row.getCodProducto());
				ps.setString(3,row.getLimite());
				


				ps.executeUpdate();
			}
			
			
			
			ps.close();
			
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
