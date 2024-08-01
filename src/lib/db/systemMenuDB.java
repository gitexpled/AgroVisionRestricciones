package lib.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import lib.struc.Fuente;
import lib.struc.filterSql;
import lib.struc.systemMenu;

public class systemMenuDB {
	
	
	public static ArrayList<systemMenu> getMenu(int idPadre,int idPerfil)
			throws Exception {

		ArrayList<systemMenu> menus = new ArrayList<systemMenu>();
		Statement stmt = null;
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			

			sql = "SELECT m.*,c.numero FROM systemMenu m "
					+ "inner join systemMenuPerfil p on (m.idMenu=p.idMenu and p.idPerfil=? )"
					+ "left join (SELECT idPadre,count(1) as numero FROM systemMenu group by idPadre) c on (m.idMenu=c.idPadre)"
					+ " where m.idPadre=?  order by m.orden ";
			
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, idPerfil);
			ps.setInt(2, idPadre);
			
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				systemMenu o = new systemMenu();
				
				o.setIdMenu(rs.getInt("idMenu"));
				o.setIdPadre(rs.getInt("idPadre"));
				o.setMenu(rs.getString("menu"));
				o.setIcono(rs.getString("icono"));
				o.setUrl(rs.getString("url"));
				o.setCount(rs.getInt("numero"));
				o.setAdm(rs.getString("adm"));
				
				menus.add(o);
			}
			rs.close();
			ps.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getMenu: " + e.getMessage());
		} finally {
			db.close();
		}

		return menus;
	}
	
	public static systemMenu getMenuUrl(String  id)
			throws Exception {

		systemMenu o = new systemMenu();
		Statement stmt = null;
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			

			sql = "SELECT m.* FROM systemMenu m "
					+ " where m.url=?";
			
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, id);
			
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				
				
				o.setIdMenu(rs.getInt("idMenu"));
				o.setIdPadre(rs.getInt("idPadre"));
				o.setMenu(rs.getString("menu"));
				o.setIcono(rs.getString("icono"));
				o.setUrl(rs.getString("url"));
		
				o.setAdm(rs.getString("adm"));
				o.setProceso(rs.getString("proceso"));
				

			}
			rs.close();
			ps.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getMenu: " + e.getMessage());
		} finally {
			db.close();
		}

		return o;
	}

}
