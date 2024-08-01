package lib.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import lib.struc.Productor;
import lib.struc.Temporada;
import lib.struc.TemporadaZona;
import lib.struc.filterSql;
import lib.struc.grafico;

public class homePageDB {

	public static ArrayList<grafico> getProductorMercado(int idEspecie) throws Exception {

		ArrayList<grafico> gs = new ArrayList<grafico>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		Temporada temp=TemporadaDB.getMaxTemprada();
		int idTemp=temp.getIdTemporada();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT m.mercado as nombre, count(IFNULL(bp.b,IFNULL(pc.b,r.bloqueado))) as valor "
			//sql = "SELECT m.mercado as nombre, count(r.bloqueado) as valor "
					+ "FROM (SELECT r.* " + "FROM restriciones r "
					+ " INNER JOIN "
					+ "(SELECT  MAX(idRestriciones) AS maxId,`codProductor`,idMercado,idTemporada,idEspecie "
					+ "FROM restriciones where inicial='N'and carga!='L' and idEspecie="+idEspecie+" and idTemporada="+idTemp+" "
					+ " GROUP BY `codProductor`,idMercado,idTemporada,idEspecie) rr "
					+ " ON r.idEspecie=rr.idEspecie  and r.idTemporada=rr.idTemporada and rr.idTemporada=r.idTemporada "
					+ "and r.idMercado = rr.idMercado  and r.`codProductor`=rr.`codProductor` "
					+ "AND r.idRestriciones = rr.maxId) as r " + "left join productorBloqueo as bp  "
					+ "on (bp.activo='Y' and bp.codProductor=r.codProductor and bp.idEspecie=r.idEspecie "
					+ "and bp.idTemporada=r.idTemporada and  bp.idMercado=r.idMercado) "
					+ " inner join mercado m on (r.idMercado=m.idMercado) "
					+ "inner join productor p on (r.codProductor=p.codProductor) "
					+ "left join vw_productorCertificado pc on (r.codProductor=pc.codProductor) "
					+ "left join bloqueoOp bo on (bo.codProductor=r.codProductor and bo.idEspecie=r.idEspecie and bo.idTemporada=r.idTemporada and  bo.idMercado=r.idMercado)"
					+ "where r.idEspecie="+idEspecie+"  and r.idTemporada="+idTemp+" and IF(bo.b='N',bo.b,IFNULL(bp.b,IFNULL(pc.b,r.bloqueado)))='N' ";
			
			
					
					//+ "where r.idEspecie="+idEspecie+"  and r.idTemporada="+idTemp+" and r.bloqueado='N' "
					sql += "  GROUP BY r.idMercado,r.idEspecie ";
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				grafico o = new grafico();

				o.setNombre(rs.getString("nombre"));
				o.setValor(rs.getInt("valor"));
				gs.add(o);
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

		return gs;
	}
	
	public static ArrayList<grafico> getProductorZona(int idEspecie,int idTemporada) throws Exception {

		ArrayList<grafico> gs = new ArrayList<grafico>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		Temporada temp=TemporadaDB.getMaxTemprada();
		int idTemp=temp.getIdTemporada();
		try {

			stmt = db.conn.createStatement();
/*
			sql = "SELECT i.* from ( SELECT p.zona as nombre, count(IFNULL(bp.b,IFNULL(pc.b,r.bloqueado))) as valor "
					+ "FROM (SELECT r.* " + "FROM restriciones r "
					+ " INNER JOIN "
					+ "(SELECT  MAX(idRestriciones) AS maxId,`codProductor`,idMercado,idTemporada,idEspecie "
					+ "FROM restriciones where carga!='L' and  idEspecie="+idEspecie+" and idTemporada="+idTemp+" "
					+ " GROUP BY `codProductor`,idMercado,idTemporada,idEspecie) rr "
					+ " ON r.idEspecie=rr.idEspecie  and r.idTemporada=rr.idTemporada and rr.idTemporada=r.idTemporada "
					+ "and r.idMercado = rr.idMercado  and r.`codProductor`=rr.`codProductor` "
					+ "AND r.idRestriciones = rr.maxId) as r " + "left join productorBloqueo as bp  "
					+ "on (bp.activo='Y' and bp.codProductor=r.codProductor and bp.idEspecie=r.idEspecie "
					+ "and bp.idTemporada=r.idTemporada and  bp.idMercado=r.idMercado) "
					+ " inner join mercado m on (r.idMercado=m.idMercado) "
					+ "inner join productor p on (r.codProductor=p.codProductor) "
					+ "left join vw_productorCertificado pc on (r.codProductor=pc.codProductor) "
					
					+ "where r.idEspecie="+idEspecie+"  and r.idTemporada="+idTemp+" and IFNULL(bp.b,IFNULL(pc.b,r.bloqueado))='N'  ";
					if (idMercado>0)
						sql += "and r.idMercado='"+idMercado+"'";
			*/
			sql += " GROUP BY p.zona,r.idEspecie )  i"
					+ "  inner join zona z on (z.nombre=i.nombre) order by z.orden asc";
			
			
			sql="SELECT i.* from ( select a.zona as nombre, a.valor cantidad,b.valor muestrados, "
					+ " FORMAT(IFNULL((b.valor*100)/a.valor,0),1, 'de_DE') as valor from "
					+ "("
					+ "select p.zona, sum(1)  as valor "
					+ "from productor as p ";
				if (idEspecie==1)
					sql += " where p.arandano='Y' ";
				else
					sql += " where p.cereza='Y' ";
				sql += "group by zona) as a "
					+ "left join ( "
					+ "select p.zona, sum(1) as valor from  "
					+ "(select max(idRestriciones),idEspecie, codProductor from restriciones where inicial='N' "
					+ "and idEspecie="+idEspecie+" AND idTemporada="+idTemp+" group by codProductor,idEspecie) as r ";
					//+ "inner join productor p on (p.codProductor=r.codProductor) ";
					if (idEspecie==1)
						sql += "inner join productor p on (p.codProductor=r.codProductor and p.arandano='Y') ";
					else
						sql += "inner join productor p on (p.codProductor=r.codProductor  and p.cereza='Y') ";
					sql += "group by p.zona) b on (a.zona=b.zona)) i";
					sql +=" inner join zona z on (z.nombre=i.nombre) order by z.orden asc";
			
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				grafico o = new grafico();

				o.setNombre(rs.getString("nombre"));
				o.setValor(rs.getInt("valor"));
				gs.add(o);
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

		return gs;
	}
	
	
	public static ArrayList<grafico> getTop10Producto(int idEspecie,int idMercado) throws Exception {

		ArrayList<grafico> gs = new ArrayList<grafico>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		Temporada temp=TemporadaDB.getMaxTemprada();
		int idTemp=temp.getIdTemporada();
		try {

			stmt = db.conn.createStatement();

			sql = "select d.codProducto,sum(a.valor) as valor from  "
					+ "(select codProducto,count(1) as valor from "
					+ "(SELECT distinct  r.idEspecie, r.codProductor, cd.codProducto ,'' as pp "
					+ "FROM restriciones r "
					+ "inner join cargaManualDetalle cd on (r.carga='M' and  cd.idCargaManualDetalle=r.idMailExcel  "
					+ "and r.idTemporada="+idTemp+")"
					+ "union  "
					+ "SELECT distinct  r.idEspecie, r.codProductor, d.codProducto ,m.nMuestra "
					+ " FROM restriciones r  "
					+ " inner join mailExcel m on (r.carga!='M'  and m.idMailExcel =r.idMailExcel "
					+ " and r.idTemporada="+idTemp+")  "
					+ " inner join diccionario d on (d.codRemplazo=m.codProducto ) "
					+ " ) p "
					+ "where p.idEspecie="+idEspecie+" "
					+ "group by codProducto order by count(1) desc "
					+ "limit 10) as a "
					+ "inner join   diccionario d on (d.codRemplazo=a.codProducto ) "
					+ " where a.codProducto not in ('MULTI','MULTIE','MULTIN')"
					+ " group by d.codProducto order by sum(a.valor) desc";
			
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				grafico o = new grafico();

				o.setNombre(rs.getString("codProducto"));
				o.setValor(rs.getInt("valor"));
				gs.add(o);
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

		return gs;
	}
	
	public static ArrayList<grafico> getProductor5(int idEspecie,int idMercado) throws Exception {

		ArrayList<grafico> gs = new ArrayList<grafico>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		Temporada temp=TemporadaDB.getMaxTemprada();
		int idTemp=temp.getIdTemporada();
		try {

			stmt = db.conn.createStatement();

			sql = "select codProductor as nombre,count(bloqueado) as valor from "
					+ "(SELECT DISTINCT  r.idEspecie, r.codProductor, cd.codProducto, r.bloqueado  "
					+ "FROM restriciones r "
					+ "inner join cargaManualDetalle cd on (r.carga='M' and r.bloqueado='N' and cd.idCargaManualDetalle=r.idMailExcel  "
					+ "and r.idTemporada="+idTemp+")"
					+ "union  "
					+ "SELECT DISTINCT r.idEspecie, r.codProductor, m.codProducto, r.bloqueado "
					+ "FROM restriciones r "
					+ "inner join mailExcel m on (r.carga='A'  and m.idMailExcel =r.idMailExcel and r.idTemporada="+idTemp+") "
					+ ") p "
					+ "where p.idEspecie="+idEspecie+" "
					+ "group by codProductor "
					+ "HAVING COUNT(bloqueado) > 5;";
			
			sql="select  codProductor as nombre,count(1) as valor,nMuestra "
			+ " from (SELECT DISTINCT  r.idEspecie, r.codProductor, cd.codProducto ,cd.idCargaManual as nMuestra "
			+ " FROM restriciones r  "
			+ " inner join cargaManualDetalle cd on (r.carga='M'  and cd.idCargaManualDetalle=r.idMailExcel  and r.idTemporada="+idTemp+") "
			+ " union   "
			+ " SELECT DISTINCT r.idEspecie, r.codProductor, d.codProducto ,m.nMuestra "
			+ " FROM restriciones r  "
			+ " inner join mailExcel m on (r.carga!='M' and  m.idMailExcel =r.idMailExcel and r.idTemporada="+idTemp+")  "
			+ " inner join   diccionario d on (d.codRemplazo=m.codProducto and d.codProducto not in ('MULTI','MULTIE','MULTIN')) ) p  "
			+ " where p.idEspecie="+idEspecie+"  "
			+ " group by codProductor ,nMuestra "
			+ " HAVING COUNT(1) > 4; ";

			System.out.println(sql);
			
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				grafico o = new grafico();

				o.setNombre(rs.getString("nombre"));
				o.setValor(rs.getInt("valor"));
				o.setTitle(rs.getString("nMuestra"));
				gs.add(o);
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

		return gs;
	}
	
	public static ArrayList<TemporadaZona> getTemporadaZona() throws Exception {
		ArrayList<TemporadaZona> lista=new ArrayList<TemporadaZona>();
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String query="select idtemporada,nombreTemporada from temporada order by idtemporada desc";
		try {
			stmt = db.conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				TemporadaZona o = new TemporadaZona();

				o.setNombre(rs.getString(1));
				o.setValor(rs.getString(0));
				lista.add(o);
			}
			rs.close();
			
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + query);
			throw new Exception("getUsers: " + e.getMessage());
		}
		
		stmt.close();
		db.close();
		
		return lista;
	}

}
