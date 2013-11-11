/**
 * 
 */
package org.urlMonitor.service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationListener;
import org.springframework.transaction.annotation.Transactional;
import org.urlMonitor.controller.form.MonitorForm;
import org.urlMonitor.events.MonitorUpdateEvent;
import org.urlMonitor.model.Monitor;
import org.urlMonitor.model.MonitorInfo;
import org.urlMonitor.model.User;

/**
 * @author Alex Eng(aeng) loones1595@gmail.com
 * 
 */
@Transactional
public interface UrlMonitorService extends
        ApplicationListener<MonitorUpdateEvent> {

    public static final Comparator<Monitor> MonitorComparator =
            new Comparator<Monitor>() {
                @Override
                public int compare(Monitor o1, Monitor o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            };

    void createMonitor(MonitorForm monitorForm, User maintainer);

    List<MonitorInfo> getPublicMonitorInfoList();

    Set<Monitor> getPublicMonitorList();

    Set<Monitor> getPublicMonitorList(String filterText);

    Set<Monitor> getPrivateMonitorList(String filterText, String email);
}
