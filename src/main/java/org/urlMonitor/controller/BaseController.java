package org.urlMonitor.controller;

import lombok.Getter;

import org.springframework.ui.ModelMap;
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

    protected void addMessages(String severity, String message, ModelMap model) {
        model.put("messages", message);
        model.put("severity", severity);
    }

    protected void clearMessages(ModelMap model) {
        model.remove("messages");
        model.remove("severity");
    }
}
