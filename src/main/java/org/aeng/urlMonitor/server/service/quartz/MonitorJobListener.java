package org.aeng.urlMonitor.server.service.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class MonitorJobListener implements JobListener 
{
   public static final String LISTENER_NAME = "MonitorJobListener";
   
   @Override
   public String getName() {
      return LISTENER_NAME;
   }
 
   // Run this if job is about to be executed.
   @Override
   public void jobToBeExecuted(JobExecutionContext context) {
 
      String jobName = context.getJobDetail().getKey().toString();
      System.out.println("Job : " + jobName + " starting...");
 
   }
 
   // No idea when will run this?
   @Override
   public void jobExecutionVetoed(JobExecutionContext context) {
      System.out.println("jobExecutionVetoed");
   }
 
   //Run this after job has been executed
   @Override
   public void jobWasExecuted(JobExecutionContext context,
         JobExecutionException jobException) {
      System.out.println("jobWasExecuted");
 
      String jobName = context.getJobDetail().getKey().toString();
      System.out.println("Job : " + jobName + " is finished...");
 
      if (!jobException.getMessage().equals("")) {
         System.out.println("Exception thrown by: " + jobName
            + " Exception: " + jobException.getMessage());
      }
 
   }
}
