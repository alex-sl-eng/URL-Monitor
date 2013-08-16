package org.urlMonitor.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class ContextBeanProvider implements ApplicationContextAware
{
   private static ApplicationContext applicationContext = null;

   public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
   {
      ContextBeanProvider.applicationContext = applicationContext;
   }
   
   public static <T> T getBean(Class<T> clazz)
   {
      return applicationContext.getBean(clazz);
   }

}
