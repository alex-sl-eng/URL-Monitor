/**
 * 
 */
package org.aeng.urlMonitor.server.service;

import java.util.Properties;

/**
 * @author Alex Eng(aeng) - loones1595@gmail.com
 *
 */
public class ApplicationConfigFactory
{
   private static Properties APPLICATION_PROPERTIES;
   private static final String DB_HOST = "db.host";
   private static final String DB_PORT = "db.port";
   private static final String DB_NAME = "db.name";
   
   
   public static void init(Properties properties)
   {
      APPLICATION_PROPERTIES = properties;
   }
   
   public static String getDBHost()
   {
      return APPLICATION_PROPERTIES.getProperty(DB_HOST);
   }
   
   public static String getDBPort()
   {
      return APPLICATION_PROPERTIES.getProperty(DB_PORT);
   }
   
   public static String getDBName()
   {
      return APPLICATION_PROPERTIES.getProperty(DB_NAME);
   }

}
