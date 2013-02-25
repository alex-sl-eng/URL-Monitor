package org.aeng.urlMonitor.server.guice;

import org.aeng.urlMonitor.server.service.UrlMonitorService;

import com.google.inject.Binder;
import com.google.inject.Module;

public class BindModule implements Module
{
   @Override
   public void configure(Binder binder)
   {
      binder.bind(UrlMonitorService.class);
   }

}
