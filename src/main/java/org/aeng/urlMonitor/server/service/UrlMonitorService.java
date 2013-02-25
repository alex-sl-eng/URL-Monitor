/**
 * 
 */
package org.aeng.urlMonitor.server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.aeng.urlMonitor.server.service.quartz.CronTrigger;
import org.aeng.urlMonitor.shared.model.UrlMonitor;
import org.quartz.JobKey;
import org.quartz.SchedulerException;

import com.google.inject.Inject;
import com.google.inject.Singleton;


/**
 * @author aeng Alex Eng - loones1595@gmail.com
 *
 */
@Singleton
public class UrlMonitorService
{
   public final Logger logger = Logger.getLogger(UrlMonitorService.class.getName());

   private CronTrigger cronTrigger;
   
   private List<UrlMonitor> urlMonitorList = new ArrayList<UrlMonitor>();
   private Map<JobKey, UrlMonitor> urlMonitorMap = new HashMap<JobKey, UrlMonitor>();
   
   @Inject
   private DBService dbService;

   public void init()
   {
      logger.info("====================================");
      logger.info("======URL Monitor Initialise========");
      logger.info("====================================");
      initConnection();
      initJobs();
   }

   private void initConnection()
   {
      logger.info("====================================");
      logger.info("==== Initialise DB Connection ======");
      logger.info("====================================");
   }

   private void initJobs()
   {
      logger.info("====================================");
      logger.info("========= Initialise Jobs ==========");
      logger.info("====================================");
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
         logger.log(Level.SEVERE, e.getMessage());
      }
   }

}
