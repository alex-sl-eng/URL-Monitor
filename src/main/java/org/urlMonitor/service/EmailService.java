package org.urlMonitor.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.urlMonitor.model.Monitor;
import org.urlMonitor.util.DateUtil;

@Service
@Slf4j
public class EmailService
{
   @Value("${email.smtpPort}")
   private String port;

   @Value("${email.host}")
   private String host;

   @Value("${email.from}")
   private String from;
   
   @Value("${email.replyTo}")
   private String replyTo;

   private static String PREFIX_SUBJECT = "Url Monitor notification: ";

   public void sendEmail(Monitor monitor, Date lastFailedTime)
   {
      String subject = PREFIX_SUBJECT + monitor.getName();
      sendEmail(subject, getMessage(monitor.getUrl(), lastFailedTime), monitor.getEmailTo());
   }

   public void sendEmail(String subject, String message, List<String> toEmailList)
   {
      if (toEmailList != null && !toEmailList.isEmpty())
      {
         Email email = new SimpleEmail();

         try
         {
            email.setFrom(from);

            for (String toEmail : toEmailList)
            {
               email.addTo(toEmail);
            }

            email.setSmtpPort(Integer.parseInt(port));
            email.setHostName(host);

            email.setSubject(subject);
            email.setMsg(message);

            email.send();
            log.info("sent email to - " + toEmailList);
         }
         catch (EmailException e)
         {
            log.error("Unable to send email-" + e);
         }

      }
      else
      {
         log.warn("Action ignored - no email recipients defined.");
      }
   }

   private String getMessage(String url, Date lastFailedTime)
   {
      StringBuilder sb = new StringBuilder();
      sb.append("This is an generated email. Please DO NOT REPLY TO THIS EMAIL.");
      sb.append("\n");
      sb.append("Checking failed on ");
      sb.append(DateUtil.formatShortDate(lastFailedTime));
      sb.append(" for url ");
      sb.append(url);
      sb.append(getEmailFooter());
      
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
         sb.append("Generated from:");
         sb.append(addr.getHostName());
         sb.append("\n");
      }
      catch (UnknownHostException e)
      {
      }
      
      if(!StringUtils.isEmpty(replyTo))
      {
         sb.append("Admin contact: ");
         sb.append(replyTo);
      }
      return sb.toString();
   }

}
