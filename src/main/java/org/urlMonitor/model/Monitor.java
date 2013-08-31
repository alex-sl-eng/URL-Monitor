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
import org.urlMonitor.exception.InvalidMonitorFileException;
import org.urlMonitor.model.type.PredefinedCron;
import org.urlMonitor.model.type.StatusType;
import org.urlMonitor.util.DateUtil;
import org.urlMonitor.util.MonitorValidator;

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
   private String cronExpression = PredefinedCron.ONE_MINUTE; // DEFAULT: every 1 minutes

   @Getter
   private String contentRegex; //check for text exist if return http 200

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

      this.description = StringUtils.trimToEmpty(prop.getProperty("description"));
      this.tag = StringUtils.trimToEmpty(prop.getProperty("tag"));
      this.emailToList = StringUtils.trimToEmpty(prop.getProperty("emailToList"));

      id = new Long(this.hashCode());
      
      MonitorValidator.isMandatoryFieldsPresent(this);
      MonitorValidator.validateNameRegexAndTag(this);
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
