/**
 * 
 */
package org.aeng.urlMonitor.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * 
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 * 
 */
public class NotificationEvent extends GwtEvent<NotificationEventHandler>
{

   public static enum Severity
   {
      Warning, Error, Info
   }

   private static Type<NotificationEventHandler> TYPE = new Type<NotificationEventHandler>();

   private final Severity severity;
   private final String message;

   public NotificationEvent(Severity severity, String message)
   {
      this.severity = severity;
      this.message = message;
   }

   @Override
   public com.google.gwt.event.shared.GwtEvent.Type<NotificationEventHandler> getAssociatedType()
   {
      return TYPE;
   }

   @Override
   protected void dispatch(NotificationEventHandler handler)
   {
      handler.onNotification(this);
   }

   public Severity getSeverity()
   {
      return severity;
   }

   public String getMessage()
   {
      return message;
   }
}
