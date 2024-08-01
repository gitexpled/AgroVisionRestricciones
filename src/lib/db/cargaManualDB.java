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

public class cargaManualDB {

	public static cargaManual getId(String id)
	{
		ConnectionDB db = new ConnectionDB();
		cargaManual o = null;
		Statement stmt = null;
		String sql = "";
		try
		{
			stmt = db.conn.createStatement();
			sql = "Select * from cargaManual where idCargaManual="+id;
			
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				o = new cargaManual();
				int idDb=rs.getInt("idCargaManual");
				o.setIdCargaManual(rs.getInt("idCargaManual"));
				o.setLaboratorio(rs.getString("laboratorio"));
				o.setCodProductor(rs.getString("codProductor"));
				o.setIdentificador(rs.getString("identificador"));
				o.setCodParcela(rs.getString("codParcela"));
				//o.setCodProducto(rs.getString("codProducto"));
				//o.setLimite(rs.getString("limite"));
				o.setFecha(rs.getString("fecha"));
				o.setIdEspecie(rs.getInt("idEspecie"));
				o.setCodTurno(rs.getString("codTurno"));
				o.setIdVariedad(rs.getString("idVariedad"));
				
				
				
				sql = "Select * from cargaManualDetalle where idCargaManual="+idDb;
				System.out.println("sql2: "+sql);
				ResultSet rs2 = stmt.executeQuery(sql);
				ArrayList<cargaManualDetalle> ds = new ArrayList<cargaManualDetalle>();
				while(rs2.next())
				{
					cargaManualDetalle d = new cargaManualDetalle();
					d.setCodProducto(rs2.getString("codProducto"));
					d.setLimite(rs2.getString("limite"));
					
					
					ds.add(d);
				}
				o.setDetalle(ds);
				rs2.close();
				
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

			sql = "SELECT count(1) FROM cargaManual  c left join variedad v  on (c.idVariedad=v.idVariedad)    WHERE normal='Y'  ";

			
			String andSql="";
			
			
			if (filter.size() > 0) {
				
				Iterator<filterSql> f = filter.iterator();
				andSql += " and ";
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
	
	public static ArrayList<cargaManual> getAll(ArrayList<filterSql> filter, String order, int start, int length)
			throws Exception {

		ArrayList<cargaManual> arr = new ArrayList<cargaManual>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM cargaManual c left join variedad v  on (c.idVariedad=v.idVariedad)  WHERE normal='Y'  ";

			
			String andSql="  ";
			
			
			if (filter.size() > 0) {
			
				Iterator<filterSql> f = filter.iterator();
				andSql += "  and ";
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
			}else
				sql += " order by creado desc";

			if (length > 0) {
				sql += " limit " + start + "," + length + " ";
			}
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				cargaManual o = new cargaManual();
				o.setIdCargaManual(rs.getInt("idCargaManual"));
				o.setFecha(rs.getString("fecha"));
				o.setLaboratorio(rs.getString("laboratorio"));
				o.setIdentificador(rs.getString("identificador"));
				o.setCodProductor(rs.getString("codProductor"));
				o.setCodParcela(rs.getString("codParcela"));
				o.setCodTurno(rs.getString("codTurno"));
				o.setIdVariedad(rs.getString("cod"));
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
	public static void setProcesoRest() throws Exception {
		ConnectionDB db = new ConnectionDB();
		 Statement stmt = null;
		String sql = "";
		try {

			 stmt = db.conn.createStatement();
			 System.out.println("aqui ··············");
			sql = "{call sp_createRestM(1) }";
			
			// ResultSet rs = stmt.executeQuery(sql);
			ResultSet rs = stmt.executeQuery(sql);

			
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("sp_createRestM: " + e.getMessage());
		} finally {
			db.close();
		}

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
			sql = "INSERT INTO cargaManual(`fecha`,`idUsuario`,`creado`,`modificado`,`laboratorio`,`identificador`,codProductor,idEspecie,idTemporada,codParcela,idVariedad,codTurno) "
					+ "Values (?,?,now(),now(),?,?,?,?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1,o.getFecha());
			ps.setInt(2,o.getIdUsuario());
			ps.setString(3,o.getLaboratorio());
			ps.setString(4,o.getIdentificador());
			ps.setString(5,o.getCodProductor());
			ps.setInt(6, o.getIdEspecie());
			ps.setInt(7, o.getIdTemporada());
			
			
			ps.setString(8, o.getCodParcela());
			ps.setString(9, o.getIdVariedad());
			ps.setString(10, o.getCodTurno());
            
               
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			int resultado=0;
	        if (rs.next()){
	        	resultado=rs.getInt(1);
	        }
	        rs.close();
	        
			Iterator<cargaManualDetalle> f = o.getDetalle().iterator();
			System.out.println("resultado: "+resultado);
			if (resultado != 0) {
				while (f.hasNext()) {
				System.out.println("paseee");
				cargaManualDetalle row = f.next();
				
			//	row.setLimite(o.getLimite());
				System.out.println("paseeee");
				sql = "INSERT INTO cargaManualDetalle(`idCargaManual`,`codProducto`,`limite`) "
						+ "Values (?,?,?)";
				ps = db.conn.prepareStatement(sql);
				ps.setInt(1, resultado);
				ps.setString(2,row.getCodProducto());
				ps.setString(3,row.getLimite());

				ps.executeUpdate();
				}
			}
			
			
			setProcesoRest();
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
	
	public static boolean UpdateAnalisis(cargaManual map) throws Exception{
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		String sql = "";
		
		
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "UPDATE cargaManual SET fecha = ?,laboratorio = ?, identificador = ?, codProductor = ?, codParcela = ?, idVariedad = ?,"
					+ "modificado = now() WHERE idCargaManual= ?";
			//sql2 = "UPDATE cargaManualDetalle SET codProducto = ?, limite = ? WHERE idCargaManual= ?";
					
					
			ps = db.conn.prepareStatement(sql);
			
			ps.setString(1, map.getFecha());
			ps.setString(2, map.getLaboratorio());
			ps.setString(3, map.getIdentificador());
			ps.setString(4, map.getCodProductor());
			
			ps.setString(5, map.getCodParcela());
			ps.setString(6, map.getIdVariedad());
			
			ps.setInt(7, map.getIdCargaManual());
			
			
			//ps2 = db.conn.prepareStatement(sql2);
	
			//ps2.setString(1, map.getCodProducto());
			//ps2.setString(2, map.getLimite());
			//ps2.setInt(3, map.getIdCargaManual());
			
			//ps.execute();
			System.out.println("update: carga manual:"+map.getIdCargaManual() + "-->"+map.getCodParcela());
			//ps2.execute();
			return true;
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			ps.close();
			
			db.conn.close();
		}
		return false;
	}
}
