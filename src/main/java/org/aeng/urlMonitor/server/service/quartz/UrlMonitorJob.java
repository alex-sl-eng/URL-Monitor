package org.aeng.urlMonitor.server.service.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class UrlMonitorJob implements Job
{

   @Override
   public void execute(JobExecutionContext arg0) throws JobExecutionException
   {
         System.out.println("running job");
   }

}
