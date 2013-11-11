/**
 * 
 */
package org.urlMonitor.service.Impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.urlMonitor.component.dao.MonitorDAO;
import org.urlMonitor.component.quartz.CronTrigger;
import org.urlMonitor.controller.form.MonitorForm;
import org.urlMonitor.events.MonitorUpdateEvent;
import org.urlMonitor.model.FailedStates;
import org.urlMonitor.model.Monitor;
import org.urlMonitor.model.MonitorInfo;
import org.urlMonitor.model.User;
import org.urlMonitor.model.type.StatusType;
import org.urlMonitor.service.AppConfiguration;
import org.urlMonitor.service.EmailService;
import org.urlMonitor.service.UrlMonitorService;
import org.urlMonitor.util.MonitorEntityBuilder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * @author Alex Eng(aeng) loones1595@gmail.com
 * 
 */
@Service
@Slf4j
public class UrlMonitorServiceImpl implements UrlMonitorService {

    @Autowired
    private AppConfiguration appConfiguration;

    @Autowired
    private EmailService emailServiceImpl;

    @Autowired
    private MonitorDAO monitorDAO;

    private Map<Long, Monitor> monitorMap = Maps.newHashMap();
    private Map<Long, FailedStates> monitorFailedMap = Maps.newHashMap();

    @PostConstruct
    public void init() throws SchedulerException {
        log.info("==================================================");
        log.info("================= URL Monitor ====================");
        log.info("==================================================");

        log.info("Initialising jobs...");

        List<Monitor> monitorList = monitorDAO.getAllActiveMonitor();
        CronTrigger cronTrigger = new CronTrigger();
        for (Monitor monitor : monitorList) {
            if (cronTrigger.scheduleMonitor(monitor)) {
                monitorMap.put(monitor.getId(), monitor);
            }
        }
        log.info("Initialised {0} jobs.", monitorList.size());
    }

    public Set<Monitor> getPublicMonitorList() {
        return Sets.newHashSet(monitorMap.values());
    }

    public Set<Monitor> getUserMonitorList(@NonNull String email) {
        return Sets.newHashSet(monitorDAO.getUserMonitor(email));
    }

    @Override
    public Set<Monitor> getPublicMonitorList(String filterText) {
        Set<Monitor> list = getPublicMonitorList();
        if (StringUtils.isEmpty(filterText)) {
            return list;
        }

        Set<Monitor> filteredList = Sets.newHashSet();
        String[] filters = filterText.split(";");

        for (Monitor monitor : list) {
            if (isMatchTagOrName(monitor.getName(), monitor.getTagList(),
                    filters)) {
                filteredList.add(monitor);
            }
        }
        return filteredList;
    }

    @Override
    public Set<Monitor> getPrivateMonitorList(String filterText, String email) {
        Set<Monitor> list = getUserMonitorList(email);
        if (StringUtils.isEmpty(filterText)) {
            return list;
        }

        Set<Monitor> filteredList = Sets.newHashSet();
        String[] filters = filterText.split(";");

        for (Monitor monitor : list) {
            if (isMatchTagOrName(monitor.getName(), monitor.getTagList(),
                    filters)) {
                filteredList.add(monitor);
            }
        }
        return filteredList;
    }

    @Override
    public List<MonitorInfo> getPublicMonitorInfoList() {
        List<MonitorInfo> result = Lists.newArrayList();
        for (Monitor monitor : getPublicMonitorList()) {
            MonitorInfo info =
                    new MonitorInfo(monitor.hashCode(), monitor.getStatus(),
                            monitor.getLastChanged(), monitor.getLastFailed());
            result.add(info);
        }
        return result;
    }

    private boolean isMatchTagOrName(String name, List<String> tags,
            String[] filterTextList) {
        for (String filterText : filterTextList) {
            if (name.contains(filterText)) {
                return true;
            } else {
                for (String tag : tags) {
                    if (tag.contains(filterText)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void onApplicationEvent(MonitorUpdateEvent event) {
        if (monitorMap.containsKey(event.getId())) {
            Monitor monitor = monitorMap.get(event.getId());
            monitor.update(event.getStatus());

            try {
                updateStates(monitor);
            } catch (EmailException e) {
                log.error("Unable to send notification email-" + e);
            }
        }
    }

    /**
     * retry MAX_RETRY_COUNT times if failed or unknown, then send email
     * 
     * @param monitor
     * @throws org.apache.commons.mail.EmailException
     */
    private void updateStates(Monitor monitor) throws EmailException {
        Long id = monitor.getId();
        if (monitor.getStatus() == StatusType.Pass) {
            if (monitorFailedMap.containsKey(id)
                    && monitorFailedMap.get(id).getCount() >= appConfiguration
                            .getRetryCount()) {
                monitorFailedMap.remove(id);
                emailServiceImpl.sendSuccessEmail(monitor,
                        monitor.getLastChanged());
            }
        } else if (monitor.getStatus() == StatusType.Unknown
                || monitor.getStatus() == StatusType.Failed) {
            if (monitorFailedMap.containsKey(id)) {
                FailedStates failedStates = monitorFailedMap.get(id);
                failedStates.addCount();
                if (failedStates.getCount() == appConfiguration.getRetryCount()) {
                    log.info("Failed check max-" + monitor.getUrl());
                    emailServiceImpl.sendFailedEmail(monitor,
                            monitor.getLastChanged());
                }
            } else {
                monitorFailedMap.put(id, new FailedStates());
            }
        }
    }

    @Override
    public void createMonitor(MonitorForm monitorForm, User maintainer) {
        Monitor monitor =
                MonitorEntityBuilder.builderFromMonitorForm(monitorForm);
        monitor.addMaintainer(maintainer);

        monitorDAO.saveOrUpdate(monitor);
        monitorMap.put(monitor.getId(), monitor);
    }
}
