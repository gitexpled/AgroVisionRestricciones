package lib.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
