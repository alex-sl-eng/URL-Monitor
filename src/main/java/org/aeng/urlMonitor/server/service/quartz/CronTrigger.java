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
import org.quartz.SchedulerException;
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
   private final Scheduler scheduler;
   
   public CronTrigger() throws SchedulerException
   {
      scheduler = new StdSchedulerFactory().getScheduler();
      scheduler.start();
   }
   
   public JobKey scheduleMonitor(UrlMonitor urlMonitor) throws SchedulerException
   {
      if(urlMonitor != null)
      {
         JobKey jobKey = new JobKey(urlMonitor.getName());
         JobDetail jobDetail = JobBuilder.newJob(UrlMonitorJob.class)
         .withIdentity(jobKey).build();
         
         jobDetail.getJobDataMap().put("value", urlMonitor);
   
         Trigger trigger = TriggerBuilder
         .newTrigger()
         .withIdentity("Trigger:" + urlMonitor.getName())
         .withSchedule(
            CronScheduleBuilder.cronSchedule(urlMonitor.getCronExpression()))
         .build();
         
         scheduler.getListenerManager().addJobListener(
               new MonitorJobListener(), KeyMatcher.keyEquals(jobKey)
            );
         
         scheduler.scheduleJob(jobDetail, trigger);
         
         return jobKey;
      }
      return null;
   }
   
   public void pauseJob(JobKey jobKey) throws SchedulerException
   {
      scheduler.pauseJob(jobKey);
   }
   
   public void resumeJob(JobKey jobKey) throws SchedulerException
   {
      scheduler.resumeJob(jobKey);
   }
   
   public void pauseAll() throws SchedulerException
   {
      scheduler.pauseAll();
   }
}
