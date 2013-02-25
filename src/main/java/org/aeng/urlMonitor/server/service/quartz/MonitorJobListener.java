package org.aeng.urlMonitor.server.service.quartz;

import java.util.logging.Level;

import org.aeng.urlMonitor.server.service.UrlMonitorService;
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
      logger.info("Job : " + jobName + " starting...");
 
   }
 
   // No idea when will run this?
   @Override
   public void jobExecutionVetoed(JobExecutionContext context) {
      String jobName = context.getJobDetail().getKey().toString();
      logger.info("jobExecutionVetoed: " + jobName);
   }
 
   //Run this after job has been executed
   @Override
   public void jobWasExecuted(JobExecutionContext context,
         JobExecutionException jobException) {
      String jobName = context.getJobDetail().getKey().toString();
      logger.info("Job : " + jobName + " is finished...");
 
      if (!jobException.getMessage().equals("")) {
         logger.info("Exception thrown by: " + jobName + " Exception: " + jobException.getMessage());
      }
 
   }
}
