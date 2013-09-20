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
   private String name;

   private String description;

   @NonNull
   @URL
   private String url;

   @Enumerated(EnumType.STRING)
   private StatusType status = StatusType.Unknown;

   /**
    * see http://en.wikipedia.org/wiki/Cron#CRON_expression
    */
   @NonNull
   private String cron = CronHelper.CronType.ONE_MINUTE.getExpression(); // DEFAULT: every 1 minutes

   private String contentRegex; //check for text exist if return http 200

   private String emailToList;

   private String tag;

   /**
    * This is used to expose formatted date to JSON object in script
    */
   @Getter
   @Transient
   private String formattedLastCheck;

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

   @Transient
   public CronHelper.CronType getCronType()
   {
      return CronHelper.getTypeFromExpression(getCron());
   }

   public void update(StatusType status)
   {
      this.status = status;

      //TODO: remove this once loaded from db
      lastChanged = new Date();
      afterUpdate();
   }

   @PostUpdate
   private void afterUpdate()
   {
      formattedLastCheck = DateUtil.formatShortDate(getLastChanged());
   }
}
