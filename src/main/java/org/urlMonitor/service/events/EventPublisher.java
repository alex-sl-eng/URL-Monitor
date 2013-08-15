package org.urlMonitor.service.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher
{
   @Autowired
   private SimpleApplicationEventMulticaster eventMulticaster;

   @Autowired
   private AsyncTaskExecutor taskExecutor;
   

   public void fireEvent(ApplicationEvent event)
   {
      eventMulticaster.setTaskExecutor(taskExecutor);
      eventMulticaster.multicastEvent(event);
   }
}
