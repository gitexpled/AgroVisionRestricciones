package lib.io;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;


public class ConfigProperties {
    public static String OS = System.getProperty("os.name").toLowerCase();

    public static String getProperty(String value)  {
    	FileInputStream fos = null;
          try {
                 Properties pro = new Properties();
                 String absPath;
                 absPath = (OS.contains("win")) ? getLocalProperty("winDisk") + getLocalProperty("propertiesFile")
                              : getLocalProperty("propertiesFile");
                  fos = new FileInputStream(absPath);
                 pro.load(fos);
                 
                 String respuesta = pro.getProperty(value);
                 fos.close();
                 return respuesta;
                 
            
          } catch (Exception e) {
                 System.out.println(e.getMessage());
          }
//          finally
//          {
//        	  if(fos!=null)
//        		  fos.close();
//        	  
//          }
          
          return "";
          
          
    }
    
    public static Properties getPropertiesByServerFile(String pFile) {
          Properties pro = new Properties();
          try {
                 pro.load(new FileInputStream(pFile));
                 return pro;
          } catch (Exception e) {
                 System.out.println(e.getMessage());
          }
          return pro;
    }


    public static String getLocalProperty(String value) {
          ResourceBundle config = ResourceBundle.getBundle("config", Locale.ENGLISH);
          return config.getString(value);
    }

    public static String getPropertyByFile(String value, String pFile) {
          try {
                 Properties pro = new Properties();
                 String absPath;
                 absPath = (OS.contains("win")) ? (getLocalProperty("winDisk") + getLocalProperty("propertiesPath")+pFile+".properties")
                              : getLocalProperty("propertiesPath")+pFile+".properties";
                 pro.load(new FileInputStream(absPath));
                 return pro.getProperty(value);
          } catch (Exception e) {
                 System.out.println(e.getMessage());
          }
          return "";
    }

}

