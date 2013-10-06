package org.urlMonitor.component;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.urlMonitor.model.User;
import org.urlMonitor.service.Impl.UserServiceImpl;
import org.urlMonitor.service.UserService;
import org.urlMonitor.service.events.UserUpdateEvent;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Identity implements ApplicationListener<UserUpdateEvent> {

    @Autowired
    private UserService userServiceImpl;

    private User user;

    private UserDetails userDetails;

    public String getEmail() {
        return getUserDetails().getUsername();
    }

    public String getName() {
        return getUser().getName();
    }

    public Date getJoinedDate() {
        return getUser().getCreationDate();
    }

    public boolean isLoggedIn() {
        return SecurityContextHolder.getContext().getAuthentication()
                .isAuthenticated();
    }

    public boolean isAdmin() {
        if (isLoggedIn()) {
            for (GrantedAuthority auth : getUserDetails().getAuthorities()) {
                if (auth.getAuthority().equals(UserServiceImpl.USER_ADMIN)) {
                    return true;
                }
            }
        }
        return false;
    }

    private UserDetails getUserDetails() {
        if (userDetails == null) {
            userDetails =
                    (UserDetails) SecurityContextHolder.getContext()
                            .getAuthentication().getPrincipal();
        }
        return userDetails;
    }

    private User getUser() {
        if (user == null) {
            user = userServiceImpl.findByEmail(getEmail());
        }
        return user;
    }

    @Override
    public void onApplicationEvent(UserUpdateEvent event) {
        //force refresh of user details if updated
        if (getEmail().equals(event.getEmail())) {
            this.user = null;
        }
    }
}
