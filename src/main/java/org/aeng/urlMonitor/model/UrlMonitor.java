package org.aeng.urlMonitor.model;

import org.aeng.urlMonitor.model.type.StatusType;

/**
 * @author aeng Alex Eng - loones1595@gmail.com
 *
 */
public class UrlMonitor
{
   private String name;
   private String description;

   private String url;
   private StatusType status;

   /**
    * see http://en.wikipedia.org/wiki/Cron#CRON_expression
    */
   private String cronExpression = "0/5 * * * * ?"; // every 5 seconds

   private String contentRegex;

   private String emailTo;

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription(String description)
   {
      this.description = description;
   }

   public String getUrl()
   {
      return url;
   }

   public void setUrl(String url)
   {
      this.url = url;
   }

   public String getCronExpression()
   {
      return cronExpression;
   }

   public void setCronExpression(String cronExpression)
   {
      this.cronExpression = cronExpression;
   }

   public String getEmailTo()
   {
      return emailTo;
   }

   public void setEmailTo(String emailTo)
   {
      this.emailTo = emailTo;
   }

   public String getContentRegex()
   {
      return contentRegex;
   }

   public void setContentRegex(String contentRegex)
   {
      this.contentRegex = contentRegex;
   }

   public StatusType getStatus()
   {
      return status;
   }

   public void setStatus(StatusType status)
   {
      this.status = status;
   }
}
