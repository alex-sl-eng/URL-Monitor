/**
 * 
 */
package org.aeng.urlMonitor.service;

import java.util.HashMap;
import java.util.Map;

import org.aeng.urlMonitor.service.quartz.CronTrigger;
import org.aeng.urlMonitor.model.UrlMonitor;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author aeng Alex Eng - loones1595@gmail.com
 * 
 */
public class UrlMonitorService
{
   private final Logger logger = LoggerFactory.getLogger(UrlMonitorService.class);
   
   private CronTrigger cronTrigger;
   private Map<JobKey, UrlMonitor> urlMonitorMap = new HashMap<JobKey, UrlMonitor>();

   public void init()
   {
      try
      {
         logger.info("======URL Monitor========");
         initJobs();
      }
      catch (SchedulerException e)
      {
         logger.error(e.getMessage());
      }
    
   }
   
   private void initJobs() throws SchedulerException
   {
      logger.info("initialising jobs...");

      cronTrigger = new CronTrigger();

//      for (UrlMonitor urlMonitor : dbService.loadAllJobs())
//      {
//         JobKey jobKey = cronTrigger.scheduleMonitor(urlMonitor);
//         urlMonitorMap.put(jobKey, urlMonitor);
//      }
   }
}
