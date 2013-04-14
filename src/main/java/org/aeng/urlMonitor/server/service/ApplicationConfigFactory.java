/**
 * 
 */
package org.aeng.urlMonitor.server.service;

import java.io.IOException;
import java.util.Properties;

import org.aeng.urlMonitor.server.exception.LoadApplicationConfigException;

/**
 * @author Alex Eng(aeng) - loones1595@gmail.com
 *
 */
public class ApplicationConfigFactory
{
   private static final String CONFIG_FILE_NAME = "urlMonitor.properties";

   private static final Properties APPLICATION_PROPERTIES = new Properties();
   private static final String DB_HOST = "db.host";
   private static final String DB_PORT = "db.port";
   private static final String DB_NAME = "db.name";
   
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

   public static void init() throws LoadApplicationConfigException
   {
      try
      {
         APPLICATION_PROPERTIES.load(ApplicationConfigFactory.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME));
      }
      catch (IOException e)
      {
         throw new LoadApplicationConfigException("Error loading urlMonitor.properties", e);
      }
   }

}
