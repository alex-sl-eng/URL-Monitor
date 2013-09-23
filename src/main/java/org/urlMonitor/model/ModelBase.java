package org.urlMonitor.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@MappedSuperclass
@Getter
public class ModelBase implements Serializable
{
   @Id
   @GeneratedValue
   @NonNull
   @Setter
   protected Long id;

   @Temporal(TemporalType.TIMESTAMP)
   @NonNull
   protected Date creationDate;

   @Temporal(TemporalType.TIMESTAMP)
   @NonNull
   protected Date lastChanged;

   @PrePersist
   public void onPersist()
   {
      Date now = new Date();
      if (creationDate == null)
      {
         creationDate = now;
      }
      if (lastChanged == null)
      {
         lastChanged = now;
      }
   }

   @PreUpdate
   private void onUpdate()
   {
      lastChanged = new Date();
   }
}
