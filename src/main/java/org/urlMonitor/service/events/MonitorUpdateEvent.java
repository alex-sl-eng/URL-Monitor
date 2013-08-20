package org.urlMonitor.service.events;

import lombok.Getter;

import org.springframework.context.ApplicationEvent;
import org.urlMonitor.model.type.StatusType;

public class MonitorUpdateEvent extends ApplicationEvent
{
   private static final long serialVersionUID = 1L;

   @Getter
   private final Integer hashCode;
   
   @Getter
   private StatusType status;
   
   public MonitorUpdateEvent(Object source, Integer hashCode, StatusType status)
   {
      super(source);
      this.hashCode = hashCode;
      this.status = status;
   }
}
