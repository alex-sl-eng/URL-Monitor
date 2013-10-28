/**
 * 
 */
package org.urlMonitor.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.context.ApplicationListener;
import org.urlMonitor.events.MonitorUpdateEvent;
import org.urlMonitor.model.Monitor;
import org.urlMonitor.model.MonitorInfo;

/**
 * @author Alex Eng(aeng) loones1595@gmail.com
 * 
 */
public interface UrlMonitorService extends
        ApplicationListener<MonitorUpdateEvent> {

    public static final Comparator<Monitor> MonitorComparator =
            new Comparator<Monitor>() {
                @Override
                public int compare(Monitor o1, Monitor o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            };

    void createMonitor(Monitor monitor);

    List<MonitorInfo> getMonitorInfoList();

    List<Monitor> getPublicMonitorList();

    List<Monitor> getPublicMonitorList(String filterText);
}
