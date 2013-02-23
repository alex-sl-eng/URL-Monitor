/**
 * 
 */
package org.aeng.urlMonitor.server.service.quartz;

import org.aeng.urlMonitor.shared.model.UrlMonitor;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;


/**
 * @author Alex Eng(aeng) - loones1595@gmail.com
 *
 */
public class CronTrigger
{
   public static void initTrigger(UrlMonitor urlMonitor) throws Exception
   {
      if(urlMonitor != null)
      {
         JobKey jobKey = new JobKey(urlMonitor.getName());
         JobDetail job = JobBuilder.newJob(UrlMonitorJob.class)
         .withIdentity(jobKey).build();
   
         Trigger trigger = TriggerBuilder
         .newTrigger()
         .withIdentity("Trigger:" + urlMonitor.getName())
         .withSchedule(
            CronScheduleBuilder.cronSchedule("0/5 * * * * ?")) // every 5 seconds
         .build();
   
         Scheduler scheduler = new StdSchedulerFactory().getScheduler();
   
         scheduler.getListenerManager().addJobListener(
            new MonitorJobListener(), KeyMatcher.keyEquals(jobKey)
         );
   
         scheduler.start();
         scheduler.scheduleJob(job, trigger);
      }
   }
}
