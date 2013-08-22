package org.urlMonitor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.UrlValidator;
import org.urlMonitor.exception.InvalidMonitorFileException;
import org.urlMonitor.model.type.StatusType;
import org.urlMonitor.util.DateUtil;

/**
 * @author Alex Eng - loones1595@gmail.com
 *
 */
@EqualsAndHashCode(of = { "name", "url", "contentRegex", "cronExpression" })
public class Monitor implements Serializable
{
   private static final long serialVersionUID = 1L;
   
   @Getter
   @NonNull
   private Long id;
   
   @Getter
   @NonNull
   private String name;

   @Getter
   private String description;

   @Getter
   @NonNull
   private String url;

   @Getter
   private StatusType status = StatusType.Unknown;

   @Getter
   private Date lastCheck;

   /**
    * see http://en.wikipedia.org/wiki/Cron#CRON_expression
    */
   @Getter
   @NonNull
   private String cronExpression = "0/5 * * * * ?"; // every 5 seconds

   @Getter
   @NonNull
   private String contentRegex;

   @Setter
   private String emailToList;
   
   private String tag;

   /**
    * This is used to expose formatted date to JSON object in script
    */
   @Getter
   private String formattedLastCheck;

   public Monitor(Properties prop) throws InvalidMonitorFileException
   {
      this.name = StringUtils.trimToEmpty(prop.getProperty("name"));
      this.url = StringUtils.trimToEmpty(prop.getProperty("url"));
      this.cronExpression = StringUtils.trimToEmpty(prop.getProperty("cronExpression"));
      this.contentRegex = StringUtils.trimToEmpty(prop.getProperty("contentRegex"));

      isMandatoryFieldsPresent();
      validateNameRegexAndTag();

      this.description = StringUtils.trimToEmpty(prop.getProperty("description"));
      this.tag = StringUtils.trimToEmpty(prop.getProperty("tag"));
      this.emailToList = StringUtils.trimToEmpty(prop.getProperty("emailToList"));

      id = new Long(this.hashCode());
   }

   private void isMandatoryFieldsPresent() throws InvalidMonitorFileException
   {
      if (StringUtils.isEmpty(name) || StringUtils.isEmpty(url)
            || StringUtils.isEmpty(cronExpression) || StringUtils.isEmpty(contentRegex))
      {
         throw new InvalidMonitorFileException("Missing mandatory field(s) in monitor file.");
      }
   }
   
   private void validateNameRegexAndTag() throws InvalidMonitorFileException
   {
      if(StringUtils.containsWhitespace(name))
      {
         throw new InvalidMonitorFileException("Invalid name-" + name);
      }
      
      UrlValidator urlValidator = new UrlValidator();
      if (!urlValidator.isValid(url)) {
         throw new InvalidMonitorFileException("Invalid url-" + url);
      }
      
      if(StringUtils.containsWhitespace(contentRegex))
      {
         throw new InvalidMonitorFileException("Invalid content regular expression-" + contentRegex);
      }
      
      if(!org.quartz.CronExpression.isValidExpression(cronExpression))
      {
         throw new InvalidMonitorFileException("Invalid cron expression-" + cronExpression);
      }
   }

   public List<String> getEmailTo()
   {
      if (emailToList != null)
      {
         return Arrays.asList(emailToList.split(";"));
      }
      return new ArrayList<String>();
   }

   public List<String> getTag()
   {
      if (tag != null)
      {
         return Arrays.asList(tag.split(";"));
      }
      return new ArrayList<String>();
   }

   public void update(StatusType status)
   {
      this.status = status;
      this.lastCheck = new Date();
      this.formattedLastCheck = DateUtil.formatShortDate(lastCheck);
   }
   
}
