package org.urlMonitor.service.events;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventPublisher
{
   @Autowired
   private SimpleApplicationEventMulticaster simpleApplicationEventMulticaster;

   private SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();

   public void fireEvent(ApplicationEvent event)
   {
      log.debug("Fire event: " + event.toString());
      simpleApplicationEventMulticaster.setTaskExecutor(asyncTaskExecutor);
      simpleApplicationEventMulticaster.multicastEvent(event);
   }
}
