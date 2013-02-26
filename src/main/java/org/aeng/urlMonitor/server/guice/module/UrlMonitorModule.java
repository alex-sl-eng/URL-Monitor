package org.aeng.urlMonitor.server.guice.module;

import org.aeng.urlMonitor.server.service.DBService;
import org.aeng.urlMonitor.server.service.UrlMonitorService;

import com.google.inject.AbstractModule;

public class UrlMonitorModule extends AbstractModule
{
   @Override
   protected void configure()
   {
      binder().bind(UrlMonitorService.class);
      binder().bind(DBService.class);
   }

}
