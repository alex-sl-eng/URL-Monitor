package org.urlMonitor.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.urlMonitor.model.type.StatusType;
import org.urlMonitor.util.CronHelper;
import com.google.common.collect.Lists;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

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

   @Temporal(TemporalType.TIMESTAMP)
   private Date lastFailed;

   @Transient
   public List<String> getEmailToList()
   {
      if (!StringUtils.isEmpty(emailToList))
      {
         return Arrays.asList(emailToList.split(";"));
      }
      return Lists.newArrayList();
   }

   @Transient
   public List<String> getTagList()
   {
      if (!StringUtils.isEmpty(tag))
      {
         return Arrays.asList(tag.split(";"));
      }
      return Lists.newArrayList();
   }

   public void update(StatusType status)
   {
      this.status = status;

      Date now = new Date();
      if (status == StatusType.Failed)
      {
         lastFailed = now;
      }

      //TODO: remove this once loaded from db
      lastChanged = now;
   }
}
