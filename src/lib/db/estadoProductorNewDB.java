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

			sql = "SELECT t.temporada temp,'' codSap, j.Especie, j.Productor, j.Etapa, j.Campo,j.Turno,	j.VariedadDenomina ";
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
			
			sql = " SELECT t.temporada temp,'' codSap, j.Especie, j.Productor, j.Etapa, j.Campo,j.Turno,j.VariedadDenomina, m.mercado, IF(r.lmr<l.limite or r.lmr=0 , 0, 1) as bloque ";
			sql += " FROM    jerarquias j JOIN temporada t  ";
			sql += " inner join  especie e on (j.Especie=e.pf)   ";
			sql += " inner join  resultadoDet r on (r.productor=j.Productor and r.etapa=j.Etapa and r.campo=j.Campo and r.turno=j.Turno and r.especie=e.codLab   and r.variedad=j.VariedadDenomina)   ";
			sql += " inner join diccionario d on (d.codRemplazo=r.producto)  ";
			sql += " inner join vw_limite l on (d.codProducto=l.codProducto and e.idEspecie=l.idEspecie)   ";
			sql += " inner join vw_mercados m on (l.idMercado=m.idMercado)     ";
			
			sql += "where  ";
			sql += " t.idTemporada='"+idTemporada+"'";
			
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

			sql = "SELECT  tt.temporada AS temp,p.codSap,e.pf,p.codProductor,bp.codParcela,pv.codTurno,v.cod,m.mercado, 1 as b   ";

			sql += "FROM  ";
			sql += "bloqueoParcela bp  join temporada tt ";
			sql += "inner join productor p on (bp.codProductor=p.codProductor)  ";
			sql += "LEFT JOIN variedad v ON (v.cod = bp.idVariedad OR bp.idVariedad = '-1')  ";
			sql += "LEFT JOIN vw_mercados m ON (m.idMercado = bp.idMercado OR bp.idMercado = -(1))  ";
			sql += "inner JOIN parcelaVariedad pv ON (pv.codParcela = bp.codParcela AND pv.idVariedad =v.cod )  ";
			sql += "inner join especie e on (e.idEspecie=v.idEspecie ) where bp.estado = 1 ";
			sql += "  ";
			sql += " and e.idEspecie='"+idEspecie+"'  and tt.idTemporada='"+idTemporada+"'  ";
			
			if (!productor.isEmpty())
			sql += " and p.codProductor='"+productor+"' ";
			
			if (!etapa.isEmpty())
				sql += " and bp.codParcela='"+etapa+"' ";
				
			if (!turno.isEmpty())
				sql += " and pv.codTurno='"+turno+"' ";
			
			if (!idVariedad.isEmpty())
				sql += " and v.cod='"+idVariedad+"' ";
				
			
//			System.out.println(sql);
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
					llave=arr.get(0)+"$"+arr.get(1)+"$"+arr.get(2)+"$"+arr.get(3)+"$"+arr.get(4)+"$"+arr.get(6);
				else
					llave=arr.get(0)+"$"+arr.get(1)+"$"+arr.get(2)+"$"+arr.get(3)+"$"+arr.get(4)+"$"+arr.get(5)+"$"+arr.get(6);
					
				//System.out.println(llave);
				try {
					Integer valor=Integer.parseInt(data.get(llave).get(arr.get(7)).toString());
					Integer value=Integer.parseInt(arr.get(8));
					//System.out.println("value"+value);
					if (valor<=0)
					{
						valor=0;
					}
					data.get(llave).put(arr.get(7),valor+value);	
				} catch (Exception e) {
					// TODO: handle exception
				}
					
				
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		return data;
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
			ArrayList<String> mercado = getMercado("");
			ArrayList<ArrayList<String>> jerarquea= getJerarquia(idTemporada,idEspecie,variedad,productor,etapa,campo,turno,false);
			Hashtable<String, Hashtable<String, Integer>> matrix = createMatrix(mercado,jerarquea);
			
			System.out.println(idTemporada+","+idEspecie+","+variedad+","+""+","+productor+","+etapa+","+campo+","+turno);
	
			ArrayList<ArrayList<String>> bloqueoLmr= getLmr(idTemporada,idEspecie,variedad,"",productor,etapa,campo,turno);
			matrix=setBloqueo(matrix,bloqueoLmr,false);
			
			//ArrayList<ArrayList<String>> bloqueoParcela= getBloackParcela(idTemporada,idEspecie,variedad,"",productor,parcela,turno);
			//matrix=setBloqueo(matrix,bloqueoParcela,false);
			
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
			ArrayList<String> mercado = getMercado("");
			ArrayList<ArrayList<String>> jerarquea= getJerarquia(idTemporada,idEspecie,variedad,productor,etapa,campo,"",true);
			Hashtable<String, Hashtable<String, Integer>> matrix = createMatrix(mercado,jerarquea);
			
			System.out.println(idTemporada+","+idEspecie+","+variedad+","+""+","+productor+","+etapa);
	
			ArrayList<ArrayList<String>> bloqueoLmr= getLmr(idTemporada,idEspecie,variedad,"",productor,etapa,campo,"");
			matrix=setBloqueo(matrix,bloqueoLmr,true);
			
			//ArrayList<ArrayList<String>> bloqueoParcela= getBloackParcela(idTemporada,idEspecie,variedad,"",productor,etapa,campo,"");
			//matrix=setBloqueo(matrix,bloqueoParcela,true);
			
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

			ArrayList<String> mercado = getMercado(Mercado);
			ArrayList<ArrayList<String>> jerarquea= getJerarquia(idTemporada,idEspecie,idVariedad,productor,etapa,campo,turno,false);
			Hashtable<String, Hashtable<String, Integer>> matrix = createMatrix(mercado,jerarquea);
			
			System.out.println(idTemporada+","+idEspecie+","+idVariedad+","+""+","+productor+","+etapa+","+campo+","+turno);
	
			ArrayList<ArrayList<String>> bloqueoLmr= getLmr(idTemporada,idEspecie,idVariedad,"",productor,etapa,campo,turno);
			matrix=setBloqueo(matrix,bloqueoLmr,false);
			
			//ArrayList<ArrayList<String>> bloqueoParcela= getBloackParcela(idTemporada,idEspecie,idVariedad,"",productor,parcela,turno);
			//matrix=setBloqueo(matrix,bloqueoParcela,false);
			
			data = getMatrix(matrix, mercado);

				
			System.out.println(data.get(0)[7]);
			Estado=data.get(0)[7];
				
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			throw new Exception("getEstadoProductor: " + e.getMessage());
		} 

		return Estado;
	}
	
	public  String getRestriccionesExcel(int idTemporada,int idEspecie, String productor,String etapa,String campo,String truno,String variedad,Boolean titulo) throws Exception {
		
		ArrayList<String[]> data = new ArrayList<>();
		ArrayList<String> titulos2 = new ArrayList<>();
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		try {
			
			ArrayList<String> mercado = getMercado("");
			ArrayList<ArrayList<String>> jerarquea= getJerarquia(idTemporada,idEspecie,variedad,productor,etapa,campo,truno,true);
			Hashtable<String, Hashtable<String, Integer>> matrix = createMatrix(mercado,jerarquea);
			
			System.out.println(idTemporada+","+idEspecie+","+variedad+","+""+","+productor+","+etapa+","+campo);
	
			ArrayList<ArrayList<String>> bloqueoLmr= getLmr(idTemporada,idEspecie,variedad,"",productor,etapa,campo,truno);
			matrix=setBloqueo(matrix,bloqueoLmr,true);
			
			//ArrayList<ArrayList<String>> bloqueoParcela= getBloackParcela(idTemporada,idEspecie,variedad,"",productor,etapa,campo,"");
			//matrix=setBloqueo(matrix,bloqueoParcela,true);
			
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
			
			ArrayList<String> mercado = getMercadoSap(cliente);
			ArrayList<ArrayList<String>> jerarquea= getJerarquia(idTemporada,idEspecie,"",productor,"","","",true);
			Hashtable<String, Hashtable<String, Integer>> matrix = createMatrix(mercado,jerarquea);
			
			System.out.println(idTemporada+","+idEspecie+","+""+","+productor+","+"");
	
			ArrayList<ArrayList<String>> bloqueoLmr= getLmr(idTemporada,idEspecie,"","",productor,"","","");
			matrix=setBloqueo(matrix,bloqueoLmr,true);
			
			//ArrayList<ArrayList<String>> bloqueoParcela= getBloackParcela(idTemporada,idEspecie,"","",productor,"","","");
			//matrix=setBloqueo(matrix,bloqueoParcela,true);
			
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
