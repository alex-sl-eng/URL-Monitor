package org.aeng.urlMonitor.service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

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

   public void sendEmail(String subject, String message, List<String> toEmailList) throws EmailException
   {
      if (toEmailList != null && !toEmailList.isEmpty())
      {
         Email email = new SimpleEmail();

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
      }
      else
      {
         log.warn("Action ignored - no email recipients defined.");
      }
   }
}
