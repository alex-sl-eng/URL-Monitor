package org.urlMonitor.component;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.urlMonitor.model.Monitor;
import org.urlMonitor.model.User;
import org.urlMonitor.service.Impl.UserServiceImpl;
import org.urlMonitor.service.UserService;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class Identity {

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
                if (auth.getAuthority().equals(UserServiceImpl.ROLE_ADMIN)) {
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

    public User getUser() {
        if (user == null) {
            user = userServiceImpl.findByEmail(getEmail());
        }
        return user;
    }

    public void refresh() {
        this.user = null;
    }

    public boolean isMaintainer(Monitor monitor) {
        return getUser().getMaintainerMonitor().contains(monitor);
    }
}
