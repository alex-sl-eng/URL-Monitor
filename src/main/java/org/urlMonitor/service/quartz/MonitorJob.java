package org.urlMonitor.service.quartz;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.client.ClientProtocolException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.urlMonitor.component.ContentReader;
import org.urlMonitor.component.ContentReaderManager;
import org.urlMonitor.component.ContextBeanProvider;
import org.urlMonitor.exception.HttpReadContentException;
import org.urlMonitor.model.Monitor;
import org.urlMonitor.model.type.StatusType;
import org.urlMonitor.service.events.EventPublisher;
import org.urlMonitor.service.events.MonitorUpdateEvent;

@Slf4j
public class MonitorJob implements Job
{
   private EventPublisher eventPublisher;
   private ContentReader contentReader;
   
   public void execute(JobExecutionContext context) throws JobExecutionException
   {
      Monitor monitor = (Monitor) context.getJobDetail().getJobDataMap().get("value");
      initRequiredComponents(monitor);
      
      try
      {
         StatusType updatedStatus = StatusType.Failed;
         String content = contentReader.readContent(monitor.getUrl());
         Pattern pattern = Pattern.compile(monitor.getContentRegex());
         Matcher matcher = pattern.matcher(content);
         if (matcher.find())
         {
            log.debug("Job {0} passed.", monitor.getName());
            updatedStatus = StatusType.Pass;
         }
         else
         {
            log.debug("Job {0} failed.", monitor.getName());
            updatedStatus = StatusType.Failed;
         }
         eventPublisher.fireEvent(new MonitorUpdateEvent(this, monitor.getId(), updatedStatus));
      }
      catch (ClientProtocolException e)
      {
         log.debug("Job {0} failed.", monitor.getName());
         eventPublisher.fireEvent(new MonitorUpdateEvent(this, monitor.getId(), StatusType.Unknown));
      }
      catch (IOException e)
      {
         log.debug("Job {0} failed.", monitor.getName());
         eventPublisher.fireEvent(new MonitorUpdateEvent(this, monitor.getId(), StatusType.Unknown));
      }
      catch (HttpReadContentException e)
      {
         log.debug("Job {0} failed.", monitor.getName());
         eventPublisher.fireEvent(new MonitorUpdateEvent(this, monitor.getId(), StatusType.Failed));
      }
   }
   
   private void initRequiredComponents(Monitor monitor)
   {
      if(eventPublisher == null)
      {
         eventPublisher = ContextBeanProvider.getBean(EventPublisher.class);
      }
      
      if(contentReader == null)
      {
         ContentReaderManager manager = ContextBeanProvider.getBean(ContentReaderManager.class);
         contentReader = manager.getOrCreateContentReader(monitor.getId(), monitor.getUrl());
      }
   }
}