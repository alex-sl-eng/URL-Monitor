package org.urlMonitor.model;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Entity(name = "user_roles")
@Getter
@Setter
@Access(AccessType.FIELD)
public class UserRoles implements Serializable
{
   @Id
   @GeneratedValue
   @NotNull
   protected Long id;

   @NotNull
   @JoinColumn(name = "user_id")
   private User user;

   @NotNull
   private String role;
}
