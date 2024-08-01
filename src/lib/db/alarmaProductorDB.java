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
import lib.struc.Temporada;
import lib.struc.alarmaProductor;
import lib.struc.cargaManual;
import lib.struc.cargaManualDetalle;
import lib.struc.filterSql;

public class alarmaProductorDB {

	public static alarmaProductor getId(String id)
	{
		ConnectionDB db = new ConnectionDB();
		alarmaProductor o = null;
		Statement stmt = null;
		String sql = "";
		try
		{
			stmt = db.conn.createStatement();
			String[] ids=id.split("_");
			sql = "Select * from `vw_faltanProductores` where     ";
			sql+="  codProductor='"+ids[0]+"'";
			sql+=" and codParcela='"+ids[1]+"'";
			sql+=" and codTurno='"+ids[2]+"'";
			sql+=" and idVariedad='"+ids[3]+"'";
			sql+=" and REPLACE(proyecto, '/ ', '')='"+ids[4]+"'";
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				o = new alarmaProductor();
				o.setCodProductor(rs.getString("codProductor"));
				o.setCodParcela(rs.getString("codParcela"));
				o.setCodTurno(rs.getString("codTurno"));
				o.setIdVariedad(rs.getString("idVariedad"));
				o.setProyecto(rs.getString("proyecto"));
			
				
				
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
	
	public static void update(alarmaProductor o) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		ConnectionDB db = new ConnectionDB();
		try {
			db.conn.setAutoCommit(false);

			
			sql = "update  mailExcel set codProductor=? , codParcela=?  , turno=?  , variedad=? where ";
			String[] ids=o.getKeyID().split("_");
			sql+="  codProductor='"+ids[0]+"'";
			sql+=" and codParcela='"+ids[1]+"'";
			sql+=" and turno='"+ids[2]+"'";
			sql+=" and variedad='"+ids[3]+"'";
			sql+=" and REPLACE(proyecto, '/ ', '')='"+ids[4]+"'";
			
			
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			int i=0;
			ps.setString(++i,o.getCodProductorNew());
			ps.setString(++i,o.getCodParcelaNew());
			ps.setString(++i,o.getCodTurnoNew());
			ps.setString(++i,o.getIdVariedadNew());
			
			
			

			
			ps.executeUpdate();
			System.out.println(o.getCodProductorNew()+"|"+o.getCodParcelaNew()+"|"+o.getCodTurnoNew()+"|"+o.getIdVariedadNew());
			db.conn.commit();
			Temporada temp=TemporadaDB.getMaxTemprada();
			procesosDB.setRestriciones(temp.getIdTemporada());
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("update: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error2: " + e.getMessage());
			throw new Exception("update: " + e.getMessage());

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

			sql = "SELECT count(1) FROM vw_faltanProductores ";

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
	
	public static ArrayList<alarmaProductor> getAll(ArrayList<filterSql> filter, String order, int start, int length)
			throws Exception {

		ArrayList<alarmaProductor> arr = new ArrayList<alarmaProductor>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM vw_faltanProductores ";

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
			while (rs.next()) {
				alarmaProductor o = new alarmaProductor();
				o.setCantidad(rs.getInt("cantidad"));
			
				o.setCodProductor(rs.getString("codProductor"));
				o.setCodParcela(rs.getString("codParcela"));
				o.setCodTurno(rs.getString("codTurno"));
				o.setIdVariedad(rs.getString("idVariedad"));
				o.setCantidad(rs.getInt("cantidad"));
				o.setProyecto(rs.getString("proyecto"));
				
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
	
	
	
}
