package org.urlMonitor.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.urlMonitor.component.MessageResource;
import org.urlMonitor.model.Monitor;
import org.urlMonitor.util.DateUtil;

@Service
@Scope("singleton")
@Slf4j
public class EmailService
{
   @Autowired
   private AppConfiguration appConfiguration;
   
   @Autowired
   private MessageResource messageResource;
   
   private Email getEmailClient()
   {
      Email email = new SimpleEmail();
      email.setHostName(appConfiguration.getEmailHost());
      email.setSmtpPort(appConfiguration.getEmailPort());
      email.setSSLOnConnect(appConfiguration.isEmailUseSsl());
      email.setStartTLSEnabled(appConfiguration.isEmailUseTsl());
      email.setAuthenticator(new DefaultAuthenticator(appConfiguration.getEmailUsername(), appConfiguration.getEmailPassword()));
      
      return email;
   }

   public void sendFailedEmail(Monitor monitor, Date lastCheck) throws EmailException
   {
      String subject = messageResource.getMessage("email.subjectPrefix") + monitor.getName();
      sendEmail(subject, getFailedMessage(monitor.getUrl(), monitor.getContentRegex(), lastCheck), monitor.getEmailToList());
   }

   public void sendSuccessEmail(Monitor monitor, Date lastCheck) throws EmailException
   {
      String subject = messageResource.getMessage("email.subjectPrefix") + monitor.getName();
      sendEmail(subject, getSuccessMessage(monitor.getUrl(), lastCheck), monitor.getEmailToList());
   }

   private void sendEmail(String subject, String message, List<String> toEmailList) throws EmailException
   {
      if (toEmailList != null && !toEmailList.isEmpty() && !StringUtils.isEmpty(message))
      {
         Email email = getEmailClient();
         
         email.setFrom(appConfiguration.getEmailFrom());
         
         for (String toEmail : toEmailList)
         {
            email.addTo(toEmail);
         }

         email.setSubject(subject);
         email.setMsg(message);

         email.send();
         log.info("sent email to - " + toEmailList);
      }
      else
      {
         log.info("send email ignored - no email recipients defined.");
      }
   }

   private String getSuccessMessage(String url, Date lastCheck)
   {
      StringBuilder sb = new StringBuilder();
      sb.append(getEmailHeader());

      sb.append(messageResource.getMessage("email.successMessage", url, DateUtil.formatShortDate(lastCheck)));

      sb.append(getEmailFooter());

      return sb.toString();
   }

   private String getFailedMessage(String url, String searchString, Date lastFailedTime)
   {
      StringBuilder sb = new StringBuilder();
      sb.append(getEmailHeader());

      sb.append(messageResource.getMessage("email.failMessage", url, DateUtil.formatShortDate(lastFailedTime)));
      sb.append("\n");
      sb.append(messageResource.getMessage("email.contentSearched", searchString));

      sb.append(getEmailFooter());

      return sb.toString();
   }

   private String getEmailHeader()
   {
      StringBuilder sb = new StringBuilder();
      sb.append(messageResource.getMessage("email.header"));
      sb.append("\n");
      sb.append("\n");

      return sb.toString();
   }

   private String getEmailFooter()
   {
      StringBuilder sb = new StringBuilder();
      try
      {
         InetAddress addr = InetAddress.getLocalHost();
         sb.append("\n");
         sb.append("\n");
         sb.append(addr.getHostName());
         sb.append("\n");
      }
      catch (UnknownHostException e)
      {
      }

      if (!StringUtils.isEmpty(appConfiguration.getEmailReplyTo()))
      {
         sb.append(appConfiguration.getEmailReplyTo());
      }
      return sb.toString();
   }
}
