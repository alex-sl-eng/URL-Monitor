package org.aeng.urlMonitor.server.service.quartz;

import org.aeng.urlMonitor.shared.model.UrlMonitor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class UrlMonitorJob implements Job
{

   @Override
   public void execute(JobExecutionContext context) throws JobExecutionException
   {
      UrlMonitor urlMonitor = (UrlMonitor)context.getJobDetail().getJobDataMap().get("value");
      System.out.println("running job:" + urlMonitor.getName());
   }

}
