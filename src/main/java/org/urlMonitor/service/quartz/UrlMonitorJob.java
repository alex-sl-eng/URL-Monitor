package org.urlMonitor.service.quartz;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.client.ClientProtocolException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.urlMonitor.exception.HttpReadContentException;
import org.urlMonitor.model.UrlMonitor;
import org.urlMonitor.model.type.StatusType;
import org.urlMonitor.util.HttpUtil;

@Slf4j
public class UrlMonitorJob implements Job
{
   public void execute(JobExecutionContext context) throws JobExecutionException
   {
      UrlMonitor urlMonitor = (UrlMonitor) context.getJobDetail().getJobDataMap().get("value");

      try
      {
         String content = HttpUtil.readContent(urlMonitor);
         Pattern pattern = Pattern.compile(urlMonitor.getContentRegex());
         Matcher matcher = pattern.matcher(content);
         if (matcher.find())
         {
            log.debug("Job {0} passed.", urlMonitor.getName());
            urlMonitor.setStatus(StatusType.Pass);
         }
         else
         {
            log.debug("Job {0} failed.", urlMonitor.getName());
            urlMonitor.setStatus(StatusType.Failed);
         }
      }
      catch (ClientProtocolException e)
      {
         log.debug("Job {0} failed.", urlMonitor.getName());
         urlMonitor.setStatus(StatusType.Failed);
         throw new JobExecutionException(e);
      }
      catch (IOException e)
      {
         log.debug("Job {0} failed.", urlMonitor.getName());
         urlMonitor.setStatus(StatusType.Failed);
         throw new JobExecutionException(e);
      }
      catch (HttpReadContentException e)
      {
         log.debug("Job {0} failed.", urlMonitor.getName());
         urlMonitor.setStatus(StatusType.Failed);
         throw new JobExecutionException(e);
      }

   }
}