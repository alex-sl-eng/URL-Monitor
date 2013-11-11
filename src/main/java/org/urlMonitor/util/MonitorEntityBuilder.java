package org.urlMonitor.util;

import org.urlMonitor.controller.form.MonitorForm;
import org.urlMonitor.model.Monitor;
import org.urlMonitor.model.type.VisibilityType;

public final class MonitorEntityBuilder {
    public static Monitor builderFromMonitorForm(MonitorForm monitorForm) {
        Monitor monitor = new Monitor();
        monitor.setName(monitorForm.getName());
        monitor.setDescription(monitorForm.getDescription());
        monitor.setUrl(monitorForm.getUrl());
        monitor.setCron(CronHelper.getExpressionFromDisplay(monitorForm
                .getCron()));
        monitor.setContentRegex(monitorForm.getContentRegex());
        monitor.setVisibility(VisibilityType.valueOf(monitorForm
                .getVisibility()));
        monitor.setTag(monitorForm.getTag());
        monitor.setEmailToList(monitorForm.getEmailToList());
        return monitor;
    }
}
