package org.urlMonitor.service.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.urlMonitor.model.type.StatusType;

public class UserUpdateEvent extends ApplicationEvent
{
   private static final long serialVersionUID = 1L;

   @Getter
   private final String email;

   public UserUpdateEvent(Object source, String email)
   {
      super(source);
      this.email = email;
   }
}
