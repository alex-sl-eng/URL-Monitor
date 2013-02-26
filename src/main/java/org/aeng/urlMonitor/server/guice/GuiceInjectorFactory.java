/**
 * 
 */
package org.aeng.urlMonitor.server.guice;

import org.aeng.urlMonitor.server.guice.module.DispatchServletModule;
import org.aeng.urlMonitor.server.guice.module.ServerHandlerModule;
import org.aeng.urlMonitor.server.guice.module.UrlMonitorModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author Alex Eng(aeng) - loones1595@gmail.com
 *
 */
public class GuiceInjectorFactory
{
   private static final Injector injector = Guice.createInjector(new ServerHandlerModule(), new DispatchServletModule(), new UrlMonitorModule());

   public static Injector getInjector()
   {
      return injector;
   }
}
