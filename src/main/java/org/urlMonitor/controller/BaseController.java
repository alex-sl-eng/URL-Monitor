package org.urlMonitor.controller;

import lombok.Getter;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;
import org.urlMonitor.model.type.SeverityType;
import org.urlMonitor.util.CronHelper;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
public abstract class BaseController {
    @Getter
    private final CronHelper cronHelper = new CronHelper();

    protected void insertUtilInSession(ModelMap model) {
        model.put("cronHelper", getCronHelper());
    }

    protected void addMessages(SeverityType severity, String message,
            ModelMap model) {
        model.put("messages", message);
        model.put("severity", severity);
    }

    protected void clearMessages(ModelMap model) {
        model.remove("messages");
        model.remove("severity");
    }

    protected boolean isUserLoggedIn() {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return true;
        }
        return false;
    }

    protected UserDetails getUserDetails() {
        if (isUserLoggedIn()) {
            return (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
        }
        return null;
    }
}
