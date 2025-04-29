package lib.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import lib.struc.Parcela;
import lib.struc.filterSql;
import lib.struc.jerarquia;

public class jerarquiaDB {

    public String getTurnos(String productor, String etapa, String campo, String variedad) {
        StringBuilder data = new StringBuilder();
        String sql = "SELECT Turno FROM jerarquias WHERE Productor = ? AND Etapa = ? AND Campo = ? AND VariedadDenomina = ?";

        ConnectionDB db = new ConnectionDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = db.conn.prepareStatement(sql);
            stmt.setString(1, productor);
            stmt.setString(2, etapa);
            stmt.setString(3, campo);
            stmt.setString(4, variedad);

            rs = stmt.executeQuery();
            boolean first = true;

            while (rs.next()) {
                if (!first) data.append(", ");
                data.append(rs.getString("Turno"));
                first = false;
            }

        } catch (Exception ex) {
            System.out.println("Error en getTurnos: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (Exception ex) {
                System.out.println("Error cerrando recursos: " + ex.getMessage());
            }
            db.close();
        }

        return data.toString();
    }
    
    public ArrayList<jerarquia> getCambios(String desde, String hasta) throws Exception {
    	
    	ArrayList<jerarquia> cambios = new ArrayList<jerarquia>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();
			
			sql = "SELECT * FROM versionJerarquias where fecha between '"+desde+"' and '"+hasta+"';";

			System.out.println("sql: " + sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				jerarquia j= new jerarquia();
				j.setSociedad(rs.getString("sociedad"));
				j.setEtapa(rs.getString("etapa"));
				j.setCampo(rs.getString("campo"));
				j.setTurno(rs.getString("turno"));
				j.setVariedad(rs.getString("variedad"));
				j.setFundo(rs.getString("fundo"));
				j.setOrigen(rs.getString("origen"));
				j.setAccion(rs.getString("operacion"));
				j.setFecha(rs.getString("fecha"));
				cambios.add(j);
			}
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			//throw new Exception("getUsers: " + e.getMessage());
		} finally {
			db.close();
		}

		return cambios;
    }
    public boolean updateEstadoById(int id, int estado) {
        String sql = "UPDATE jerarquias SET estado = ? WHERE id = ?";
        boolean success = false;

        ConnectionDB db = new ConnectionDB();
        PreparedStatement stmt = null;

        try {
            stmt = db.conn.prepareStatement(sql);
            stmt.setInt(1, estado);
            stmt.setInt(2, id);
            int rows = stmt.executeUpdate();
            success = rows > 0;

        } catch (Exception ex) {
            System.out.println("Error en updateEstadoById: " + ex.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception ex) {
                System.out.println("Error cerrando statement: " + ex.getMessage());
            }
            db.close();
        }

        return success;
    }
    
    
}
