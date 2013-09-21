package org.urlMonitor.model;

import java.io.*;
import java.util.*;

import javax.persistence.*;

import lombok.*;

import org.apache.commons.lang3.*;
import org.hibernate.validator.constraints.*;
import org.urlMonitor.exception.*;
import org.urlMonitor.model.type.*;
import org.urlMonitor.util.*;

/**
 * @author Alex Eng - loones1595@gmail.com
 *
 */
@Entity
@NoArgsConstructor
@Access(AccessType.FIELD)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, of = { "name", "url", "contentRegex", "cron" })
public class Monitor extends ModelBase implements Serializable
{
   private static final long serialVersionUID = 1L;

   @NonNull
   @Length(max = 100)
   private String name;

   @Length(max = 255)
   private String description;

   @NonNull
   @URL
   @Length(max = 2083)
   private String url;

   @Enumerated(EnumType.STRING)
   @Length(max = 20)
   private StatusType status = StatusType.Unknown;

   /**
    * see http://en.wikipedia.org/wiki/Cron#CRON_expression
    */
   @NonNull
   @Length(max = 100)
   private String cron = CronHelper.CronType.ONE_MINUTE.getExpression(); // DEFAULT: every 1 minutes

   @Length(max = 255)
   private String contentRegex; //check for text exist if return http 200

   @Length(max = 255)
   private String emailToList;

   @Length(max = 255)
   private String tag;

   /**
    * This is used to expose formatted date to JSON object in script
    */
   @Transient
   private String lastCheck;

   @Temporal(TemporalType.TIMESTAMP)
   private Date lastFailed;

   @Transient
   public List<String> getEmailToList()
   {
      if (!StringUtils.isEmpty(emailToList))
      {
         return Arrays.asList(emailToList.split(";"));
      }
      return new ArrayList<String>();
   }

   @Transient
   public List<String> getTagList()
   {
      if (!StringUtils.isEmpty(tag))
      {
         return Arrays.asList(tag.split(";"));
      }
      return new ArrayList<String>();
   }

   public void update(StatusType status)
   {
      this.status = status;

      Date now = new Date();
      if(status == StatusType.Failed)
      {
         lastFailed = now;
      }

      //TODO: remove this once loaded from db
      lastChanged = now;
      afterUpdate();
   }

   //TODO: remove this once loaded from db
   @PostUpdate
   private void afterUpdate()
   {
      lastCheck = DateUtil.getHowLongAgoDescription(getLastChanged(), new Date());
   }
}
