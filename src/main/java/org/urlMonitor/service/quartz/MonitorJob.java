package org.urlMonitor.service.quartz;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.urlMonitor.component.ContextBeanProvider;
import org.urlMonitor.component.WebClient;
import org.urlMonitor.component.WebClientManager;
import org.urlMonitor.model.Monitor;
import org.urlMonitor.model.type.StatusType;
import org.urlMonitor.service.events.EventPublisher;
import org.urlMonitor.service.events.MonitorUpdateEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MonitorJob implements Job
{
   public void execute(JobExecutionContext context) throws JobExecutionException
   {
      Monitor monitor = (Monitor) context.getJobDetail().getJobDataMap().get("value");

      EventPublisher eventPublisher = ContextBeanProvider.getBean(EventPublisher.class);
      WebClientManager manager = ContextBeanProvider.getBean(WebClientManager.class);
      WebClient webClient = null;
      
      if(manager != null)
      {
         webClient = manager.getOrCreateWebClient(monitor.getId(), monitor.getUrl());
      }

      if (webClient != null && eventPublisher != null)
      {
         try
         {
            StatusType updatedStatus = webClient.checkStatus(monitor.getUrl(), monitor.getContentRegex());
            log.debug("{} status: {}", monitor.getName(), updatedStatus);

            eventPublisher.fireEvent(new MonitorUpdateEvent(this, monitor.getId(), updatedStatus));
         }
         catch (ClientProtocolException e)
         {
            log.debug("{} failed.", monitor.getName());
            eventPublisher.fireEvent(new MonitorUpdateEvent(this, monitor.getId(), StatusType.Unknown));
         }
         catch (IOException e)
         {
            log.debug("{} failed.", monitor.getName());
            eventPublisher.fireEvent(new MonitorUpdateEvent(this, monitor.getId(), StatusType.Unknown));
         }
         finally
         {
            webClient.close();
         }
      }
   }
}