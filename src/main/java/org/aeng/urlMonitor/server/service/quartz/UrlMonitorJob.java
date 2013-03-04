package org.aeng.urlMonitor.server.service.quartz;

import java.io.IOException;

import org.aeng.urlMonitor.server.exception.HttpReadContentException;
import org.aeng.urlMonitor.server.util.HttpUtil;
import org.aeng.urlMonitor.shared.model.UrlMonitor;
import org.aeng.urlMonitor.shared.model.type.StatusType;
import org.apache.http.client.ClientProtocolException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlMonitorJob implements Job
{
   private final static Logger logger = LoggerFactory.getLogger(UrlMonitorJob.class);
   
   
   @Override
   public void execute(JobExecutionContext context) throws JobExecutionException
   {
      UrlMonitor urlMonitor = (UrlMonitor)context.getJobDetail().getJobDataMap().get("value");
      logger.debug("===================running job:" + urlMonitor.getName());
      
      try
      {
         String content = HttpUtil.readContent(urlMonitor);
//         check regex
      }
      catch (ClientProtocolException e)
      {
         throw new JobExecutionException(e);
      }
      catch (IOException e)
      {
         throw new JobExecutionException(e);
      }
      catch (HttpReadContentException e)
      {
         urlMonitor.setStatus(StatusType.Unknown);
      }
   }

}
