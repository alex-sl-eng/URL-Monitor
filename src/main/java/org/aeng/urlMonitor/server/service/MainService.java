/**
 * 
 */
package org.aeng.urlMonitor.server.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.aeng.urlMonitor.server.service.quartz.CronTrigger;
import org.aeng.urlMonitor.shared.model.UrlMonitor;
import org.quartz.JobKey;
import org.quartz.SchedulerException;

import com.google.inject.Singleton;



/**
 * @author aeng Alex Eng - loones1595@gmail.com
 *
 */
@Singleton
public class MainService
{
   public final Logger logger = Logger.getLogger(MainService.class.getName());

   private CronTrigger cronTrigger;
   
   private List<UrlMonitor> urlMonitorList;
   private Map<JobKey, UrlMonitor> urlMonitorMap = new HashMap<JobKey, UrlMonitor>();
   
   public MainService()
   {
      try
      {
         cronTrigger = new CronTrigger();
         
         for(UrlMonitor urlMonitor: urlMonitorList)
         {
            JobKey jobKey = cronTrigger.scheduleMonitor(urlMonitor);
            urlMonitorMap.put(jobKey, urlMonitor);
         }
      }
      catch (SchedulerException e)
      {
         logger.log(Level.SEVERE, e.getMessage());
         e.printStackTrace();
      }
   }
   
}
