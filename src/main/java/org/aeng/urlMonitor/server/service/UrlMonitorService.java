/**
 * 
 */
package org.aeng.urlMonitor.server.service;

import java.util.HashMap;
import java.util.Map;

import org.aeng.urlMonitor.server.exception.DatabaseException;
import org.aeng.urlMonitor.server.exception.LoadApplicationConfigException;
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
   private final Logger logger = LoggerFactory.getLogger(UrlMonitorService.class);

   // this is client side logging
   // public final Logger logger =
   // Logger.getLogger(UrlMonitorService.class.getName());

   @Inject
   private DBService dbService;
   
   private CronTrigger cronTrigger;
   private Map<JobKey, UrlMonitor> urlMonitorMap = new HashMap<JobKey, UrlMonitor>();

   public void init()
   {
      try
      {
         logger.info("======URL Monitor========");
         initApplicationConfig();
         initDBConnection();
         initJobs();
      }
      catch (LoadApplicationConfigException e)
      {
         logger.error(e.getMessage());
      }
      catch (DatabaseException e)
      {
         logger.error(e.getMessage());
      }
    
   }

   private void initApplicationConfig() throws LoadApplicationConfigException
   {
      logger.info("initialising application config... ");
      ApplicationConfigFactory.init();
   }

   private void initDBConnection() throws DatabaseException
   {
      logger.info("initialising database connection...");
//      dbService.loadConnection();
   }
   
   private void initJobs() throws DatabaseException, SchedulerException
   {
      logger.info("initialising jobs...");

      cronTrigger = new CronTrigger();

      for (UrlMonitor urlMonitor : dbService.loadAllJobs())
      {
         JobKey jobKey = cronTrigger.scheduleMonitor(urlMonitor);
         urlMonitorMap.put(jobKey, urlMonitor);
      }

}
