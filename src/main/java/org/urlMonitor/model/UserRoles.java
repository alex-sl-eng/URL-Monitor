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
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Entity
@Table(name="User_Roles")
@Getter
@Setter
@Access(AccessType.FIELD)
public class UserRoles implements Serializable
{
   public UserRoles(User user, String role)
   {
      this.user = user;
      this.role = role;
   }

   @NotNull
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "user_id", nullable = false)
   @Id
   private User user;

   @NotNull
   @Id
   private String role;
}
