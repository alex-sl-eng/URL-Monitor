package org.urlMonitor.controller;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;
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
}
