package org.urlMonitor.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.Email;

import com.google.common.collect.Sets;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Entity
@Access(AccessType.FIELD)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class User extends ModelBase implements Serializable
{
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

   @ManyToMany
   @JoinTable(name = "User_Roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role"))
   private Set<UserRoles> roles = Sets.newHashSet();
}
