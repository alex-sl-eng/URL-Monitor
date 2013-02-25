package org.aeng.urlMonitor.server.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;


public class UrlMonitorServletContextListener extends GuiceServletContextListener {

  @Override
  protected Injector getInjector() {
      return Guice.createInjector(new ServerHandlerModule(), new DispatchServletModule(), new BindModule());
  }
}
