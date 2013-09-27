package org.urlMonitor.component;

import java.util.Collection;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.urlMonitor.service.UserService;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Identity
{
   private User user;

   public String getUsername()
   {
      return getUser().getUsername();
   }

   public boolean isLoggedIn()
   {
      return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
   }

   public boolean isAdmin()
   {
      if (isLoggedIn())
      {
         Collection<GrantedAuthority> authorities = user.getAuthorities();
         for (GrantedAuthority auth : authorities)
         {
            if (auth.getAuthority().equals(UserService.USER_ADMIN))
            {
               return true;
            }
         }
      }
      return false;
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
