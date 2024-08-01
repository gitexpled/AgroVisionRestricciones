package lib.io;


import java.util.Locale;
import java.util.ResourceBundle;

public class config {
	
		public static String getProperty(String value)
		{
			//Properties prop = new Properties();
			String fileName = "config";
			//InputStream input =null;
			ResourceBundle config = ResourceBundle.getBundle(fileName, Locale.ENGLISH);
			String result=null;
			try {
		    
				result=config.getString(value);
			
			} catch (Exception ex) {
				ex.printStackTrace();
			} 
		
			return result;
		}

}
