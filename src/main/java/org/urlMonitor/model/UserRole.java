package org.urlMonitor.model;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Entity
@Table(name = "User_Role")
@Getter
@Setter
@Access(AccessType.FIELD)
@NoArgsConstructor
public class UserRole implements Serializable
{
   public UserRole(String role)
   {
      this.role = role;
   }

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "user_id")
   @Id
   private User user;

   @Id
   private String role;
}
