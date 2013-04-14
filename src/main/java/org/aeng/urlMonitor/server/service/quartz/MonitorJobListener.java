package org.aeng.urlMonitor.server.service.quartz;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitorJobListener implements JobListener 
{
   public static final String LISTENER_NAME = "MonitorJobListener";
   
   private final Logger logger =  LoggerFactory.getLogger(MonitorJobListener.class);
 
   @Override
   public String getName() {
      return LISTENER_NAME;
   }
 
   // Run this if job is about to be executed.
   @Override
   public void jobToBeExecuted(JobExecutionContext context) {
 
      String jobName = context.getJobDetail().getKey().toString();
      logger.debug("Job : " + jobName + " starting...");
 
   }
 
   // No idea when will run this?
   @Override
   public void jobExecutionVetoed(JobExecutionContext context) {
      String jobName = context.getJobDetail().getKey().toString();
      logger.debug("jobExecutionVetoed: " + jobName);
   }
 
   //Run this after job has been executed
   @Override
   public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
      String jobName = context.getJobDetail().getKey().toString();
      logger.debug("Job : " + jobName + " is finished...");
      
      if (jobException != null && !StringUtils.isEmpty(jobException.getMessage())) {
         logger.error("Exception thrown by: " + jobName + " Exception: " + jobException.getMessage());
      }
   }
}