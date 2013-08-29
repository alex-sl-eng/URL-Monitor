package org.urlMonitor.service;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author Alex Eng(aeng)  loones1595@gmail.com
 *
 */
@Service
@Scope("singleton")
public class AppConfiguration
{
   @Getter
   @Value("${email.host}")
   private String emailHost;
   
   @Value("${email.port}")
   private String emailPort;

   @Getter
   @Value("${email.from}")
   private String emailFrom;

   @Getter
   @Value("${email.replyTo}")
   private String emailReplyTo;
   
   @Value("${email.ssl}")
   private String emailUseSsl;
   
   @Value("${email.tsl}")
   private String emailUseTsl;
   
   @Getter
   @Value("${email.username}")
   private String emailUsername;
   
   @Getter
   @Value("${email.password}")
   private String emailPassword;

   @Getter
   @Value("${monitor.files.path}")
   private String filesPath;

   @Value("${retry.count}")
   private String retryCount;
   
   public int getEmailPort()
   {
      return Integer.parseInt(emailPort);
   }
   
   public boolean isEmailUseSsl()
   {
      return Boolean.parseBoolean(emailUseSsl);
   }
   
   public boolean isEmailUseTsl()
   {
      return Boolean.parseBoolean(emailUseTsl);
   }
   
   public int getRetryCount()
   {
      return Integer.parseInt(retryCount);
   }
}
