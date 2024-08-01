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

import lib.struc.bloqueo;
import lib.struc.cargaManual;
import lib.struc.cargaManualDetalle;
import lib.struc.filterSql;

public class bloqueadoOpDB {

	public static bloqueo getId(String id)
	{
		ConnectionDB db = new ConnectionDB();
		bloqueo o = null;
		Statement stmt = null;
		String sql = "";
		try
		{
			stmt = db.conn.createStatement();
			sql = "Select pb.*,u.user from bloqueoOp pb  left join user u on (pb.idUsuario=u.idUser) where pb.idBloqueo="+id;
			
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				o = new bloqueo();
				o.setIdBloqueo(rs.getInt("idBloqueo"));
				o.setCodProductor(rs.getString("codProductor"));
				o.setIdEspecie(rs.getInt("idEspecie"));
				o.setIdMercado(rs.getInt("idMercado"));
				o.setComentario(rs.getString("cometario"));
				
				//o.setActivo(rs.getString("activo"));
				o.setB(rs.getString("b"));
				o.setCreado(rs.getString("creado"));
				o.setModificado(rs.getString("modificado"));
				if (rs.getString("user")==null)
					o.setUsuario("sistema");
				else
					o.setUsuario(rs.getString("user"));
				
				
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
	
	public static void update(bloqueo o) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			db.conn.setAutoCommit(false);

			sql = "update  bloqueoOp set "
					//+ "codProductor=?,"
					//+ "idEspecie=?,"
					+ "cometario=?,"
					+ "b=?,"
					+ "idUsuario=?,  "
					//+ "idTemporada=?,  "
					//+ "activo=?,"
					//+ "idMercado=?,"
					+ "modificado=now() "
					+ "where idBloqueo=?";
			
			
			ps = db.conn.prepareStatement(sql);
			
			//ps.setString(1, o.getCodProductor());
			//ps.setInt(2,o.getIdEspecie());
			ps.setString(1, o.getComentario());
			ps.setString(2, o.getB());
			ps.setInt(3, o.getIdUsuario());
			//ps.setInt(6, o.getIdTemporada());
			//ps.setString(7, o.getActivo());
			//ps.setInt(8, o.getIdMercado());
			
			ps.setInt(4, o.getIdBloqueo());
	


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

			sql = "SELECT count(1) FROM vw_bloqueoOp ";

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
	
	public static ArrayList<bloqueo> getAll(ArrayList<filterSql> filter, String order, int start, int length)
			throws Exception {

		ArrayList<bloqueo> arr = new ArrayList<bloqueo>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT o.*,m.mercado FROM vw_bloqueoOp o  "
					+ " inner join mercado m on (m.idMercado=o.idMercado) ";

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
						else if (row.getCampo().equals("mercado"))
							sql+=andSql+""+row.getCampo()+" like '%"+row.getValue()+"%'";
						else
						sql+=andSql+"o."+row.getCampo()+" like '%"+row.getValue()+"%'";
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
			System.out.println("sql: " + sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				bloqueo o = new bloqueo();
				
				o.setIdBloqueo(rs.getInt("idBloqueo"));
				o.setCodProductor(rs.getString("codProductor"));
				o.setMercado(rs.getString("mercado"));
				o.setEspecie(rs.getString("especie"));
				o.setB(rs.getString("b"));
				//o.setActivo(rs.getString("activo"));
				o.setModificado(rs.getString("modificado"));
				o.setUsuario(rs.getString("user"));
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
	
	public static boolean insert(bloqueo o) throws ParseException
	{
		ConnectionDB db = new ConnectionDB();
		PreparedStatement ps = null;
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		boolean resp = true;
		String sql = "";
		try
		{
			sql = "INSERT INTO bloqueoOp(codProductor, idEspecie, cometario, b, idUsuario, creado, modificado, idTemporada) "
					+ "Values (?,?,?,?,?,now(),now(),?,?,?)";
			ps = db.conn.prepareStatement(sql);
			
			ps.setString(1, o.getCodProductor());
			ps.setInt(2,o.getIdEspecie());
			ps.setString(3, o.getComentario());
			ps.setString(4, o.getB());
			ps.setInt(5, o.getIdUsuario());
			ps.setInt(6, o.getIdTemporada());
			//ps.setString(7, o.getActivo());
			//ps.setInt(8, o.getIdMercado());
			


			ps.executeUpdate();

			
			
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
