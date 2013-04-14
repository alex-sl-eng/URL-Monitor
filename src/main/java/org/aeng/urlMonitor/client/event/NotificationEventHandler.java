package org.aeng.urlMonitor.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface NotificationEventHandler extends EventHandler
{

   void onNotification(NotificationEvent event);

}
