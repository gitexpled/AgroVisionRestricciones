package lib.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Hashtable;

import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;






public class estadoProductorNewDB {

	public ArrayList<String> getMercado(String mercado) throws Exception {
		ArrayList<String> list = new ArrayList<>();

		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT mercado FROM mercado ";
			if (!mercado.isEmpty())
				sql +="  where mercado='"+mercado+"';";
			
			
//			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {

				list.add(rs.getString(1));
			}
			rs.close();
			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getMercado: " + e.getMessage());
		} finally {
			db.close();
		}

		return list;
	}
	public ArrayList<String> getMercadoSap(String cliente) throws Exception {
		ArrayList<String> list = new ArrayList<>();

		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT mercado FROM mercado ";
			if (!cliente.isEmpty())
				sql +="  where cliente='"+cliente+"';";
			
			
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {

				list.add(rs.getString(1));
			}
			rs.close();
			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getMercado: " + e.getMessage());
		} finally {
			db.close();
		}

		return list;
	}

	public ArrayList<ArrayList<String>> getJerarquia(int idTemporada,int idEspecie,String variedad,String productor,String etapa,String campo,String turno,Boolean group) throws Exception {
		ArrayList<ArrayList<String>> list = new ArrayList<>();

		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT t.temporada temp,j.Productor codSap, j.ProductorNombre, j.Especie, j.Etapa, j.Campo,j.Turno,	j.VariedadDenomina ";
			sql += "FROM  ";
			sql += "  jerarquias j JOIN temporada t inner join  especie e on (j.Especie=e.pf) ";
			sql += "where   ";
			sql += "  e.idEspecie='"+idEspecie+"' and t.idTemporada='"+idTemporada+"'   ";
			
			if (!productor.isEmpty())
				sql += " and j.Productor='"+productor+"' ";
				
				if (!etapa.isEmpty())
					sql += " and j.Etapa='"+etapa+"' ";
					
				if (!campo.isEmpty())
					sql += " and j.Campo='"+campo+"' ";
				
				if (!turno.isEmpty())
					sql += " and j.Turno='"+turno+"' ";

			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ArrayList<String> l = new ArrayList<>();
				l.add(rs.getString(1));
				l.add(rs.getString(2));
				l.add(rs.getString(3));
				l.add(rs.getString(4));
				l.add(rs.getString(5));
				l.add(rs.getString(6));
				if (group)
				{
					l.add(rs.getString(8));	
				}
				else
				{
					l.add(rs.getString(7));
					l.add(rs.getString(8));
				}
				

				list.add(l);
			}
			rs.close();
			stmt.close();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getJerarquia: " + e.getMessage());
		} finally {
			db.close();
		}

		return list;
	}
	public ArrayList<ArrayList<String>> getLmr(int idTemporada,int idEspecie,String idVariedad, String Mercado,String productor,String etapa,String campo,String turno) throws Exception {
		ArrayList<ArrayList<String>> list = new ArrayList<>();

		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();
			
			sql = " SELECT t.temporada temp,j.productor codSap, j.ProductorNombre, j.Especie, j.Etapa, j.Campo,j.Turno,j.VariedadDenomina, m.mercado, IF(r.lmr<l.limite or r.lmr=0 , 0, 1) as bloque ";
			sql += " FROM    jerarquias j ";
			sql += " inner join  especie e on (j.Especie=e.pf)   ";
			sql += " inner join  resultadoDet r on (r.productor=j.Productor and r.etapa=j.Etapa and r.campo=j.Campo and r.turno=j.Turno and r.especie=e.codLab   and r.variedad=j.VariedadDenomina)   ";
			sql += " inner JOIN temporada t  on (t.idEspecie=j.Especie  and r.creado between t.desde and t.hasta)";
			sql += " inner join diccionario d on (d.codRemplazo=r.producto)  ";
			sql += " inner join vw_limite l on (d.codProducto=l.codProducto and e.idEspecie=l.idEspecie)   ";
			sql += " inner join vw_mercados m on (l.idMercado=m.idMercado)     ";
			
			sql += "where  ";
			sql += " t.idTemporada='"+idTemporada+"' and r.vigente=1";
			
			sql += "   and e.idEspecie='"+idEspecie+"' ";
			
			if (!productor.isEmpty())
			sql += " and j.Productor='"+productor+"' ";
			
			if (!etapa.isEmpty())
				sql += " and j.Etapa='"+etapa+"' ";
			
			if (!campo.isEmpty())
				sql += " and j.Campo='"+campo+"' ";
				
			if (!turno.isEmpty())
				sql += " and j.Turno='"+turno+"' ";
			
			if (!idVariedad.isEmpty())
				sql += " and j.VariedadDenomina='"+idVariedad+"' ";
		
			

			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				//if (rs.getString("vigente").equals("1")) {
					ArrayList<String> l = new ArrayList<>();
					l.add(rs.getString(1));
					l.add(rs.getString(2));
					l.add(rs.getString(3));
					l.add(rs.getString(4));
					l.add(rs.getString(5));
					l.add(rs.getString(6));
					l.add(rs.getString(7));
					l.add(rs.getString(8));
					l.add(rs.getString(9));
					l.add(rs.getString(10));

					list.add(l);
				//}
			}
			rs.close();
			stmt.close();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getLmr: " + e.getMessage());
		} finally {
			db.close();
		}

		return list;
	}
	
	public ArrayList<ArrayList<String>> blockMolecula(int idTemporada,int idEspecie,String idVariedad, String Mercado,String productor,String etapa,String campo,String turno,Boolean swHaving) throws Exception {
		ArrayList<ArrayList<String>> list = new ArrayList<>();

		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();
			
			sql = " SELECT t.temporada temp,j.productor codSap, j.ProductorNombre, j.Especie, j.Etapa, j.Campo,j.Turno,j.VariedadDenomina, m.mercado,  count(l.codProducto) as b,m.molecula";
			sql += " FROM    jerarquias j ";
			sql += " inner join  especie e on (j.Especie=e.pf)   ";
			sql += " inner join  resultadoDet r on (r.productor=j.Productor and r.etapa=j.Etapa and r.campo=j.Campo and r.turno=j.Turno and r.especie=e.codLab   and r.variedad=j.VariedadDenomina)   ";
			sql += " inner JOIN temporada t  on (t.idEspecie=j.Especie  and r.creado between t.desde and t.hasta)";
			sql += " inner join diccionario d on (d.codRemplazo=r.producto)  ";
			sql += " inner join vw_limite l on (d.codProducto=l.codProducto and e.idEspecie=l.idEspecie)   ";
			sql += " inner join mercado m on (l.idMercado=m.idMercado)     ";
			
			sql += "where  ";
			sql += " t.idTemporada='"+idTemporada+"' and r.vigente=1 and m.retricionMolecula='Y' ";
			
			sql += "   and e.idEspecie='"+idEspecie+"' ";
			
			if (!productor.isEmpty())
			sql += " and j.Productor='"+productor+"' ";
			
			if (!etapa.isEmpty())
				sql += " and j.Etapa='"+etapa+"' ";
			
			if (!campo.isEmpty())
				sql += " and j.Campo='"+campo+"' ";
				
			if (!turno.isEmpty())
				sql += " and j.Turno='"+turno+"' ";
			
			if (!idVariedad.isEmpty())
				sql += " and j.VariedadDenomina='"+idVariedad+"' ";
		
			if (!Mercado.isEmpty())
				sql += " and m.mercado='"+Mercado+"' ";
			
			sql += "  group by t.temporada, j.productor,j.ProductorNombre,j.Especie,j.Etapa,j.Campo,j.Turno, j.VariedadDenomina,m.mercado ";
			if (swHaving)
				sql += "  having b>molecula";

			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				//if (rs.getString("vigente").equals("1")) {
					ArrayList<String> l = new ArrayList<>();
					l.add(rs.getString(1));
					l.add(rs.getString(2));
					l.add(rs.getString(3));
					l.add(rs.getString(4));
					l.add(rs.getString(5));
					l.add(rs.getString(6));
					l.add(rs.getString(7));
					l.add(rs.getString(8));
					l.add(rs.getString(9));
					l.add(rs.getString(10));

					list.add(l);
				//}
			}
			rs.close();
			stmt.close();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getLmr: " + e.getMessage());
		} finally {
			db.close();
		}

		return list;
	}
	public ArrayList<ArrayList<String>> blockPorcentaje(int idTemporada,int idEspecie,String idVariedad, String Mercado,String productor,String etapa,String campo,String turno,Boolean swHaving) throws Exception {
		ArrayList<ArrayList<String>> list = new ArrayList<>();

		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();
			
			sql = " SELECT t.temporada temp,j.productor codSap, j.ProductorNombre, j.Especie, j.Etapa, j.Campo,j.Turno,j.VariedadDenomina, m.mercado,  1,sum(IF((l.limite = 0),0,((r.lmr * 100) / IFNULL(l.limite2, l.limite)))) as b,m.restValor";
			sql += " FROM    jerarquias j ";
			sql += " inner join  especie e on (j.Especie=e.pf)   ";
			sql += " inner join  resultadoDet r on (r.productor=j.Productor and r.etapa=j.Etapa and r.campo=j.Campo and r.turno=j.Turno and r.especie=e.codLab   and r.variedad=j.VariedadDenomina)   ";
			sql += " inner JOIN temporada t  on (t.idEspecie=j.Especie  and r.creado between t.desde and t.hasta)";
			sql += " inner join diccionario d on (d.codRemplazo=r.producto)  ";
			sql += " inner join vw_limite l on (d.codProducto=l.codProducto and e.idEspecie=l.idEspecie)   ";
			sql += " inner join mercado m on (l.idMercado=m.idMercado)     ";
			
			sql += "where  ";
			sql += " t.idTemporada='"+idTemporada+"' and r.vigente=1 and m.retricionMolecula='Y' ";
			
			sql += "   and e.idEspecie='"+idEspecie+"' ";
			
			if (!productor.isEmpty())
			sql += " and j.Productor='"+productor+"' ";
			
			if (!etapa.isEmpty())
				sql += " and j.Etapa='"+etapa+"' ";
			
			if (!campo.isEmpty())
				sql += " and j.Campo='"+campo+"' ";
				
			if (!turno.isEmpty())
				sql += " and j.Turno='"+turno+"' ";
			
			if (!idVariedad.isEmpty())
				sql += " and j.VariedadDenomina='"+idVariedad+"' ";
		
			if (!Mercado.isEmpty())
				sql += " and m.mercado='"+Mercado+"' ";
			
			sql += "  group by t.temporada, j.productor,j.ProductorNombre,j.Especie,j.Etapa,j.Campo,j.Turno, j.VariedadDenomina,m.mercado ";
			if (swHaving)
				sql += "  having b>restValor";

			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				//if (rs.getString("vigente").equals("1")) {
					ArrayList<String> l = new ArrayList<>();
					l.add(rs.getString(1));
					l.add(rs.getString(2));
					l.add(rs.getString(3));
					l.add(rs.getString(4));
					l.add(rs.getString(5));
					l.add(rs.getString(6));
					l.add(rs.getString(7));
					l.add(rs.getString(8));
					l.add(rs.getString(9));
					l.add(rs.getString(10));
					l.add(rs.getString(11));

					list.add(l);
				//}
			}
			rs.close();
			stmt.close();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getLmr: " + e.getMessage());
		} finally {
			db.close();
		}

		return list;
	}
	public ArrayList<ArrayList<String>> blockPoArfd(int idTemporada,int idEspecie,String idVariedad, String Mercado,String productor,String etapa,String campo,String turno,Boolean swHaving) throws Exception {
		ArrayList<ArrayList<String>> list = new ArrayList<>();

		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();
			
			sql = " SELECT t.temporada temp,j.productor codSap, j.ProductorNombre, j.Especie, j.Etapa, j.Campo,j.Turno,j.VariedadDenomina, m.mercado,  1, (l.limite * e.factor) AS ingesta,m.restValor";
			sql += " FROM    jerarquias j ";
			sql += " inner join  especie e on (j.Especie=e.pf)   ";
			sql += " inner join  resultadoDet r on (r.productor=j.Productor and r.etapa=j.Etapa and r.campo=j.Campo and r.turno=j.Turno and r.especie=e.codLab   and r.variedad=j.VariedadDenomina)   ";
			sql += " inner JOIN temporada t  on (t.idEspecie=j.Especie  and r.creado between t.desde and t.hasta)";
			sql += " inner join diccionario d on (d.codRemplazo=r.producto)  ";
			sql += " inner join vw_limite l on (d.codProducto=l.codProducto and e.idEspecie=l.idEspecie)   ";
			sql += " inner join mercado m on (l.idMercado=m.idMercado)     ";
			
			sql += "where  ";
			sql += " t.idTemporada='"+idTemporada+"' and r.vigente=1 and m.retricionMolecula='Y' ";
			
			sql += "   and e.idEspecie='"+idEspecie+"' ";
			
			if (!productor.isEmpty())
			sql += " and j.Productor='"+productor+"' ";
			
			if (!etapa.isEmpty())
				sql += " and j.Etapa='"+etapa+"' ";
			
			if (!campo.isEmpty())
				sql += " and j.Campo='"+campo+"' ";
				
			if (!turno.isEmpty())
				sql += " and j.Turno='"+turno+"' ";
			
			if (!idVariedad.isEmpty())
				sql += " and j.VariedadDenomina='"+idVariedad+"' ";
		
			if (!Mercado.isEmpty())
				sql += " and m.mercado='"+Mercado+"' ";
			
			sql += "  group by t.temporada, j.productor,j.ProductorNombre,j.Especie,j.Etapa,j.Campo,j.Turno, j.VariedadDenomina,m.mercado ";
			if (swHaving)
				sql += "  having b>restValor";

			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				//if (rs.getString("vigente").equals("1")) {
					ArrayList<String> l = new ArrayList<>();
					l.add(rs.getString(1));
					l.add(rs.getString(2));
					l.add(rs.getString(3));
					l.add(rs.getString(4));
					l.add(rs.getString(5));
					l.add(rs.getString(6));
					l.add(rs.getString(7));
					l.add(rs.getString(8));
					l.add(rs.getString(9));
					l.add(rs.getString(10));
					l.add(rs.getString(11));

					list.add(l);
				//}
			}
			rs.close();
			stmt.close();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getLmr: " + e.getMessage());
		} finally {
			db.close();
		}

		return list;
	}
	
	
	public ArrayList<ArrayList<String>> getBloackParcela(int idTemporada,int idEspecie,String idVariedad, String Mercado,String productor,String etapa,String campo,String turno) throws Exception {
		ArrayList<ArrayList<String>> list = new ArrayList<>();

		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();
			
			
			

			sql = "SELECT tt.temporada AS temp,r.productor codSap, r.ProductorNombre, r.Especie, r.Etapa, r.Campo,r.Turno,r.VariedadDenomina, m.mercado, 1 as b   ";

			sql += "FROM  ";
			sql += "bloqueoParcela bp   ";
			sql += "LEFT JOIN variedad v ON (v.cod = bp.idVariedad OR bp.idVariedad = '-1')  ";
			sql += "LEFT JOIN mercado m ON (m.idMercado = bp.idMercado OR bp.idMercado = -(1))  ";
			//sql += "inner JOIN parcelaVariedad pv ON (pv.codParcela = bp.codParcela AND pv.idVariedad =v.cod )  ";
			sql += "inner join especie e on (e.idEspecie=v.idEspecie )  ";
			sql += "  join temporada tt on (tt.idEspecie=e.pf)";
			
			sql += " inner join  jerarquias r on (r.productor=bp.codProductor and r.etapa=bp.codParcela   and r.VariedadDenomina=v.nombre)   ";
			
			
			
			sql += "where  ";
			sql += " tt.idTemporada='"+idTemporada+"'  AND bp.estado = 1 ";
			
			sql += "   and e.idEspecie='"+idEspecie+"' ";
			
			if (!productor.isEmpty())
			sql += " and r.Productor='"+productor+"' ";
			
			if (!etapa.isEmpty())
				sql += " and r.Etapa='"+etapa+"' ";
			
			if (!campo.isEmpty())
				sql += " and r.Campo='"+campo+"' ";
				
			if (!turno.isEmpty())
				sql += " and r.Turno='"+turno+"' ";
			
			if (!idVariedad.isEmpty())
				sql += " and r.VariedadDenomina='"+idVariedad+"' ";
			
			if (!Mercado.isEmpty())
				sql += " and r.VariedadDenomina='"+idVariedad+"' ";
			
			if (!Mercado.isEmpty())
				sql += " and m.mercado='"+Mercado+"' ";
			
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ArrayList<String> l = new ArrayList<>();
				l.add(rs.getString(1));
				l.add(rs.getString(2));
				l.add(rs.getString(3));
				l.add(rs.getString(4));
				l.add(rs.getString(5));
				l.add(rs.getString(6));
				l.add(rs.getString(7));
				l.add(rs.getString(8));
				l.add(rs.getString(9));
				l.add(rs.getString(10));
				

				list.add(l);
			}
			rs.close();
			stmt.close();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getBloackParcela: " + e.getMessage());
		} finally {
			db.close();
		}

		return list;
	}
	public ArrayList<ArrayList<String>> getBloackMercado(int idTemporada,int idEspecie,String idVariedad, String Mercado,String productor,String etapa,String campo,String turno) throws Exception {
		ArrayList<ArrayList<String>> list = new ArrayList<>();

		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		try {

			stmt = db.conn.createStatement();
			
			
			

			sql = "SELECT tt.temporada AS temp,r.productor codSap, r.ProductorNombre, r.Especie, r.Etapa, r.Campo,r.Turno,r.VariedadDenomina, m.mercado, 1 as b   ";

			sql += "FROM  ";
			sql += "mercadoProductor bp   ";
			sql += "LEFT JOIN variedad v ON (v.cod = bp.idVariedad)  ";
			sql += "LEFT JOIN mercado m ON (m.idMercado = bp.idMercado OR bp.idMercado = -(1))  ";
			//sql += "inner JOIN parcelaVariedad pv ON (pv.codParcela = bp.codParcela AND pv.idVariedad =v.cod )  ";
			sql += "inner join especie e on (e.idEspecie=v.idEspecie )  ";
			sql += "  join temporada tt on (tt.idEspecie=e.pf)";
			
			sql += " inner join  jerarquias r on (r.productor=bp.codProductor and r.etapa=bp.codParcela    and r.campo=bp.codTurno   and r.VariedadDenomina=v.nombre)   ";
			
			
			
			sql += "where  ";
			sql += " tt.idTemporada='"+idTemporada+"'  ";
			
			sql += "   and e.idEspecie='"+idEspecie+"' ";
			
			if (!productor.isEmpty())
			sql += " and r.Productor='"+productor+"' ";
			
			if (!etapa.isEmpty())
				sql += " and r.Etapa='"+etapa+"' ";
			
			if (!campo.isEmpty())
				sql += " and r.Campo='"+campo+"' ";
				
			if (!turno.isEmpty())
				sql += " and r.Turno='"+turno+"' ";
			
			if (!idVariedad.isEmpty())
				sql += " and r.VariedadDenomina='"+idVariedad+"' ";
			
			if (!Mercado.isEmpty())
				sql += " and r.VariedadDenomina='"+idVariedad+"' ";
			
			if (!Mercado.isEmpty())
				sql += " and m.mercado='"+Mercado+"' ";
			
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ArrayList<String> l = new ArrayList<>();
				l.add(rs.getString(1));
				l.add(rs.getString(2));
				l.add(rs.getString(3));
				l.add(rs.getString(4));
				l.add(rs.getString(5));
				l.add(rs.getString(6));
				l.add(rs.getString(7));
				l.add(rs.getString(8));
				l.add(rs.getString(9));
				l.add(rs.getString(10));
				

				list.add(l);
			}
			rs.close();
			stmt.close();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getBloackMercado: " + e.getMessage());
		} finally {
			db.close();
		}

		return list;
	}

	private Hashtable<String, Hashtable<String, Integer>> createMatrix(ArrayList<String> mercado,ArrayList<ArrayList<String>> jerarquea) {
		Hashtable<String, Hashtable<String, Integer>> data = new Hashtable<>();
		try {

			// creamos matrix en blanco
			for (ArrayList<String> arrayList : jerarquea) {
				Hashtable<String, Integer> mercados = new Hashtable<>();
				
				for (String string : mercado) {
					mercados.put(string, -1);
				}
				String separador="";
				String llave="";
				
				for (String key : arrayList) {
					llave+=separador+key;
					separador="$";
				}
				
				data.put(llave, mercados);
			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		return data;
	}
	
	private Hashtable<String, Hashtable<String, Integer>> setBloqueo(Hashtable<String, Hashtable<String, Integer>> data,ArrayList<ArrayList<String>> bloqueo, Boolean group) {
		
		try {
			
			for (ArrayList<String> arr : bloqueo) {
				String llave="";
				if (group)
					llave=arr.get(0)+"$"+arr.get(1)+"$"+arr.get(2)+"$"+arr.get(3)+"$"+arr.get(4)+"$"+arr.get(5)+"$"+arr.get(7);
				else
					llave=arr.get(0)+"$"+arr.get(1)+"$"+arr.get(2)+"$"+arr.get(3)+"$"+arr.get(4)+"$"+arr.get(5)+"$"+arr.get(6)+"$"+arr.get(7);
					
				//System.out.println(llave);
				try {
					//System.out.println("arr.get(8):"+arr.get(8));
					Integer valor=Integer.parseInt(data.get(llave).get(arr.get(8)).toString());
					//System.out.println("valor:"+valor);
					//System.out.println("get(9):"+arr.get(9));
					Integer value=Integer.parseInt(arr.get(9));
					//System.out.println("value:"+value);
					
					if (valor<=0)
					{
						valor=0;
					}
					data.get(llave).put(arr.get(8),valor+value);	
				} catch (Exception e) {
					// TODO: handle exception
					//System.out.println("Error: " + e.getMessage());
				}
					
				
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		return data;
	}
	private Hashtable<String, Hashtable<String, Integer>> setGroup(Hashtable<String, Hashtable<String, Integer>> data) {
		Hashtable<String, Hashtable<String, Integer>>  array = new Hashtable<String, Hashtable<String, Integer>>();
		
		try {
			
			TreeMap<String, Hashtable<String, Integer>> arrMap = new TreeMap<>(data);
			for (Map.Entry<String, Hashtable<String, Integer>> dataMatrix : arrMap.entrySet()) {
				String key = dataMatrix.getKey();
				Hashtable<String, Integer> restriccionMercado = dataMatrix.getValue();
				String[] arrKey=key.split("\\$");
				String llave=arrKey[0]+"$"+arrKey[1]+"$"+arrKey[2]+"$"+arrKey[3]+"$"+arrKey[4]+"$"+arrKey[5]+"$"+arrKey[7];
				
				TreeMap<String, Integer> arrMapMercado = new TreeMap<>(restriccionMercado);
				
				if (array.get(llave)==null)
				{
					Hashtable<String, Integer> mercados = new Hashtable<>();
					for (Map.Entry<String, Integer> row : arrMapMercado.entrySet()) {
						if (row.getValue()<0)
						{
							row.setValue(1);
						}
						mercados.put(row.getKey(), row.getValue());
					}
					array.put(llave, mercados);
				}
				else
				{
					for (Map.Entry<String, Integer> row : arrMapMercado.entrySet()) {
						Integer valor=Integer.parseInt(array.get(llave).get(row.getKey()).toString());
						//System.out.println("valor:"+valor);
						//System.out.println("get(9):"+arr.get(9));
						Integer value=row.getValue();
						//System.out.println("value:"+value);
						
						if (valor<=0)
						{
							valor=0;
						}
						array.get(llave).put(row.getKey(),valor+value);
					}
				}
				
			}

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		return array;
	}
	private ArrayList<String[]> getMatrix(Hashtable<String, Hashtable<String, Integer>> data, ArrayList<String> mercado) {
		ArrayList<String[]> array = new ArrayList<>();
		try {
			TreeMap<String, Hashtable<String, Integer>> sortedMap = new TreeMap<>(data);
			for (Map.Entry<String, Hashtable<String, Integer>> entry2 : sortedMap.entrySet()) {
				String key2 = entry2.getKey();
				Hashtable<String, Integer> subMap2 = entry2.getValue();
				int largo = subMap2.size() + key2.split("\\$").length;
				String[] o = new String[largo];
				
				int i = 0;
				//System.out.println(key2+":::::::::largo: "+key2.split("\\$").length);
				for (String cab : key2.split("\\$")) {
					
					o[i] = cab;
					++i;
				}
				// Tercer nivel
				for (String m : mercado) {
					String valor="NO";
					if (subMap2.get(m).toString().contentEquals("0"))
						valor="SI";
					o[i] = valor;
					++i;
				}

				array.add(o);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			
		}
		return array;
	}

	public ArrayList<String[]> getEstadoProductorA(int idTemporada, int idEspecie, String productor, String etapa, String campo,String turno, String variedad, Boolean titulo, String cliente) throws Exception {
		ArrayList<String[]> data = new ArrayList<>();

		
		try {
			TemporadaDB temp=new TemporadaDB();
			idTemporada=temp.getMaxTemprada(idEspecie);
			ArrayList<String> mercado = getMercado("");
			ArrayList<ArrayList<String>> jerarquea= getJerarquia(idTemporada,idEspecie,variedad,productor,etapa,campo,turno,false);
			Hashtable<String, Hashtable<String, Integer>> matrix = createMatrix(mercado,jerarquea);
			
			System.out.println(idTemporada+","+idEspecie+","+variedad+","+""+","+productor+","+etapa+","+campo+","+turno);
	
			ArrayList<ArrayList<String>> bloqueoLmr= getLmr(idTemporada,idEspecie,variedad,"",productor,etapa,campo,turno);
			matrix=setBloqueo(matrix,bloqueoLmr,false);
			
			ArrayList<ArrayList<String>> bloqueoParcela= getBloackParcela(idTemporada,idEspecie,variedad,"",productor,etapa,campo,turno);
			matrix=setBloqueo(matrix,bloqueoParcela,false);
			
			ArrayList<ArrayList<String>> bloqueoMercado =getBloackMercado(idTemporada,idEspecie,variedad,"",productor,etapa,campo,turno);
			matrix=setBloqueo(matrix,bloqueoMercado,false);
			
			ArrayList<ArrayList<String>> blockMolecula= blockMolecula(idTemporada,idEspecie,variedad,"",productor,etapa,campo,turno,true);
			matrix=setBloqueo(matrix,blockMolecula,false);
			
			ArrayList<ArrayList<String>> blockPorcentaje= blockPorcentaje(idTemporada,idEspecie,variedad,"",productor,etapa,campo,turno,true);
			matrix=setBloqueo(matrix,blockPorcentaje,false);
			
			data = getMatrix(matrix, mercado);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			throw new Exception("getEstadoProductor: " + e.getMessage());
		} finally {
			
		}

		return data;
	}
	public ArrayList<String[]> getEstadoProductorB(int idTemporada, int idEspecie, String productor, String etapa,String campo,  String variedad, Boolean titulo, String cliente) throws Exception {
		ArrayList<String[]> data = new ArrayList<>();

		
		try {
			TemporadaDB temp=new TemporadaDB();
			idTemporada=temp.getMaxTemprada(idEspecie);
			ArrayList<String> mercado = getMercado("");
			ArrayList<ArrayList<String>> jerarquea= getJerarquia(idTemporada,idEspecie,variedad,productor,etapa,campo,"",false);
			Hashtable<String, Hashtable<String, Integer>> matrix = createMatrix(mercado,jerarquea);
			
			System.out.println(idTemporada+","+idEspecie+","+variedad+","+""+","+productor+","+etapa+","+campo+","+"");
	
			ArrayList<ArrayList<String>> bloqueoLmr= getLmr(idTemporada,idEspecie,variedad,"",productor,etapa,campo,"");
			matrix=setBloqueo(matrix,bloqueoLmr,false);
			
			ArrayList<ArrayList<String>> bloqueoParcela= getBloackParcela(idTemporada,idEspecie,variedad,"",productor,etapa,campo,"");
			matrix=setBloqueo(matrix,bloqueoParcela,false);
			
			ArrayList<ArrayList<String>> bloqueoMercado =getBloackMercado(idTemporada,idEspecie,variedad,"",productor,etapa,campo,"");
			matrix=setBloqueo(matrix,bloqueoMercado,false);
			
			ArrayList<ArrayList<String>> blockMolecula= blockMolecula(idTemporada,idEspecie,variedad,"",productor,etapa,campo,"",true);
			matrix=setBloqueo(matrix,blockMolecula,false);
			
			ArrayList<ArrayList<String>> blockPorcentaje= blockPorcentaje(idTemporada,idEspecie,variedad,"",productor,etapa,campo,"",true);
			matrix=setBloqueo(matrix,blockPorcentaje,false);
			
			matrix=setGroup(matrix);
			data = getMatrix(matrix, mercado);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			throw new Exception("getEstadoProductor: " + e.getMessage());
		} finally {
			
		}

		return data;
	}
	
	public  String getEstadoProductor(int idTemporada,int idEspecie,String idVariedad, String Mercado,String productor,String etapa,String campo,String turno) throws Exception {
		
		String Estado="";
		ArrayList<String[]> data = new ArrayList<>();
		try {
			TemporadaDB temp=new TemporadaDB();
			idTemporada=temp.getMaxTemprada(idEspecie);
			ArrayList<String> mercado = getMercado(Mercado);
			ArrayList<ArrayList<String>> jerarquea= getJerarquia(idTemporada,idEspecie,idVariedad,productor,etapa,campo,turno,false);
			Hashtable<String, Hashtable<String, Integer>> matrix = createMatrix(mercado,jerarquea);
			
			System.out.println(idTemporada+","+idEspecie+","+idVariedad+","+""+","+productor+","+etapa+","+campo+","+turno);
	
			ArrayList<ArrayList<String>> bloqueoLmr= getLmr(idTemporada,idEspecie,idVariedad,"",productor,etapa,campo,turno);
			matrix=setBloqueo(matrix,bloqueoLmr,false);
			
			ArrayList<ArrayList<String>> bloqueoParcela= getBloackParcela(idTemporada,idEspecie,idVariedad,"",productor,etapa,campo,turno);
			matrix=setBloqueo(matrix,bloqueoParcela,false);
			
			ArrayList<ArrayList<String>> bloqueoMercado =getBloackMercado(idTemporada,idEspecie,idVariedad,"",productor,etapa,campo,"");
			matrix=setBloqueo(matrix,bloqueoMercado,false);
			
			ArrayList<ArrayList<String>> blockMolecula= blockMolecula(idTemporada,idEspecie,idVariedad,"",productor,etapa,campo,turno,true);
			matrix=setBloqueo(matrix,blockMolecula,false);
			
			ArrayList<ArrayList<String>> blockPorcentaje= blockPorcentaje(idTemporada,idEspecie,idVariedad,"",productor,etapa,campo,turno,true);
			matrix=setBloqueo(matrix,blockPorcentaje,false);
			
			
			if (turno.isEmpty())
				matrix=setGroup(matrix);
			
			data = getMatrix(matrix, mercado);

				
			System.out.println(data);
			
			if (turno.isEmpty())
				Estado=data.get(0)[7];
			else
				Estado=data.get(0)[8];
				
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			throw new Exception("getEstadoProductor: " + e.getMessage());
		} 

		return Estado;
	}
	
	public  String getBlockMolecula(int idTemporada,int idEspecie,String idVariedad, String Mercado,String productor,String etapa,String campo,String turno) throws Exception {
		
		String html="";
		ArrayList<String[]> data = new ArrayList<>();
		try {
			TemporadaDB temp=new TemporadaDB();
			idTemporada=temp.getMaxTemprada(idEspecie);

			
			ArrayList<ArrayList<String>> matrix= blockMolecula(idTemporada,idEspecie,idVariedad,Mercado,productor,etapa,campo,turno,false);
			
			
				html="<table border=1>";
				html+="<tr><td>&nbsp;Etapa&nbsp;</td><td>&nbsp;Campo&nbsp;</td><td>&nbsp;Turno&nbsp;</td><td>&nbsp;variedad&nbsp;</td><td>&nbsp;Moleculas&nbsp;</td></tr>";
				for (ArrayList<String> arr : matrix) {
					String str_etapa=arr.get(4);
					String str_campo=arr.get(5);
					String str_turno=arr.get(6);
					String str_variedad=arr.get(7);
					String str_valor=arr.get(9);
					html+="<tr><td>&nbsp;"+str_etapa+"&nbsp;</td><td>&nbsp;"+str_campo+"&nbsp;</td><td>&nbsp;"+str_turno+"&nbsp;</td><td>&nbsp;"+str_variedad+"&nbsp;</td><td>&nbsp;"+str_valor+"&nbsp;</td></tr>";
				}
					
					
				
				html+="</table>";
			
			
				
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			throw new Exception("getEstadoProductor: " + e.getMessage());
		} 

		return html;
	}
	public  String getBlockPorcentaje(int idTemporada,int idEspecie,String idVariedad, String Mercado,String productor,String etapa,String campo,String turno) throws Exception {
		
		String html="";
		ArrayList<String[]> data = new ArrayList<>();
		try {
			TemporadaDB temp=new TemporadaDB();
			idTemporada=temp.getMaxTemprada(idEspecie);

			
			ArrayList<ArrayList<String>> matrix= blockPorcentaje(idTemporada,idEspecie,idVariedad,Mercado,productor,etapa,campo,turno,false);
			
			
				html="<table border=1>";
				html+="<tr><td>&nbsp;Etapa&nbsp;</td><td>&nbsp;Campo&nbsp;</td><td>&nbsp;Turno&nbsp;</td><td>&nbsp;variedad&nbsp;</td><td>&nbsp;Moleculas&nbsp;</td></tr>";
				for (ArrayList<String> arr : matrix) {
					String str_etapa=arr.get(4);
					String str_campo=arr.get(5);
					String str_turno=arr.get(6);
					String str_variedad=arr.get(7);
					String str_valor=arr.get(10);
					html+="<tr><td>&nbsp;"+str_etapa+"&nbsp;</td><td>&nbsp;"+str_campo+"&nbsp;</td><td>&nbsp;"+str_turno+"&nbsp;</td><td>&nbsp;"+str_variedad+"&nbsp;</td><td>&nbsp;"+str_valor+"&nbsp;</td></tr>";
				}
					
					
				
				html+="</table>";
			
			
				
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			throw new Exception("getEstadoProductor: " + e.getMessage());
		} 

		return html;
	}
	
	public  String getRestriccionesExcel(int idTemporada,int idEspecie, String productor,String etapa,String campo,String turno,String variedad,Boolean titulo) throws Exception {
		
		ArrayList<String[]> data = new ArrayList<>();
		ArrayList<String> titulos2 = new ArrayList<>();
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		try {
			TemporadaDB temp=new TemporadaDB();
			idTemporada=temp.getMaxTemprada(idEspecie);
			ArrayList<String> mercado = getMercado("");
			ArrayList<ArrayList<String>> jerarquea= getJerarquia(idTemporada,idEspecie,variedad,productor,etapa,campo,turno,true);
			Hashtable<String, Hashtable<String, Integer>> matrix = createMatrix(mercado,jerarquea);
			
			System.out.println(idTemporada+","+idEspecie+","+variedad+","+""+","+productor+","+etapa+","+campo);
	
			ArrayList<ArrayList<String>> bloqueoLmr= getLmr(idTemporada,idEspecie,variedad,"",productor,etapa,campo,turno);
			matrix=setBloqueo(matrix,bloqueoLmr,true);
			
			
			
			ArrayList<ArrayList<String>> bloqueoMercado =getBloackMercado(idTemporada,idEspecie,variedad,"",productor,etapa,campo,"");
			matrix=setBloqueo(matrix,bloqueoMercado,true);
			
			ArrayList<ArrayList<String>> bloqueoParcela= getBloackParcela(idTemporada,idEspecie,variedad,"",productor,etapa,campo,turno);
			matrix=setBloqueo(matrix,bloqueoParcela,true);
			
			
			
			ArrayList<ArrayList<String>> blockMolecula= blockMolecula(idTemporada,idEspecie,variedad,"",productor,etapa,campo,turno,true);
			matrix=setBloqueo(matrix,blockMolecula,true);
			
			ArrayList<ArrayList<String>> blockPorcentaje= blockPorcentaje(idTemporada,idEspecie,variedad,"",productor,etapa,campo,turno,true);
			matrix=setBloqueo(matrix,blockPorcentaje,true);
			
			data = getMatrix(matrix, mercado);
			
			
			

			
			String label="";
			JSONArray columns = new JSONArray();
			label="TEMPORADA";
			columns.put(label);
			titulos2.add(label);
			label="SOCIEDAD";
			columns.put(label);
			titulos2.add(label);
			label="ESPECIE";
			columns.put(label);
			titulos2.add(label);
			label="PRODUCTOR";
			columns.put(label);
			titulos2.add(label);
			label="ETAPA";
			columns.put(label);
			label="CAMPO";
			columns.put(label);
//			label="TURNO";
//			columns.put(label);
			titulos2.add(label);
			label="VARIEDAD";
			columns.put(label);
			titulos2.add(label);
			
			for (int i = 0; i < mercado.size(); i++) {
				columns.put(mercado.get(i));
				titulos2.add(mercado.get(i));
			}
			
			for (String[] arr : data) {
				JSONArray ob = new JSONArray();
				for(int i = 0; i < titulos2.size(); i++){
					ob.put(arr[i]);
				}
				array.put(ob);
			}
			
			json.put("columns", columns);
			System.out.println(columns);
			json.put("data", array);
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			throw new Exception("getRestriccionesExcel: " + e.getMessage());
		} finally {
			
		}

		return json.toString();
	}
	public  String getRestricionesParcelaTurnoExcel(int idTemporada,int idEspecie, String productor,String nombre,String cliente,Boolean titulo) throws Exception {
		ArrayList<String[]> data = new ArrayList<>();
		ArrayList<String> titulos2 = new ArrayList<>();
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		try {
			TemporadaDB temp=new TemporadaDB();
			idTemporada=temp.getMaxTemprada(idEspecie);
			
			ArrayList<String> mercado = getMercadoSap(cliente);
			ArrayList<ArrayList<String>> jerarquea= getJerarquia(idTemporada,idEspecie,"",productor,"","","",true);
			Hashtable<String, Hashtable<String, Integer>> matrix = createMatrix(mercado,jerarquea);
			
			System.out.println(idTemporada+","+idEspecie+","+""+","+productor+","+"");
	
			ArrayList<ArrayList<String>> bloqueoLmr= getLmr(idTemporada,idEspecie,"","",productor,"","","");
			matrix=setBloqueo(matrix,bloqueoLmr,true);
			
			ArrayList<ArrayList<String>> bloqueoMercado =getBloackMercado(idTemporada,idEspecie,"","",productor,"","","");
			matrix=setBloqueo(matrix,bloqueoMercado,true);
			
			ArrayList<ArrayList<String>> bloqueoParcela= getBloackParcela(idTemporada,idEspecie,"","",productor,"","","");
			matrix=setBloqueo(matrix,bloqueoParcela,true);
			
			
			
			ArrayList<ArrayList<String>> blockMolecula= blockMolecula(idTemporada,idEspecie,"","",productor,"","","",true);
			matrix=setBloqueo(matrix,blockMolecula,true);
			
			ArrayList<ArrayList<String>> blockPorcentaje= blockPorcentaje(idTemporada,idEspecie,"","",productor,"","","",true);
			matrix=setBloqueo(matrix,blockPorcentaje,true);
			
			data = getMatrix(matrix, mercado);
			
			
			

			
			String label="";
			JSONArray columns = new JSONArray();
			label="TEMPORADA";
			columns.put(label);
			titulos2.add(label);
			label="SOCIEDAD";
			columns.put(label);
			titulos2.add(label);
			label="FECHACOSECHA";
			columns.put(label);
			titulos2.add(label);
			label="VERSION";
			columns.put(label);
			titulos2.add(label);
			label="MANEJO";
			columns.put(label);
			titulos2.add(label);
			label="ESPECIE";
			columns.put(label);
			titulos2.add(label);
			label="PRODUCTOR";
			columns.put(label);
			titulos2.add(label);
			label="ETAPA";
			columns.put(label);
			titulos2.add(label);
			label="CAMPO";
			columns.put(label);
			titulos2.add(label);
//			label="TURNO";
//			columns.put(label);
			titulos2.add(label);
			label="VARIEDAD";
			columns.put(label);
			titulos2.add(label);
			
			titulos2.add(label);
			
			for (int i = 0; i < mercado.size(); i++) {
				columns.put(mercado.get(i));
				titulos2.add(mercado.get(i));
			}
			
			for (String[] arr : data) {
				JSONArray ob = new JSONArray();
				int j=0;
				for(int i = 0; i < titulos2.size()-2; i++){
					
					if (i==2 || i==3) {ob.put("17092024");}
					else if(i==4) {ob.put("");}
					
				
					
					
					else
					{
					ob.put(arr[j]);
					++j;
					}
				}
				array.put(ob);
			}
			
			json.put("columns", columns);
			System.out.println(columns);
			json.put("data", array);
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			throw new Exception("getRestriccionesExcel: " + e.getMessage());
		} finally {
			
		}

		return json.toString();
	}

}
