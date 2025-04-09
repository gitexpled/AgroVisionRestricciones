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
import java.util.UUID;

import lib.struc.cargaManual;
import lib.struc.cargaManualDetalle;
import lib.struc.especie;
import lib.struc.filterSql;

public class cargaManualDB {

	public static cargaManual getId(String id) {
		ConnectionDB db = new ConnectionDB();
		cargaManual o = null;
		Statement stmt = null;
		String sql = "";
		try {
			stmt = db.conn.createStatement();
			sql = "Select * from resultadoDet where uuid='" + id+"'";

			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				o = new cargaManual();
				
				o.setIdCargaManual(rs.getString("uuid"));
				o.setLaboratorio(rs.getString("userLab"));
				o.setCodProductor(rs.getString("productor"));
				o.setIdentificador(rs.getString("code"));
				o.setCodParcela(rs.getString("etapa"));
				o.setDfa(rs.getString("dfa"));
				
				o.setFecha(rs.getString("creado"));
				
				o.setCodTurno(rs.getString("turno"));
				o.setIdVariedad(rs.getString("variedad"));
				o.setEspecie(rs.getString("especie"));
				o.setCampo(rs.getString("campo"));

				sql = "Select * from resultadoDet where uuid='" + id+"'";
				System.out.println("sql2: " + sql);
				ResultSet rs2 = stmt.executeQuery(sql);
				ArrayList<cargaManualDetalle> ds = new ArrayList<cargaManualDetalle>();
				while (rs2.next()) {
					cargaManualDetalle d = new cargaManualDetalle();
					d.setCodProducto(rs2.getString("producto"));
					d.setLimite(rs2.getString("lmr"));

					ds.add(d);
				}
				o.setDetalle(ds);
				rs2.close();

			}
			rs.close();
			stmt.close();
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		} finally {
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
					+ "modificacion='" + d + "' where idLimites='" + "'";

			ps = db.conn.prepareStatement(sql);
			ps.setString(1, o.getIdentificador());

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

			sql = "SELECT count(1) FROM resultadoDet   WHERE carga='M'  ";

			String andSql = "";

			if (filter.size() > 0) {

				Iterator<filterSql> f = filter.iterator();
				andSql += " and ";
				while (f.hasNext()) {
					filterSql row = f.next();
					if (!row.getValue().equals("")) {
						if (row.getCampo().endsWith("_to")) {
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='"
									+ sqlDate.format(formatter.parse(row.getValue())) + "'";
						} else if (row.getCampo().endsWith("_from")) {
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='"
									+ sqlDate.format(formatter.parse(row.getValue())) + "'";
						} else
							sql += andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
						andSql = " and ";
					}
				}

			}
			sql+=" group by uuid";
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
	public static  void setVigente(String idTemporada,String productor,String etapa,String campo,String turno,String variedad,String especie,String dfa)
	{
		PreparedStatement ps = null;
		String sql = "";
		
		ConnectionDB db = new ConnectionDB();
		try {
			db.conn.setAutoCommit(false);

			sql = "";
			sql += " UPDATE resultadoDet  SET vigente = 0";
			sql += " WHERE uuid IN (SELECT * FROM (";
			sql += " SELECT r.uuid  FROM resultadoDet r";
			sql += " INNER JOIN especie e ON r.Especie = e.codLab";
			sql += " INNER JOIN temporada t ON t.idEspecie = e.pf AND r.creado BETWEEN t.desde AND t.hasta";
			sql += " WHERE t.idTemporada = '"+idTemporada+"' ";
			sql += " AND r.especie = '"+especie+"' ";
			//sql += " AND productor = '"+productor+"' ";
			//sql += " AND etapa = '"+etapa+"' ";
			//sql += " AND campo = '"+campo+"' ";
			//sql += " AND turno = '"+turno+"' ";
			//sql += " AND variedad = '"+variedad+"'";
			sql += " AND dfa = '"+dfa+"'";
			sql += " ) t";
			sql += " );";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.executeUpdate();
			
			
			sql = "";
			sql += "  UPDATE resultadoDet r ";
			sql += "  inner join (";
			sql += "  SELECT  r.uuid,r.code,r.Especie ,r.Etapa,r.Campo,r.Turno,r.variedad  FROM  resultadoDet r";
			sql += " INNER JOIN especie e ON r.Especie = e.codLab";
			sql += " INNER JOIN temporada t ON t.idEspecie = e.pf AND r.creado BETWEEN t.desde AND t.hasta";
			sql += " WHERE t.idTemporada = '"+idTemporada+"' ";
			sql += " AND r.especie = '"+especie+"' ";
			//sql += " AND productor = '"+productor+"' ";
			//sql += " AND etapa = '"+etapa+"' ";
			//sql += " AND campo = '"+campo+"' ";
			//sql += " AND turno = '"+turno+"' ";
			//sql += " AND variedad = '"+variedad+"'";
			sql += " AND dfa = '"+dfa+"'";
			sql += " AND r.creado = (SELECT MAX(r2.creado) FROM resultadoDet r2  inner join diccionario d on (r2.producto=d.codRemplazo) WHERE r2.productor = r.productor ";
			sql += " AND r2.Especie = r.Especie  AND r2.Etapa = r.Etapa  AND r2.Campo = r.Campo  AND r2.Turno = r.Turno  AND r2.variedad = r.variedad AND r2.dfa = r.dfa)";
			sql += " GROUP BY r.uuid,r.code,r.Especie ,r.Etapa,r.Campo,r.Turno,r.variedad ";
			sql += " ) t on (r.uuid=t.uuid and  r.code=t.code and r.Especie=t.Especie  and r.Etapa=t.Etapa and r.Campo=t.Campo and r.Turno=t.Turno and r.variedad=t.variedad)";
			sql += "  SET r.vigente = 1 ;";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.executeUpdate();
			
			
			
			db.conn.commit();
			db.conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			e.printStackTrace();

		} finally {
			db.close();
		}
	}
	public static ArrayList<cargaManual> getAll(ArrayList<filterSql> filter, String order, int start, int length)
			throws Exception {

		ArrayList<cargaManual> arr = new ArrayList<cargaManual>();
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql += "SELECT distinct ";
			sql += "* ";
			sql += "FROM resultadoDet ";
			sql += "WHERE ";
			sql += "carga = 'M' group by uuid ";

			// sql = "SELECT * FROM cargaManual c left join variedad v on
			// (c.idVariedad=v.idVariedad) WHERE normal='Y' ";

			String andSql = "  ";

			if (filter.size() > 0) {

				Iterator<filterSql> f = filter.iterator();
				andSql += "  and ";
				while (f.hasNext()) {
					filterSql row = f.next();

					if (!row.getValue().equals("")) {
						if (row.getCampo().endsWith("_to")) {
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='"
									+ sqlDate.format(formatter.parse(row.getValue())) + "'";
						} else if (row.getCampo().endsWith("_from")) {
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='"
									+ sqlDate.format(formatter.parse(row.getValue())) + "'";
						} else
							sql += andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
						andSql = " and ";
					}
				}

			}
			if (order.contains(":")) {
				String[] ord=order.split(":");
				sql += " order by "+ord[0] +" "+ord[1];
			} else
				sql += " order by creado desc";

			if (length > 0) {
				sql += " limit " + start + "," + length + " ";
			}
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				cargaManual o = new cargaManual();
				o.setIdCargaManual(rs.getString("uuid"));
				o.setFecha(rs.getString("creado"));
				o.setLaboratorio(rs.getString("userLab"));
				o.setIdentificador(rs.getString("code"));
				o.setCodProductor(rs.getString("productor"));
				o.setCodParcela(rs.getString("etapa"));
				o.setCampo(rs.getString("campo"));
				o.setCodTurno(rs.getString("turno"));
				o.setIdVariedad(rs.getString("variedad"));
				o.setEspecie(rs.getString("especie"));
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

	public static boolean insert(cargaManual o) throws ParseException {
		ConnectionDB db = new ConnectionDB();
		PreparedStatement ps = null;
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		boolean resp = true;
		String sql = "";
		try {
			 UUID uuid = UUID.randomUUID();
		     String uuidAsString = uuid.toString();
			Iterator<cargaManualDetalle> f = o.getDetalle().iterator();
			db = new ConnectionDB();
			db.conn.setAutoCommit(false);
			String idTemporada="";
			String productor="";
			String etapa="";
			String campo="";
			String turno="";
			String variedad="";
			String especie="";
			String dfa="N";
			while (f.hasNext()) {
				System.out.println("paseee");
				cargaManualDetalle row = f.next();

				sql = "insert into resultadoDet (userLab,code,creado,procesado,analizado,entregado,productor,productorNombre,fundo,etapa,campo,turno,variedad,especie,producto,lmr,dfa,carga,uuid) ";
				sql += " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'M',?)";

				
				
				 productor=o.getCodProductor();
				 etapa=o.getCodParcela();
				 campo=o.getCampo();
				 turno=o.getCodTurno();
				 variedad=o.getIdVariedad();
				 especie=o.getEspecie();
				
				ps = db.conn.prepareStatement(sql);
				int i = 0;
				ps.setString(++i, o.getLaboratorio());
				ps.setString(++i, o.getIdentificador());
				ps.setString(++i, d);
				ps.setString(++i, o.getFecha());
				ps.setString(++i, o.getFecha());
				ps.setString(++i, o.getFecha());
				ps.setString(++i, o.getCodProductor());
				ps.setString(++i, "productor");
				ps.setString(++i, "fundo");
				ps.setString(++i, o.getCodParcela());
				ps.setString(++i, o.getCampo());
				ps.setString(++i, o.getCodTurno());
				ps.setString(++i, o.getIdVariedad());
				ps.setString(++i, o.getEspecie());
				ps.setString(++i, row.getCodProducto());
				Double lmr=Double.parseDouble(row.getLimite());
				ps.setDouble(++i, lmr);
				
				if (o.getTipo() == 1) {
					ps.setString(++i, "Y");
					dfa="Y";
				}
				else
				{
					ps.setString(++i, "N");
					dfa="N";
				}
				ps.setString(++i, uuidAsString);
				ps.executeUpdate();

			}

			db.conn.commit();
			especie espe= especieDB.getByCodLab(especie);
			TemporadaDB temp=new TemporadaDB();
			idTemporada=temp.getMaxTemprada(espe.getPf())+"";
			ps.close();
			setVigente(idTemporada, productor, etapa, campo, turno, variedad, especie,dfa);

			

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			resp=false;
		} finally {
			db.close();
		}
		return resp;
	}

	public static boolean UpdateAnalisis(cargaManual map) throws Exception {
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		String sql = "";

		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE cargaManual SET fecha = ?,laboratorio = ?, identificador = ?, codProductor = ?, codParcela = ?, idVariedad = ?, campo = ?, "
					+ "modificado = now() WHERE idCargaManual= ?";
			// sql2 = "UPDATE cargaManualDetalle SET codProducto = ?, limite = ? WHERE
			// idCargaManual= ?";

			ps = db.conn.prepareStatement(sql);

			ps.setString(1, map.getFecha());
			ps.setString(2, map.getLaboratorio());
			ps.setString(3, map.getIdentificador());
			ps.setString(4, map.getCodProductor());

			ps.setString(5, map.getCodParcela());
			ps.setString(6, map.getIdVariedad());
			ps.setString(7, map.getCampo());

			ps.setString(8, map.getIdCargaManual());

			// ps2 = db.conn.prepareStatement(sql2);

			// ps2.setString(1, map.getCodProducto());
			// ps2.setString(2, map.getLimite());
			// ps2.setInt(3, map.getIdCargaManual());

			ps.execute();
			System.out.println("update: carga manual:" + map.getIdCargaManual() + "-->" + map.getCodParcela());
			// ps2.execute();
			return true;
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		} finally {
			ps.close();

			db.conn.close();
		}
		return false;
	}
}
