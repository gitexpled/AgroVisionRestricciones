package lib.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import lib.io.ConfigProperties;

public class MantenedorBD {
	static ConnectionDB cdb = null;
	public static String InsertDB (ConnectionDB db, String table, Statement multiQuery)throws Exception{
		PreparedStatement ps = null;
		JSONObject json = new JSONObject();
		ArrayList<String> titulos = new ArrayList<>();
		JSONArray array = new JSONArray();
		try {
			multiQuery.executeBatch();
			ps = db.conn.prepareStatement("SELECT *FROM "+table+" ORDER BY 1 DESC LIMIT 1");
			ResultSet rs = ps.executeQuery("SELECT *FROM "+table+" ORDER BY 1 DESC LIMIT 1");
			ResultSetMetaData md = rs.getMetaData();
			int count = md.getColumnCount();
			for (int i = 1; i <= count; i++) {
				titulos.add(md.getColumnLabel(i));
			}
			if (rs.next()) {
				JSONObject ob = new JSONObject();
				for(int i = 0; i < titulos.size(); i++){
					ob.put(titulos.get(i).toUpperCase(), rs.getObject(titulos.get(i)) == null ? JSONObject.NULL: rs.getObject(titulos.get(i)));
				}
				array.put(ob);
			}
			json.put("data", array);
			json.put("error", 0);
			json.put("mensaje", "Informacion registrada con exito");
		}catch (SQLException ex){
			json.put("error", 1);
			json.put("mensaje", ex.getMessage());
			cdb.conn.rollback();
			cdb.close();
			cdb = null;
		}catch (Exception ex){
			json.put("error", 2);
			json.put("mensaje", ex.getMessage());
			cdb.conn.rollback();
			cdb.close();
			cdb = null;
		} finally {
			try{
				ps.close();
			}catch(Exception ex){
				System.out.println(ex.getMessage());
			}
			
		}
		return json.toString();
	}
	public static String UpdateDB (ConnectionDB db, String table, Statement multiQuery)throws Exception{
		JSONObject json = new JSONObject();
		try {
			multiQuery.executeBatch();
			json.put("error", 0);
			json.put("mensaje", "Informacion actualizada con exito");
		}catch (SQLException ex){
			json.put("error", 1);
			json.put("mensaje", ex.getMessage());
			json.put("mensaje_2", multiQuery.toString());
			cdb.close();
			cdb = null;
		}catch (Exception ex){
			json.put("error", 2);
			json.put("mensaje", ex.getMessage());
			cdb.conn.rollback();
			cdb.close();
			cdb = null;
		} finally {
//			ps_add.close();
//			db.close();
		}
		return json.toString();
	}
	public static String DeleteDB (ConnectionDB db, String table, Statement multiQuery)throws Exception{
		JSONObject json = new JSONObject();
		try {
			multiQuery.executeBatch();
			json.put("error", 0);
			json.put("mensaje", "Informacion actualizada con exito");
		}catch (SQLException ex){
			json.put("error", 1);
			json.put("mensaje", ex.getMessage());
			json.put("mensaje_2", multiQuery.toString());
			cdb.close();
			cdb = null;
		}catch (Exception ex){
			json.put("error", 2);
			json.put("mensaje", ex.getMessage());
			cdb.conn.rollback();
			cdb.close();
			cdb = null;
		} finally {
//			ps_add.close();
//			db.close();
		}
		return json.toString();
	}
	public static String getColumnName (String table)throws Exception{
		PreparedStatement ps = null;
		ConnectionDB db = new ConnectionDB();
		JSONObject json = new JSONObject();
		JSONArray columns = new JSONArray();
		try {
			String getTable = "SELECT *FROM "+table+" LIMIT 1";
			ps = db.conn.prepareStatement(getTable);
			ResultSet gt = ps.executeQuery(getTable);
			ResultSetMetaData mdgt = gt.getMetaData();
			int cmdgt = mdgt.getColumnCount();
			for (int i = 1; i <= cmdgt; i++) {
				JSONObject e = new JSONObject();
				e.put("column", mdgt.getColumnLabel(i));
				columns.put(e);
			}
			json.put("error", 0);
			json.put("column", columns);
		}catch (SQLException ex){
			json.put("error", 1);
			json.put("mensaje", ex.getMessage());
		}catch (Exception ex){
			json.put("error", 2);
			json.put("mensaje", ex.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return json.toString();
	}
	public static String Insert (String row)throws Exception{
		String r = "";
		try{
			row = new String(row.getBytes("ISO-8859-1"), "UTF-8");
			if(cdb == null){
				cdb = new ConnectionDB();
			}
			cdb.conn.setAutoCommit(false);
			boolean com = true; 
			JSONObject json = new JSONObject(row);
			if(json.has("COMMIT")){
				if(json.optBoolean("COMMIT") == false){
					com = false;
				}
			}
			JSONObject object = new JSONObject(row);
			String resGetColumn = getColumnName(json.getString("TABLE"));
			JSONObject jGetColumn = new JSONObject(resGetColumn);
			if(jGetColumn.getInt("error") != 0){
				return resGetColumn;
			}else{
				JSONArray columnName = jGetColumn.getJSONArray("column");
				ArrayList<String> tColumnName = new ArrayList<>();
				for(int i = 0; i < columnName.length(); i++){
					JSONObject e = columnName.getJSONObject(i);
					tColumnName.add(e.getString("column"));
				}
				JSONArray item = null;
				if(object.optJSONArray("VALUES")!=null){
					item = object.getJSONArray("VALUES");
				}else{
					JSONObject aux = object.getJSONObject("VALUES");
					item = new JSONArray();
					item.put(aux);
				}
				json.put("TABLE", object.get("TABLE"));
				Statement multiQuery = null;
				multiQuery = cdb.conn.createStatement();
				for(int i = 0; i < item.length(); i++){
					JSONObject e = new JSONObject(item.getJSONObject(i).toString());
					json.put("VALUES", e);
					String ColName = "";
					String fill = "";
					int c = 0;
					for(int x = 0; x < tColumnName.size(); x++){
						if(e.has(tColumnName.get(x)) ){
							ColName += c == 0? tColumnName.get(x):", "+tColumnName.get(x);
							fill += c == 0? "'"+e.get(tColumnName.get(x))+"'": ", '"+e.get(tColumnName.get(x))+"'";
							c++;
						}
					}
					String sql = "REPLACE INTO "+json.getString("TABLE")+" ("+ColName+") VALUES ("+fill+")";
					System.out.println(sql);
					multiQuery.addBatch(sql);
				}
				r = InsertDB(cdb, json.getString("TABLE"), multiQuery);
				JSONObject re = new JSONObject(r);
				if(re.getInt("error") == 0 && com == true){
					cdb.conn.commit();
					cdb.close();
					cdb = null;
				}
			}
		}catch(Exception ex){
			JSONObject res = new JSONObject();
			res.put("error", 500);
			res.put("mensaje", ex.getMessage());
			cdb.close();
			cdb = null;
			r = res.toString();
		}
		return r;
	}
	public static String Update (String row)throws Exception{
		String r = "";
		try{
			if(cdb == null){
				cdb = new ConnectionDB();
			}
			cdb.conn.setAutoCommit(false);
			boolean com = true; 
			JSONObject json = new JSONObject(row);
			if(json.has("COMMIT")){
				if(json.optBoolean("COMMIT") == false){
					com = false;
				}
			}
			String resGetColumn = getColumnName(json.getString("TABLE"));
			JSONObject jGetColumn = new JSONObject(resGetColumn);
			if(jGetColumn.getInt("error") != 0){
				return resGetColumn;
			}else{
				JSONArray columnName = jGetColumn.getJSONArray("column");
				ArrayList<String> tColumnName = new ArrayList<>();
				for(int i = 0; i < columnName.length(); i++){
					JSONObject e = columnName.getJSONObject(i);
					tColumnName.add(e.getString("column"));
				}
				JSONArray item = null;
				if(json.optJSONArray("UPDATES")!=null){
					item = json.getJSONArray("UPDATES");
				}else{
					JSONObject set = new JSONObject();
					set.put("SET", json.getJSONObject("SET"));
					set.put("WHERE", json.getJSONObject("WHERE"));
					item = new JSONArray();
					item.put(set);
				}
				Statement multiQuery = cdb.conn.createStatement();
				for(int i = 0; i < item.length(); i++){
					JSONObject e = new JSONObject(item.getJSONObject(i).toString());
					JSONObject set = e.getJSONObject("SET");
					JSONObject where = e.getJSONObject("WHERE");
					String updSql = "";
					String sqlWhere = "";
					int cSet = 0;
					int cWhere = 0;
					for(int x = 0; x < tColumnName.size(); x++){
						if(e.getJSONObject("SET").has(tColumnName.get(x)) ){
							Object setValue = null;
							if(!set.get(tColumnName.get(x)).toString().equals("null")){
								setValue = "'"+set.get(tColumnName.get(x))+"'";
							}
							updSql += cSet == 0? tColumnName.get(x) +" = "+setValue+"": ", "+tColumnName.get(x) +" = "+setValue+"";
							cSet++;
						}
						if(e.getJSONObject("WHERE").has(tColumnName.get(x)) ){
							sqlWhere += cWhere == 0? tColumnName.get(x) +" = '"+where.get(tColumnName.get(x))+"'": " AND "+tColumnName.get(x) +" = '"+where.get(tColumnName.get(x))+"'";
							cWhere++;
						}
					}
					String sql = "UPDATE "+json.getString("TABLE")+" SET "+updSql+" WHERE "+sqlWhere;
					System.out.println(sql);
					multiQuery.addBatch(sql);
				}
				r = UpdateDB(cdb, json.getString("TABLE"), multiQuery);
				JSONObject re = new JSONObject(r);
				if(re.getInt("error") == 0 && com == true){
					cdb.conn.commit();
					cdb.close();
					cdb = null;
				}
				return r;
			}
		}catch(Exception ex){
			JSONObject res = new JSONObject();
			res.put("error", 500);
			res.put("mensaje", ex.getMessage());
			cdb.close();
			cdb = null;
			r = res.toString();
		}
		return r;
	}
	public static String Delete (String row)throws Exception{
		String r = "";
		try{
			if(cdb == null){
				cdb = new ConnectionDB();
			}
			cdb.conn.setAutoCommit(false);
			boolean com = true; 
			JSONObject json = new JSONObject(row);
			if(json.has("COMMIT")){
				if(json.optBoolean("COMMIT") == false){
					com = false;
				}
			}
			String resGetColumn = getColumnName(json.getString("TABLE"));
			JSONObject jGetColumn = new JSONObject(resGetColumn);
			if(jGetColumn.getInt("error") != 0){
				return resGetColumn;
			}else{
				JSONArray columnName = jGetColumn.getJSONArray("column");
				ArrayList<String> tColumnName = new ArrayList<>();
				for(int i = 0; i < columnName.length(); i++){
					JSONObject e = columnName.getJSONObject(i);
					tColumnName.add(e.getString("column"));
				}
				JSONArray item = null;
				if(json.optJSONArray("DELETE")!=null){
					item = json.getJSONArray("DELETE");
				}else{
					JSONObject set = new JSONObject();
					set.put("WHERE", json.getJSONObject("WHERE"));
					item = new JSONArray();
					item.put(set);
				}
				Statement multiQuery = cdb.conn.createStatement();
				for(int i = 0; i < item.length(); i++){
					JSONObject e = new JSONObject(item.getJSONObject(i).toString());
					JSONObject where = e.getJSONObject("WHERE");
					String sqlWhere = "";
					int cWhere = 0;
					for(int x = 0; x < tColumnName.size(); x++){
						if(e.getJSONObject("WHERE").has(tColumnName.get(x)) ){
							sqlWhere += cWhere == 0? tColumnName.get(x) +" = '"+where.get(tColumnName.get(x))+"'": " AND "+tColumnName.get(x) +" = '"+where.get(tColumnName.get(x))+"'";
							cWhere++;
						}
					}
					String sql = "DELETE FROM "+json.getString("TABLE")+" WHERE "+sqlWhere;
					System.out.println(sql);
					multiQuery.addBatch(sql);
				}
				r = DeleteDB(cdb, json.getString("TABLE"), multiQuery);
				JSONObject re = new JSONObject(r);
				if(re.getInt("error") == 0 && com == true){
					cdb.conn.commit();
					cdb.close();
					cdb = null;
				}
			}
		}catch(Exception ex){
			JSONObject res = new JSONObject();
			res.put("error", 500);
			res.put("mensaje", ex.getMessage());
			cdb.close();
			cdb = null;
			r = res.toString();
		}
		return r;
		
	}
	public static String Select (String row)throws Exception{
		PreparedStatement ps = null;
		ConnectionDB db = new ConnectionDB();
		JSONObject json = new JSONObject(row);
		System.out.println(json);
		String values = "*";
		String where = " ";
		String sql = "SELECT ";
		ArrayList<String> titulos = new ArrayList<>();
		JSONArray array = new JSONArray();
		try {
			String resGetColumn = getColumnName(json.getString("TABLE"));
			JSONObject jGetColumn = new JSONObject(resGetColumn);
			JSONArray columnName = jGetColumn.getJSONArray("column");
			ArrayList<String> tColumnName = new ArrayList<>();
			for(int i = 0; i < columnName.length(); i++){
				JSONObject e = columnName.getJSONObject(i);
				tColumnName.add(e.getString("column"));
			}
			if(json.has("COLUMN")){
				values = "";
				JSONArray e = new JSONArray(json.get("COLUMN").toString());
				for(int x = 0; x < e.length(); x++){
					values += x == 0 ? e.get(x): ", "+e.get(x) ;
				}
			}
			sql += values+" FROM "+json.getString("TABLE");
			if(json.has("WHERE")){
				where = " WHERE ";
				JSONObject e = json.getJSONObject("WHERE");
				int cWhere = 0;
				for(int x = 0; x < tColumnName.size(); x++){
					if(e.has(tColumnName.get(x))){
						where += cWhere == 0? "": " AND ";
						if(e.optJSONObject(tColumnName.get(x)) != null){
							JSONObject w = e.getJSONObject(tColumnName.get(x));
							if(w.has("BETWEEN")){
								JSONArray aw = w.getJSONArray("BETWEEN");
								where += tColumnName.get(x)+" BETWEEN ";
								for(int i = 0; i < aw.length(); i++){
									where += i == 0 ? "'"+aw.get(i): "' AND '"+aw.get(i)+"'";
								}
							}else if(w.has("IN")){
								JSONArray aw = w.getJSONArray("IN");
								where += tColumnName.get(x)+" IN (";
								for(int i = 0; i < aw.length(); i++){
									where += i == 0 ? "'"+aw.get(i)+"'": " ,'"+aw.get(i)+"'";
								}
								where += ") ";
							}else if(w.has("NOTIN")){
								JSONArray aw = w.getJSONArray("NOTIN");
								where += tColumnName.get(x)+" NOT IN (";
								for(int i = 0; i < aw.length(); i++){
									where += i == 0 ? "'"+aw.get(i)+"'": " ,'"+aw.get(i)+"'";
								}
								where += ") ";
							}
						}else{
							where += tColumnName.get(x) +" = '"+e.get(tColumnName.get(x))+"'";
						}
						cWhere++;
					}
				}
			}
			sql += where;
			ps = db.conn.prepareStatement(sql);
			System.out.println(sql);
			ResultSet rs = ps.executeQuery(sql);
			ResultSetMetaData md = rs.getMetaData();
			int count = md.getColumnCount();
			for (int i = 1; i <= count; i++) {
				titulos.add(md.getColumnLabel(i));
			}
			while (rs.next()) {
				JSONObject ob = new JSONObject();
				for(int i = 0; i < titulos.size(); i++){
					ob.put(titulos.get(i), rs.getObject(titulos.get(i)) == null ? JSONObject.NULL: rs.getObject(titulos.get(i)));
				}
				array.put(ob);
			}
			json.put("data", array);
			json.put("error", 0);
			json.put("mensaje", "Ok");

		}catch (SQLException ex){
			json.put("data", array);
			json.put("error", 2);
			json.put("sql", sql);
			json.put("mensaje", ex.getMessage());
		}catch (Exception ex){
			json.put("data", array);
			json.put("error", 1);
			json.put("mensaje", ex.getMessage());
			json.put("line", ex.getStackTrace()[1].getLineNumber());
		} finally {
//			ps.close();
			db.close();
		}
		return json.toString();
	}
	public static String EXECSP (String row)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		JSONObject object = new JSONObject(row);
		JSONArray array = new JSONArray();
		JSONObject data = new JSONObject();
		ConnectionDB db = new ConnectionDB();
		ArrayList<String> titulos = new ArrayList<>();
		try {
			String fill = "";
			if(object.optJSONArray("FILTERS") != null){
				JSONArray filters = new JSONArray(object.get("FILTERS").toString());
				int c = 0;
				for(int ix = 0; ix < filters.length(); ix++){
		        	JSONObject e = new JSONObject(filters.getJSONObject(ix).toString());
		        	c++;
		        	fill += (c != filters.length())? "'"+e.get("value")+"'," : "'"+e.get("value")+"'";
				}
			}
			sql ="CALL "+object.getString("SP")+"("+fill+")";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			ResultSetMetaData md = rs.getMetaData();
			int count = md.getColumnCount();
			for (int i = 1; i <= count; i++) {
				titulos.add(md.getColumnLabel(i));
			}
			while (rs.next()) {
				JSONObject ob = new JSONObject();
				for(int i = 0; i < titulos.size(); i++){
					ob.put(titulos.get(i), rs.getObject(titulos.get(i)) == null ? JSONObject.NULL: rs.getObject(titulos.get(i)));
				}
				array.put(ob);
			}
			data.put("data", array);
			data.put("message", "Ok");
			data.put("error", 0);
		} catch (SQLException e) {
			data.put("data", array);
			data.put("message", e.getMessage());
			data.put("error", 1);
		} catch (Exception e){
			data.put("data", array);
			data.put("message", e.getMessage());
			data.put("error", 2);
		} finally {
			ps.close();
			db.close();
		}
		return data.toString();
	}
	public static String CallSp (String row)throws Exception{
		PreparedStatement ps = null;
		JSONObject json = new JSONObject(row);
		JSONArray array = new JSONArray();
		JSONObject data = new JSONObject();
		ConnectionDB db = new ConnectionDB();
		ArrayList<String> titulos = new ArrayList<>();
		int opt = 1;
		String sql = "";
		try {
			db.conn.setAutoCommit(false);
			String resGetColumn = getParameterNameSp(json.getString("SP"));
			JSONObject jGetColumn = new JSONObject(resGetColumn);
			JSONArray paramName = jGetColumn.getJSONArray("parameters");
			ArrayList<String> tParamName = new ArrayList<>();
			for(int i = 0; i < paramName.length(); i++){
				JSONObject e = paramName.getJSONObject(i);
				tParamName.add(e.getString("parameter"));
			}
			Statement multiQuery = null;
			multiQuery = db.conn.createStatement();
			String fill = "";
			if(json.has("FILTERS")){
				JSONArray item = null;
				if(json.optJSONArray("FILTERS")!=null){
					item = json.getJSONArray("FILTERS");
					opt = 2;
				}else{
					JSONObject aux = json.getJSONObject("FILTERS");
					item = new JSONArray();
					item.put(aux);
					opt = 1;
				}
				for(int i = 0; i < item.length(); i++){
					JSONObject e = new JSONObject(item.getJSONObject(i).toString());
					int c = 0;
					String fill2 = "";
					for(int x = 0; x < tParamName.size(); x++){
						if(e.has(tParamName.get(x)) ){
							fill2 += c == 0? "'"+e.get(tParamName.get(x))+"'": ", '"+e.get(tParamName.get(x))+"'";
							c++;
						}
					}
					if(i != item.length()){
						sql = "CALL "+json.getString("SP")+"("+fill2+")";
						multiQuery.addBatch(sql);
					}
				}
			}else{
				sql = "CALL "+json.getString("SP")+"("+fill+");";
			}
			if(opt == 1){
				System.out.println(sql);
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);
				try{
					ResultSetMetaData md = rs.getMetaData();
					int count = md.getColumnCount();
					for (int i = 1; i <= count; i++) {
						titulos.add(md.getColumnLabel(i));
					}
					while (rs.next()) {
						JSONObject ob = new JSONObject();
						for(int i = 0; i < titulos.size(); i++){
							ob.put(titulos.get(i), rs.getObject(titulos.get(i)) == null ? JSONObject.NULL: rs.getObject(titulos.get(i)));
						}
						array.put(ob);
					}
				}catch(Exception e){
					
				}
				
			}else{
				multiQuery.executeBatch();
			}
			db.conn.commit();
			data.put("data", array);
			data.put("message", "Ok");
			data.put("error", 0);
		} catch (SQLException e) {
			data.put("data", array);
			data.put("message", e.getMessage());
			data.put("error", 1);
		} catch (Exception e){
			data.put("data", array);
			data.put("message", e.getMessage());
			data.put("message_1", e);
			data.put("message_2", e.getStackTrace());
			data.put("error", 2);
		} finally {
//			ps.close();
			db.close();
		}
		return data.toString();
	}
	public static String getParameterNameSp (String sp)throws Exception{
		PreparedStatement ps = null;
		ConnectionDB db = new ConnectionDB();
		JSONObject json = new JSONObject();
		JSONArray columns = new JSONArray();
		try {
			String getSp = "SELECT *FROM information_schema.parameters WHERE SPECIFIC_NAME = '"+sp+"' AND SPECIFIC_SCHEMA = '"+ConfigProperties.getProperty("SPECIFIC_SCHEMA")+"';";
			System.out.println(getSp);
			ps = db.conn.prepareStatement(getSp);
			ResultSet rs = ps.executeQuery(getSp);
			while(rs.next()){
				JSONObject e = new JSONObject();
				e.put("parameter", rs.getString("PARAMETER_NAME"));
				columns.put(e);
			}
			json.put("error", 0);
			json.put("parameters", columns);
		}catch (SQLException ex){
			json.put("error", 1);
			json.put("mensaje", ex.getMessage());
		}catch (Exception ex){
			json.put("error", 2);
			json.put("mensaje", ex.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		System.out.println(json.toString());
		return json.toString();
	}
}
