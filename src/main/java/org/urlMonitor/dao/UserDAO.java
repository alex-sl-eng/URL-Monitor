package org.urlMonitor.dao;

import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.urlMonitor.model.User;
import org.urlMonitor.model.UserRole;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Repository
@Transactional
public class UserDAO extends AbstractDAO<User, Long>
{
   protected UserDAO()
   {
      super(User.class);
   }

   public User createUser(String username, String fullName, String email, boolean enabled, Set<String> roles)
   {
      User user = new User(username, fullName, email, enabled);
      for (String userRole : roles)
      {
         user.addRole(new UserRole(userRole));
      }
      saveOrUpdate(user);
      return user;
   }

   public User findByUsername(String username)
   {
      Query query = getEntityManager().createQuery("from User u where u.username=:username");
      query.setParameter("username", username);
      try
      {
         return (User) query.getSingleResult();
      }
      catch (NoResultException e)
      {
         return null;
      }
   }

   public User findByEmail(String email)
   {
      Query query = getEntityManager().createQuery("from User u where u.email=:email");
      query.setParameter("email", email);
      try
      {
         return (User) query.getSingleResult();
      }
      catch (NoResultException e)
      {
         return null;
      }
   }
}
