package org.urlMonitor.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import com.google.common.collect.Sets;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Entity
@Access(AccessType.FIELD)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class User extends ModelBase implements Serializable
{
   public User(String username, String name, String email, boolean enabled)
   {
      this.username = username;
      this.name = name;
      this.email = email;
      this.enabled = enabled;
   }

   @NotNull
   @Size(max = 100)
   @Column(unique = true)
   private String username;

   @NotNull
   @Size(max = 255)
   private String name;

   @Size(max = 100)
   @Email
   private String email;

   @NotNull
   private boolean enabled;

   @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
   private Set<UserRoles> roles = Sets.newHashSet();
}
