package org.urlMonitor.service.events;

import org.springframework.context.ApplicationEvent;

public class MonitorUpdateEvent extends ApplicationEvent
{
   private static final long serialVersionUID = 1L;

   public MonitorUpdateEvent(Object source)
   {
      super(source);
   }
}
