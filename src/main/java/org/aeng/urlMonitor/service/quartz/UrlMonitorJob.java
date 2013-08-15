package org.aeng.urlMonitor.service.quartz;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

import org.aeng.urlMonitor.exception.HttpReadContentException;
import org.aeng.urlMonitor.model.UrlMonitor;
import org.aeng.urlMonitor.model.type.StatusType;
import org.aeng.urlMonitor.util.HttpUtil;
import org.apache.http.client.ClientProtocolException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

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