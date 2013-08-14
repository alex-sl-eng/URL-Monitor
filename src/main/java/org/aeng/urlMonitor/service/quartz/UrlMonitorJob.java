package org.aeng.urlMonitor.service.quartz;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aeng.urlMonitor.exception.HttpReadContentException;
import org.aeng.urlMonitor.model.UrlMonitor;
import org.aeng.urlMonitor.model.type.StatusType;
import org.aeng.urlMonitor.util.HttpUtil;
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
      UrlMonitor urlMonitor = (UrlMonitor) context.getJobDetail().getJobDataMap().get("value");
      logger.debug("===================running job:" + urlMonitor.getName());

      try
      {
         String content = HttpUtil.readContent(urlMonitor);
         Pattern pattern = Pattern.compile(urlMonitor.getContentRegex());
         Matcher matcher = pattern.matcher(content);
         if(matcher.find())
         {
            urlMonitor.setStatus(StatusType.Pass);
         }
         else
         {
            urlMonitor.setStatus(StatusType.Failed);
         }
      }
      catch (ClientProtocolException e)
      {
         urlMonitor.setStatus(StatusType.Failed);
         throw new JobExecutionException(e);
      }
      catch (IOException e)
      {
         urlMonitor.setStatus(StatusType.Failed);
         throw new JobExecutionException(e);
      }
      catch (HttpReadContentException e)
      {
         urlMonitor.setStatus(StatusType.Failed);
      }
   }

}