package org.aeng.urlMonitor.server.guice;

import org.aeng.urlMonitor.server.guice.module.DispatchServletModule;
import org.aeng.urlMonitor.server.guice.module.ServerHandlerModule;
import org.aeng.urlMonitor.server.guice.module.UrlMonitorModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;


public class ServletContextListener extends GuiceServletContextListener {

  @Override
  protected Injector getInjector() {
      return Guice.createInjector(new ServerHandlerModule(), new DispatchServletModule(), new UrlMonitorModule());
  }
}
