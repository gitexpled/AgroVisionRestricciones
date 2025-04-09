package lib.db;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class resultadoDB {

	
	public  String deleteResultadoDet(String codProducto) throws Exception {
		String respuesta="No se pudo eliminar";
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			db.conn.setAutoCommit(false);
			String[] ids=codProducto.split("_");
			sql = "delete from   resultadoDet  where ";
			
		
			sql+="  Productor  ='"+ids[0]+"'";
			sql+=" and etapa   ='"+ids[1]+"'";
			sql+=" and campo   ='"+ids[2]+"'";
			sql+=" and turno   ='"+ids[3]+"'";
			sql+=" and variedad='"+ids[4]+"'";
			System.out.println(sql);
			
		

			ps = db.conn.prepareStatement(sql);
			
			

			ps.executeUpdate();
			db.conn.commit();
			db.conn.close();
			
			
			respuesta="Registro eliminado";
			System.out.println("ok");
			System.out.println("ok");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("deleteResultadoDet: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error2: " + e.getMessage());
			throw new Exception("deleteResultadoDet: " + e.getMessage());

		} finally {
			db.close();
		}
		return respuesta;
	}
	
	
	public byte[] getPdfId(String id)
	{
		ConnectionDB db = new ConnectionDB();
		byte[]  pdf = null;
		Statement stmt = null;
		String sql = "";
		try
		{
			stmt = db.conn.createStatement();
			sql = "Select pdf from resultadoPdf where code='"+id+"'";
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println(sql);
			if(rs.next())
			{
				System.out.println("buscamos pdf");
				
				pdf=rs.getBytes(1);
				System.out.println(pdf.length);

				
			}
			rs.close();
			stmt.close();
		}catch(Exception ex)
		{
			System.out.println("Error: "+ex.getMessage());
		}finally {
			db.close();
		}
		return pdf;
	}
	
	
}
