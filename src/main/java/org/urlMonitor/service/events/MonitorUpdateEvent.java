package org.urlMonitor.service.events;

import lombok.Getter;

import org.quartz.JobKey;
import org.springframework.context.ApplicationEvent;
import org.urlMonitor.model.type.StatusType;

public class MonitorUpdateEvent extends ApplicationEvent
{
   private static final long serialVersionUID = 1L;

   @Getter
   private final JobKey key;
   
   @Getter
   private StatusType status;
   
   public MonitorUpdateEvent(Object source, JobKey key, StatusType status)
   {
      super(source);
      this.key = key;
      this.status = status;
   }
}
