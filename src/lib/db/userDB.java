package lib.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import lib.struc.filterSql;
import lib.struc.user;

public class userDB {
	//public static ConnectionDB db = null;

	public static Boolean validateUser(String user, String password) throws Exception {

		Boolean sw = false;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {
			stmt = db.conn.createStatement();

			sql = "SELECT * FROM user where user='" + user + "' and password='" + password + "'";

			ResultSet rs = stmt.executeQuery(sql);
			System.out.println(sql);
			if (rs.next()) {
				System.out.println("usuario valido");
				sw = true;
			} else {
				sw = false;
			}
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("validateUser: " + e.getMessage());
		} finally {
			db.close();
		}

		return sw;
	}

	public static user getUser(int id) throws Exception {

		user o = null;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM user where idUser='" + id + "'";
			
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				o = new user();
				o.setId(rs.getInt("idUser"));
				o.setNombre(rs.getString("nombre"));
				o.setApellido(rs.getString("apellido"));
				o.setUser(rs.getString("user"));
				o.setPassword(rs.getString("password"));
				o.setCreacion(rs.getDate("creacion"));
				o.setBaja(rs.getDate("baja"));
				o.setEstado(rs.getInt("estado"));
				o.setMail(rs.getString("mail"));
				o.setIdPerfil(rs.getInt("idPerfil"));
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
	public static ArrayList<Map<String, Object>> getRoles() throws Exception {
	    ConnectionDB db = new ConnectionDB();
	    Statement stmt = null;
	    String sql = "SELECT idPerfil as id, nombre as name FROM systemPerfil";
	    ArrayList<Map<String, Object>> roles = new ArrayList<>();

	    try {
	        stmt = db.conn.createStatement();
	        ResultSet rs = stmt.executeQuery(sql);

	        while (rs.next()) {
	            Map<String, Object> role = new HashMap<>();
	            role.put("id", rs.getInt("id"));
	            role.put("name", rs.getString("name"));
	            roles.add(role);
	        }

	        rs.close();
	        stmt.close();
	        db.conn.close();
	    } catch (SQLException e) {
	        System.out.println("Error al obtener roles: " + e.getMessage());
	        throw new Exception("getRoles: " + e.getMessage());
	    } finally {
	        db.close();
	    }

	    return roles;
	}
	public static user getUserByUser(String user) throws Exception {

		user o = null;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM user where user='" + user + "'";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				o = new user();
				o.setId(rs.getInt("idUser"));
				o.setNombre(rs.getString("nombre"));
				o.setApellido(rs.getString("apellido"));
				o.setUser(rs.getString("user"));
				o.setPassword(rs.getString("password"));
				o.setCreacion(rs.getDate("creacion"));
				o.setBaja(rs.getDate("baja"));
				o.setEstado(rs.getInt("estado"));
				o.setIdPerfil(rs.getInt("idPerfil"));
				o.setMail(rs.getString("mail"));
			} else {
				o = null;
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

	public static void updateUser(user u) throws Exception {
	    PreparedStatement ps = null;
	    StringBuilder sql = new StringBuilder();
	    ConnectionDB db = new ConnectionDB();

	    try {
	        db.conn.setAutoCommit(false);
	        sql.append("UPDATE user SET ");
	        sql.append("nombre = ?, ");
	        sql.append("apellido = ?, ");
	        sql.append("user = ?, ");
	        sql.append("baja = ?, ");
	        sql.append("password = ?, ");
	        sql.append("estado = ?, ");
	        sql.append("mail = ?, ");
	        sql.append("idPerfil = ?");
	        sql.append(", passTemporal = ?");
	        sql.append(", fechaSolicitudModificacion = ?");

	        sql.append(" WHERE idUser = ?");

	        ps = db.conn.prepareStatement(sql.toString());

	        int index = 1;
	        ps.setString(index++, u.getNombre());
	        ps.setString(index++, u.getApellido());
	        ps.setString(index++, u.getUser());
	        if (u.getEstado() == 1) {
	            ps.setTimestamp(index++, new java.sql.Timestamp(new java.util.Date().getTime()));
	        } else {
	            ps.setNull(index++, java.sql.Types.TIMESTAMP);
	        }

	        ps.setString(index++, u.getPassword());
	        int estadoFinal = (u.getFechaSolicitudModificacion() != null) ? 0 : u.getEstado();
	        ps.setInt(index++, estadoFinal);

	        ps.setString(index++, u.getMail());
	        ps.setInt(index++, u.getIdPerfil());

	        if (u.getPassTemporal() != null) {
	            ps.setString(index++, u.getPassTemporal());
	        } else {
	            ps.setNull(index++, java.sql.Types.VARCHAR);
	        }

	        if (u.getFechaSolicitudModificacion() != null) {
	            ps.setTimestamp(index++, new java.sql.Timestamp(u.getFechaSolicitudModificacion().getTime()));
	        } else {
	            ps.setNull(index++, java.sql.Types.TIMESTAMP);
	        }
	        ps.setInt(index++, u.getId());
	        ps.executeUpdate();
	        db.conn.commit();
	        db.conn.close();
	        System.out.println("Usuario actualizado correctamente.");
	    } catch (SQLException e) {
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

	public static int getUsersAll(ArrayList<filterSql> filter) throws Exception {

		int total = 0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT count(1) FROM user ";

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

	public static ArrayList<user> getUsers(ArrayList<filterSql> filter, String order, int start, int length)
			throws Exception {

		ArrayList<user> users = new ArrayList<user>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM user ";

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
				user row = new user();
				
				row.setId(rs.getInt("idUser"));
				row.setNombre(rs.getString("nombre"));
				row.setApellido(rs.getString("apellido"));
				row.setUser(rs.getString("user"));
				row.setPassword(rs.getString("password"));
				row.setCreacion(rs.getDate("creacion"));
				row.setBaja(rs.getDate("baja"));
				row.setEstado(rs.getInt("estado"));
				row.setMail(rs.getString("mail"));
				users.add(row);
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

		return users;
	}
	
	public static boolean insertUser(user us) throws ParseException
	{
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		boolean resp = true;
		String sql = "";
		try
		{
			sql = "INSERT INTO user(nombre,apellido,user,password,creacion,estado,idPerfil,mail) Values ('"+us.getNombre()+"','"+us.getApellido()+"','"+us.getUser()+"','"+us.getPassword()+"','"+d+"',"+us.getEstado()+","+us.getIdPerfil()+",'"+us.getMail()+"')";
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
	
	public static user getUserLogin(String user, String password) throws Exception {

		user us = null;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {
			stmt = db.conn.createStatement();

			sql = "SELECT * FROM user where user='" + user + "' and password='" + password + "' and estado=0";

			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				System.out.println("usuario valido");
				us = new user();
				us.setId(rs.getInt("idUser"));
				us.setNombre(rs.getString("nombre"));
				us.setApellido(rs.getString("apellido"));
				us.setUser(rs.getString("user"));
				us.setPassword(rs.getString("password"));
				us.setCreacion(rs.getDate("creacion"));
				us.setBaja(rs.getDate("baja"));
				us.setEstado(rs.getInt("estado"));
				us.setMail(rs.getString("mail"));
			} else {
				us = null;
			}
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("validateUser: " + e.getMessage());
		} finally {
			db.close();
		}

		return us;
	}
	
	public static ArrayList<user> getAllUsers()
	{
		ConnectionDB db = new ConnectionDB();
		ArrayList<user> users = new ArrayList<user>();
		Statement stmt = null;
		String sql = "";
		try
		{
			sql = "Select * from user";
			stmt = db.conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				user us = new user();
				us.setId(rs.getInt("idUser"));
				us.setNombre(rs.getString("nombre"));
				users.add(us);
			}
			stmt.close();
			rs.close();
		}catch(Exception ex)
		{
			System.out.println("getAllUsers: "+ex.getMessage());
		}
		return users;
	}
	
	public static user getUserByMail(String mail) throws Exception {
		user u = null;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {
			stmt = db.conn.createStatement();
			sql = "SELECT * FROM user WHERE mail = '" + mail + "'";
			ResultSet rs = stmt.executeQuery(sql);
	
			if (rs.next()) {
				u = new user();
				u.setId(rs.getInt("idUser"));
				u.setNombre(rs.getString("nombre"));
				u.setApellido(rs.getString("apellido"));
				u.setUser(rs.getString("user"));
				u.setPassword(rs.getString("password"));
				u.setCreacion(rs.getDate("creacion"));
				u.setBaja(rs.getDate("baja"));
				u.setEstado(rs.getInt("estado"));
				u.setIdPerfil(rs.getInt("idPerfil"));
				u.setMail(rs.getString("mail"));
				u.setPassTemporal(rs.getString("passTemporal"));;
				u.setFechaSolicitudModificacion(rs.getTimestamp("fechaSolicitudModificacion"));
			}
	
			rs.close();
			stmt.close();
			db.conn.close();
			System.out.println(u);
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
			throw new Exception("getUserByMail: " + e.getMessage());
		} finally {
			db.close();
		}
	
		return u;
	}
	
}
