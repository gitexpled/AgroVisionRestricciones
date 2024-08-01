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

import lib.struc.Mercado;
import lib.struc.filterSql;

public class MercadoDB {

	public static Mercado getMercado(String idMercado) throws Exception {

		Mercado o = null;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM vw_mercados where idMercado='" + idMercado + "'";
			
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				o = new Mercado();
				o.setIdMercado(rs.getInt("idMercado"));
				o.setMercado(rs.getString("mercado"));
				o.setMercado2(rs.getString("mercado2"));
				o.setPf(rs.getString("pf"));
				o.setRegla(rs.getString("regla"));
				o.setCliente(rs.getString("cliente"));
				
				o.setIdMercadoPadre(rs.getInt("idMercadoPadre"));
				o.setPorcentaje(rs.getString("porcentaje"));
				
				o.setProductor(rs.getString("productor"));
				o.setRetricionMolecula(rs.getString("retricionMolecula"));
				o.setMolecula(rs.getInt("molecula"));
				
				o.setRestPorcentaje(rs.getString("restPorcentaje"));
				o.setRestValor(rs.getString("RestValor"));
				
				o.setRestArfD(rs.getString("restArfD"));
				o.setRestSumArfD(rs.getString("restSumArfD"));
				o.setRestValorArfD(rs.getString("restValorArfD"));
				o.setRestPorArfD(rs.getString("restPorArfD"));
				o.setSap(rs.getString("SAP"));
				
				o.setCreado(rs.getDate("creado"));
				o.setModificado(rs.getDate("modificado"));
				o.setIdUser(rs.getInt("idUser"));
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
			throw new Exception("getMercado: " + e.getMessage());
		} finally {
			db.close();
		}

		return o;
	}
	public static ArrayList<Mercado> getMercadoStrArray(String mercado) throws Exception {
		
		ArrayList<Mercado> listado = new ArrayList<Mercado>();
		Mercado o;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM vw_mercados where mercado2='" + mercado + "'";
			
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				
					o = new Mercado();
					o.setIdMercado(rs.getInt("idMercado"));
					o.setMercado(rs.getString("mercado"));
					o.setCreado(rs.getDate("creado"));
					o.setModificado(rs.getDate("modificado"));
					o.setIdUser(rs.getInt("idUser"));
					o.setRegla(rs.getString("regla"));
					listado.add(o);
			}

			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getMercado: " + e.getMessage());
		} finally {
			db.close();
		}

		return listado;
	}
	public static Mercado getMercadoStr(String mercado) throws Exception {

		Mercado o = null;
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM vw_mercados where mercado2='" + mercado + "'";
			
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				o = new Mercado();
				o.setIdMercado(rs.getInt("idMercado"));
				o.setMercado(rs.getString("mercado"));
				o.setRegla(rs.getString("regla"));
				o.setCreado(rs.getDate("creado"));
				o.setModificado(rs.getDate("modificado"));
				o.setIdUser(rs.getInt("idUser"));
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
			throw new Exception("getMercado: " + e.getMessage());
		} finally {
			db.close();
		}

		return o;
	}

	public static void updateMercado(Mercado mercado) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		ConnectionDB db = new ConnectionDB();
		try {
			db.conn.setAutoCommit(false);

			sql = "update  mercado set idUser=?,mercado=?, mercado2=?, pf=?,regla=?,cliente=?,idMercadoPadre=?,porcentaje=?,productor=?,retricionMolecula=?,molecula=?,restPorcentaje=?,restValor=?,restArfD=?,restSumArfD=?,restValorArfD=?,restPorArfD=?,SAP=?,  modificado='"+d+"' where idMercado='" + mercado.getIdMercado()
					+ "'";

			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, mercado.getIdUser());
			ps.setString(2, mercado.getMercado());
			ps.setString(3, mercado.getMercado2());
			ps.setString(4, mercado.getPf());
			ps.setString(5, mercado.getRegla());
			ps.setString(6, mercado.getCliente());
			ps.setInt(7, mercado.getIdMercadoPadre());
			ps.setString(8, mercado.getPorcentaje());
			
			ps.setString(9, mercado.getProductor());
			ps.setString(10, mercado.getRetricionMolecula());
			ps.setInt(11, mercado.getMolecula());
			
			ps.setString(12, mercado.getRestPorcentaje());
			ps.setString(13, mercado.getRestValor());
			
			ps.setString(14, mercado.getRestArfD());
			ps.setString(15, mercado.getRestSumArfD());
			ps.setString(16, mercado.getRestValorArfD());
			ps.setString(17, mercado.getRestPorArfD());
			ps.setString(18, mercado.getSap());
			
			



			ps.executeUpdate();
			db.conn.commit();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("sepMercado: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error2: " + e.getMessage());
			throw new Exception("sepMercado: " + e.getMessage());

		} finally {
			db.close();
		}

	}

	public static int getMercadosAll(ArrayList<filterSql> filter) throws Exception {

		int total = 0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT count(1) FROM vw_mercados ";

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
			throw new Exception("getMercadosAll: " + e.getMessage());
		} finally {
			db.close();
		}

		return total;
	}

	public static ArrayList<Mercado> getMercado(ArrayList<filterSql> filter, String order, int start, int length)
			throws Exception {

		ArrayList<Mercado> mercados = new ArrayList<Mercado>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT * FROM vw_mercados ";

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
				sql += " order by idMercado";
			}

			if (length > 0) {
				sql += " limit " + start + "," + length + " ";
			}
			System.out.println("sql: " + sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Mercado o = new Mercado();
				
				o.setIdMercado(rs.getInt("idMercado"));
				o.setMercado(rs.getString("mercado"));
				o.setMercado2(rs.getString("mercado2"));
				o.setPf(rs.getString("pf"));
				o.setCreado(rs.getDate("creado"));
				o.setModificado(rs.getDate("modificado"));
				o.setIdUser(rs.getInt("idUser"));
				o.setRegla(rs.getString("regla"));
				o.setCliente(rs.getString("cliente"));
				o.setSap(rs.getString("sap"));
				mercados.add(o);
			}
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getMercado: " + e.getMessage());
		} finally {
			db.close();
		}

		return mercados;
	}
	
	public static boolean insertMercado(Mercado mercado) throws ParseException
	{
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		boolean resp = true;
		String sql = "";
		try
		{
			sql = "INSERT INTO mercado(idUser,mercado,mercado2,pf,regla,cliente,idMercadoPadre,porcentaje,creado,modificado) Values ('"+mercado.getIdUser()+"','"+mercado.getMercado()+"','"+mercado.getMercado2()+"','"+mercado.getPf()+"','"+mercado.getRegla()+"','"+mercado.getCliente()+"','"+mercado.getIdMercadoPadre()+"','"+mercado.getPorcentaje()+"','"+d+"','"+d+"')";
			stmt = db.conn.createStatement();
			resp = stmt.execute(sql);
			stmt.close();
			TemporadaDB.setCreateRestriciones();
			
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}finally
		{
			db.close();
		}
		return resp;
	}
	
	public static Mercado getMercadoByName(String mercado)
	{
		ConnectionDB db = new ConnectionDB();
		Mercado o = null;
		Statement stmt = null;
		String sql = "";
		try{
			sql = "Select * from vw_mercados where lower(mercado)=lower('"+mercado+"')";
			System.out.println(sql);
			stmt = db.conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				o = new Mercado();
				o.setIdMercado(rs.getInt("idMercado"));
				o.setMercado(rs.getString("mercado"));
				o.setMercado2(rs.getString("mercado2"));
				o.setPf(rs.getString("pf"));
				o.setRegla(rs.getString("regla"));
				o.setCliente(rs.getString("cliente"));
				
				o.setIdMercadoPadre(rs.getInt("idMercadoPadre"));
				o.setPorcentaje(rs.getString("porcentaje"));
				
				o.setProductor(rs.getString("productor"));
				o.setRetricionMolecula(rs.getString("retricionMolecula"));
				o.setMolecula(rs.getInt("molecula"));
				
				o.setRestPorcentaje(rs.getString("restPorcentaje"));
				o.setRestValor(rs.getString("RestValor"));
				
				o.setRestArfD(rs.getString("restArfD"));
				o.setRestSumArfD(rs.getString("restSumArfD"));
				o.setRestValorArfD(rs.getString("restValorArfD"));
				o.setRestPorArfD(rs.getString("restPorArfD"));
					
				o.setCreado(rs.getDate("creado"));
				o.setModificado(rs.getDate("modificado"));
				o.setIdUser(rs.getInt("idUser"));
			}
			rs.close();
			stmt.close();
		}catch(Exception e)
		{
			System.out.println("getMercadoByName: "+e.getMessage());
		}
		return o;
	}
	
	public static Mercado getMercadoByPf(String mercado)
	{
		ConnectionDB db = new ConnectionDB();
		Mercado o = null;
		Statement stmt = null;
		String sql = "";
		try{
			sql = "Select * from vw_mercados where lower(pf)=lower('"+mercado+"')";
			System.out.println(sql);
			stmt = db.conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				o = new Mercado();
				o.setIdMercado(rs.getInt("idMercado"));
				o.setMercado(rs.getString("mercado"));
				o.setMercado2(rs.getString("mercado2"));
				o.setPf(rs.getString("pf"));
				o.setRegla(rs.getString("regla"));
				o.setCliente(rs.getString("cliente"));
				
				o.setIdMercadoPadre(rs.getInt("idMercadoPadre"));
				o.setPorcentaje(rs.getString("porcentaje"));
				
				o.setProductor(rs.getString("productor"));
				o.setRetricionMolecula(rs.getString("retricionMolecula"));
				o.setMolecula(rs.getInt("molecula"));
				
				o.setCreado(rs.getDate("creado"));
				o.setModificado(rs.getDate("modificado"));
				o.setIdUser(rs.getInt("idUser"));
			}
			rs.close();
			stmt.close();
		}catch(Exception e)
		{
			System.out.println("getMercadoByName: "+e.getMessage());
		}
		return o;
	}
}
