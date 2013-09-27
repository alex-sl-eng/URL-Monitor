package org.urlMonitor.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.urlMonitor.model.User;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Repository
@Transactional
public class UserDAO extends AbstractDAO<User, Long>
{
   @Autowired
   private UserRolesDAO userRolesDAO;

   protected UserDAO()
   {
      super(User.class);
   }

   public User createUser(String username, boolean enabled)
   {
      User user = new User(username, "", "", enabled);
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
}
