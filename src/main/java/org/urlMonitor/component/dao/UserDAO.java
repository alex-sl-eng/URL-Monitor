package org.urlMonitor.component.dao;

import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.urlMonitor.model.User;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Repository
@Transactional
public class UserDAO extends AbstractDAO<User, Long> {
    protected UserDAO() {
        super(User.class);
    }

    public User createUser(String fullName, String email, boolean enabled,
            Set<String> roles) {
        User user = new User(fullName, email, enabled);
        for (String userRole : roles) {
            user.addRole(userRole);
        }
        saveOrUpdate(user);
        return user;
    }

    public User findByEmail(String email) {
        if (!StringUtils.isEmpty(email)) {
            Query query =
                    getEntityManager().createQuery(
                            "from User u where u.email=:email");
            query.setParameter("email", email);
            try {
                return (User) query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        }
        return null;
    }
}
