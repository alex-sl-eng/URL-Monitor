package org.urlMonitor.component;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class MessageResource
{
   @Autowired
   private MessageSource messageSource;
   
   private final Locale locale = Locale.getDefault();
   
   public String getMessage(String messageKey, Object ...args)
   {
      return messageSource.getMessage(messageKey, args, locale);
   }
   
   public String getMessage(String messageKey)
   {
      return messageSource.getMessage(messageKey, null, locale);
   }
}
