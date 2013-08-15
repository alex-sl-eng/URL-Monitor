package org.urlMonitor.service.quartz;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

@Slf4j
public class MonitorJobListener implements JobListener
{
   public static final String LISTENER_NAME = "MonitorJobListener";

   public String getName()
   {
      return LISTENER_NAME;
   }

   // Run this if job is about to be executed.
   public void jobToBeExecuted(JobExecutionContext context)
   {
      String jobName = context.getJobDetail().getKey().toString();
      log.debug("Job : " + jobName + " starting.");
   }

   public void jobExecutionVetoed(JobExecutionContext context)
   {
      String jobName = context.getJobDetail().getKey().toString();
      log.debug("jobExecutionVetoed: " + jobName);
   }

   public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException)
   {
      String jobName = context.getJobDetail().getKey().toString();
      log.debug("Job : " + jobName + " is completed.");

      if (jobException != null && !StringUtils.isEmpty(jobException.getMessage()))
      {
         log.error("Exception thrown by: " + jobName + " Exception: " + jobException.getMessage());
      }
   }
}