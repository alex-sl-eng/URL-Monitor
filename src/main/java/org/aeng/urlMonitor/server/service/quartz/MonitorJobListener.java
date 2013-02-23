package org.aeng.urlMonitor.server.service.quartz;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class MonitorJobListener implements JobListener 
{
   public static final String LISTENER_NAME = "MonitorJobListener";
   public final Logger logger = Logger.getLogger(MonitorJobListener.class.getName());
 
   @Override
   public String getName() {
      return LISTENER_NAME;
   }
 
   // Run this if job is about to be executed.
   @Override
   public void jobToBeExecuted(JobExecutionContext context) {
 
      String jobName = context.getJobDetail().getKey().toString();
      logger.log(Level.INFO, "Job : " + jobName + " starting...");
 
   }
 
   // No idea when will run this?
   @Override
   public void jobExecutionVetoed(JobExecutionContext context) {
      String jobName = context.getJobDetail().getKey().toString();
      logger.log(Level.INFO, "jobExecutionVetoed: " + jobName);
   }
 
   //Run this after job has been executed
   @Override
   public void jobWasExecuted(JobExecutionContext context,
         JobExecutionException jobException) {
      String jobName = context.getJobDetail().getKey().toString();
      logger.log(Level.INFO, "Job : " + jobName + " is finished...");
 
      if (!jobException.getMessage().equals("")) {
         logger.log(Level.SEVERE, "Exception thrown by: " + jobName + " Exception: " + jobException.getMessage());
      }
 
   }
}
