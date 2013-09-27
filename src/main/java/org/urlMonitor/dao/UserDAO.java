package org.urlMonitor.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.urlMonitor.model.User;
import org.urlMonitor.model.UserRoles;
import com.google.common.collect.Lists;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Repository
@Transactional
public class UserDAO extends AbstractDAO<User, Long> implements UserDetailsService
{
   @Autowired
   private UserRolesDAO userRolesDAO;

   protected UserDAO()
   {
      super(User.class);
   }

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
   {
      User user = findByUsername(username);

      if (user == null)
      {
         user = createUser(username, true);
         userRolesDAO.createDefaultRole(user);
      }

      List<SimpleGrantedAuthority> authorities = Lists.newArrayList();
      for (UserRoles role : user.getRoles())
      {
         authorities.add(new SimpleGrantedAuthority(role.getRole()));
      }
      return new org.springframework.security.core.userdetails.User(username, "" , user.isEnabled(), true, true, true, authorities);
   }

   public User createUser(String username, boolean enabled)
   {
      User user = new User(username, "", "", enabled);
      saveOrUpdate(user);
      getEntityManager().flush();
      return user;
   }

   public User findByUsername(String username)
   {
      Query query = getEntityManager().createQuery("from User u where u.username=:username");
      query.setParameter("username", username);

      try
      {
         return (User)query.getSingleResult();
      }
      catch (NoResultException e)
      {
         return null;
      }
   }
}
