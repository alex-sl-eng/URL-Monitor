package org.urlMonitor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.urlMonitor.dao.UserDAO;
import org.urlMonitor.dao.UserRolesDAO;
import org.urlMonitor.model.User;
import org.urlMonitor.model.UserRoles;

import com.google.common.collect.Lists;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Service
@Scope("singleton")
public class UserService implements UserDetailsService
{
   public static String USER_ROLE = "ROLE_USER";
   public static String USER_ADMIN = "ROLE_ADMIN";

   @Autowired
   private UserDAO userDAO;

   @Autowired
   private UserRolesDAO userRolesDAO;

   @Autowired
   private AppConfiguration appConfiguration;

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
   {
      User user = userDAO.findByUsername(username);
      boolean isNewUser = user == null;
      if (isNewUser)
      {
         user = createUserWithRoles(username);
      }

      List<SimpleGrantedAuthority> authorities = Lists.newArrayList();
      for (UserRoles role : user.getRoles())
      {
         authorities.add(new SimpleGrantedAuthority(role.getRole()));
      }
      return new org.springframework.security.core.userdetails.User(username, "", user.isEnabled(), true, true, true, authorities);
   }

   /**
    * Return if user is new - account disabled or no entry in db
    * @param username
    * @return
    */
   public boolean isNewUser(String username)
   {
      User user = userDAO.findByUsername(username);
      return user == null || !user.isEnabled();
   }

   /**
    * Create user with enabled=false, with role {ROLE_USER} and
    * {ROLE_ADMIN} if username predefined in urlmonitor.properties
    * @param username
    * @return
    */
   public User createUserWithRoles(String username)
   {
      User user = userDAO.createUser(username, false);
      userDAO.getEntityManager().flush();

      userRolesDAO.createRole(user, USER_ROLE);

      if (isUserAdmin(username))
      {
         userRolesDAO.createRole(user, USER_ADMIN);
      }

      userDAO.getEntityManager().flush();

      return user;
   }

   public boolean isUserAdmin(String username)
   {
      for (String predefinedAdminUser : appConfiguration.getAdminUsers())
      {
         if (username.equals(predefinedAdminUser))
         {
            return true;
         }
      }
      return false;
   }
}
