package lib.db;

import java.sql.ResultSet;

import java.sql.ResultSetMetaData;

import org.json.JSONArray;
import org.json.JSONObject;

public class ResultSet2Json {
	
	
	public static JSONArray getJson(ResultSet rs)
	{
	
		JSONArray json = new JSONArray();
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			while(rs.next()) {
			  int numColumns = rsmd.getColumnCount();
			  JSONObject obj = new JSONObject();
			  for (int i=1; i<=numColumns; i++) {
			    String column_name = rsmd.getColumnLabel(i);
			    obj.put(column_name, rs.getObject(column_name));
			  }
			  json.put(obj);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return json;
		
		

	}

}
