package org.urlMonitor.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.urlMonitor.service.UserService;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Component
@Scope("session")
public class Identity
{
   @Autowired
   private UserService userService;

   private User user;

   public String getUsername()
   {
      return getUser().getUsername();
   }

   public boolean isLoggedIn()
   {
      return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
   }

   public boolean isNewUser()
   {
      return userService.isNewUser(getUsername());
   }

   private User getUser()
   {
      if (user == null)
      {
         user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      }
      return user;
   }
}
