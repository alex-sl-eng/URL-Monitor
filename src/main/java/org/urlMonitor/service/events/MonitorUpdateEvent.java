package org.urlMonitor.service.events;

import org.springframework.context.ApplicationEvent;
import org.urlMonitor.model.type.StatusType;

import lombok.Getter;

public class MonitorUpdateEvent extends ApplicationEvent
{
   private static final long serialVersionUID = 1L;

   @Getter
   private final Long id;
   
   @Getter
   private StatusType status;
   
   public MonitorUpdateEvent(Object source, Long id, StatusType status)
   {
      super(source);
      this.id = id;
      this.status = status;
   }
}
