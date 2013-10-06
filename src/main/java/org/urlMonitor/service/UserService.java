package org.urlMonitor.service;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.urlMonitor.model.User;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Transactional
public interface UserService {

    public static String ROLE_USER = "ROLE_USER";
    public static String ROLE_ADMIN = "ROLE_ADMIN";

    /**
     * Load all roles available in application
     * 
     * @param email
     * @return
     */
    Map<String, Boolean> getUserRoles(String email);

    User findByEmail(String email);

    User updateUserByEmail(String email, String updatedName,
            boolean isAdmin, boolean isUser);
}
