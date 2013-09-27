package org.urlMonitor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import lombok.Getter;

/**
 * @author Alex Eng(aeng)  loones1595@gmail.com
 *
 */
@Service
@Scope("singleton")
public class AppConfiguration
{
   @Getter
   @Value("${build.version}")
   private String buildVersion;
   
   @Getter
   @Value("${build.date}")
   private String buildDate;
   
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

   @Value("${admin.username}")
   private String admins;
   
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

   public List<String> getAdminUsers()
   {
      return Lists.newArrayList(admins.split(","));
   }
}
