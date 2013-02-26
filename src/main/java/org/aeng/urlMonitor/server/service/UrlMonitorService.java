/**
 * 
 */
package org.aeng.urlMonitor.server.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.aeng.urlMonitor.server.service.quartz.CronTrigger;
import org.aeng.urlMonitor.shared.model.UrlMonitor;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;


/**
 * @author aeng Alex Eng - loones1595@gmail.com
 *
 */
@Singleton
public class UrlMonitorService
{
   private final Logger logger =  LoggerFactory.getLogger(this.getClass());
   private final static String CONFIG_FILE_NAME = "/urlMonitor.properties";
   
   //this is client side logging
//   public final Logger logger = Logger.getLogger(UrlMonitorService.class.getName());

   private CronTrigger cronTrigger;
   
   private List<UrlMonitor> urlMonitorList = new ArrayList<UrlMonitor>();
   private Map<JobKey, UrlMonitor> urlMonitorMap = new HashMap<JobKey, UrlMonitor>();
   
   @Inject
   private DBService dbService;

   public void init()
   {
      logger.info("\n\n======URL Monitor Initialise========");
      initApplicationConfig();
      initDBConnection();
      initJobs();
   }
   
   private void initApplicationConfig()
   {
      try
      {
         Properties properties = new Properties();
         properties.load(this.getClass().getResourceAsStream(CONFIG_FILE_NAME));
         ApplicationConfigFactory.init(properties);
      }
      catch (FileNotFoundException e)
      {
         logger.error("urlMonitor.properties not found:" + e);
      }
      catch (IOException e)
      {
         logger.error("error reading urlMonitor.properties:" + e);
      }
   }

   private void initDBConnection()
   {
      logger.info("\n\n==== Initialise DB Connection ======");
      dbService.loadConnection();
   }

   private void initJobs()
   {
      logger.info("\n\n========= Initialise Jobs ==========");
      try
      {
         cronTrigger = new CronTrigger();

         for (UrlMonitor urlMonitor : urlMonitorList)
         {
            JobKey jobKey = cronTrigger.scheduleMonitor(urlMonitor);
            urlMonitorMap.put(jobKey, urlMonitor);
         }
      }
      catch (SchedulerException e)
      {
         logger.error(e.getMessage());
      }
   }

}
