package org.urlMonitor.service;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.urlMonitor.model.User;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Transactional
public interface UserService {

    /**
     * Load all roles available in application
     * 
     * @param email
     * @return
     */
    Map<String, Boolean> getUserRoles(String email);

    User findByEmail(String email);

    User updateUserByEmail(String email, String updatedName);

    User updateUserByEmail(String email, String updatedName,
            Map<String, Boolean> roles);
}
