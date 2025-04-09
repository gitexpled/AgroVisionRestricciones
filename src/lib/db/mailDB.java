package lib.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import lib.struc.mail;
import lib.struc.TipoProducto;
import lib.struc.filterSql;

public class mailDB {

	public static mail getMail(String idmail) throws Exception {

		mail o = null;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM mail where idmail='" + idmail + "'";
			
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				o = new mail();
				o.setIdMail(rs.getInt("idmail"));
				o.setMail(rs.getString("mail"));
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
			throw new Exception("getmail: " + e.getMessage());
		} finally {
			db.close();
		}

		return o;
	}

	public static int getmailAll(ArrayList<filterSql> filter) throws Exception {

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
			throw new Exception("getmailAll: " + e.getMessage());
		} finally {
			db.close();
		}

		return total;
	}

	public static ArrayList<mail> getmail(ArrayList<filterSql> filter, String order, int start, int length)
			throws Exception {

		ArrayList<mail> tiposProducto = new ArrayList<mail>();
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
				mail o = new mail();
				
				o.setIdMail(rs.getInt("idmail"));
				o.setMail(rs.getString("mail"));
				tiposProducto.add(o);
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

		return tiposProducto;
	}
	
	public static boolean insertmail(mail mail) throws ParseException
	{
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		boolean resp = true;
		String sql = "";
		try
		{
			sql = "INSERT INTO mail(mail) Values ('"+mail.getMail()+"')";
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
	
	public static void updatemail(mail mail) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			db.conn.setAutoCommit(false);

			sql = "update  mail set mail=? where idmail=" + mail.getIdMail()
					+ "";

			ps = db.conn.prepareStatement(sql);
			ps.setString(1, mail.getMail());


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
	public static void delete(int idMail) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			db.conn.setAutoCommit(false);

			sql = "delete from   mail  where idmail=" + idMail + "";

			ps = db.conn.prepareStatement(sql);
			


			ps.executeUpdate();
			db.conn.commit();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("delete: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error2: " + e.getMessage());
			throw new Exception("delete: " + e.getMessage());

		} finally {
			db.close();
		}

	}
}
