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



/**
 * @author aeng Alex Eng - loones1595@gmail.com
 *
 */
public class UrlMonitorServiceImpl
{
   public final Logger logger = Logger.getLogger(UrlMonitorServiceImpl.class.getName());

   private CronTrigger cronTrigger;
   
   private List<UrlMonitor> urlMonitorList = new ArrayList<UrlMonitor>();
   private Map<JobKey, UrlMonitor> urlMonitorMap = new HashMap<JobKey, UrlMonitor>();
   
   public UrlMonitorServiceImpl()
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
