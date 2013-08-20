/**
 * 
 */
package org.urlMonitor.service.quartz;

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
import org.urlMonitor.model.Monitor;

/**
 * @author Alex Eng(aeng)  loones1595@gmail.com
 *
 */
public class CronTrigger
{
   private final Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

   public CronTrigger() throws SchedulerException
   {
      scheduler.start();
   }

   public boolean scheduleMonitor(Monitor monitor) throws SchedulerException
   {
      if (monitor != null)
      {
         JobKey jobKey = new JobKey(monitor.hashCode() + "");
         
         if (!scheduler.checkExists(jobKey))
         {
            JobDetail jobDetail = JobBuilder.newJob(MonitorJob.class).withIdentity(jobKey).build();

            jobDetail.getJobDataMap().put("value", monitor);

            Trigger trigger = TriggerBuilder
                  .newTrigger()
                  .withIdentity("Trigger:" + monitor.getName())
                  .withSchedule(CronScheduleBuilder.cronSchedule(monitor.getCronExpression()))
                  .build();

            scheduler.getListenerManager().addJobListener(new MonitorJobListener(), KeyMatcher.keyEquals(jobKey));
            scheduler.scheduleJob(jobDetail, trigger);

            return true;
         }
      }
      return false;
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