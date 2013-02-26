package org.aeng.urlMonitor.server.guice;

import javax.servlet.ServletContextEvent;

import org.aeng.urlMonitor.server.service.UrlMonitorService;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class ServletContextListener extends GuiceServletContextListener
{
   @Inject
   private UrlMonitorService urlMonitorService;

   @Override
   protected Injector getInjector()
   {
      return GuiceInjectorFactory.getInjector();
   }

   @Override
   public void contextInitialized(ServletContextEvent servletContextEvent)
   {
      super.contextInitialized(servletContextEvent);
      
      startMainService();
   }
   
   private void startMainService()
   {
      urlMonitorService.init();
   }

}
