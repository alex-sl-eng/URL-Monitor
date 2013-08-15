package org.urlMonitor.service.events;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MonitorUpdateEventListener implements ApplicationListener<MonitorUpdateEvent>
{
   public void onApplicationEvent(MonitorUpdateEvent event)
   {

   }

}
