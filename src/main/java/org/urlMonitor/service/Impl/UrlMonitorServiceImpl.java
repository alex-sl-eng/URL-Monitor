/**
 * 
 */
package org.urlMonitor.service.Impl;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.urlMonitor.component.dao.MonitorDAO;
import org.urlMonitor.component.quartz.CronTrigger;
import org.urlMonitor.events.MonitorUpdateEvent;
import org.urlMonitor.exception.InvalidMonitorFileException;
import org.urlMonitor.model.FailedStates;
import org.urlMonitor.model.Monitor;
import org.urlMonitor.model.MonitorInfo;
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

    private final static String REGEX_PROPERTIES = "*.properties";

    private CronTrigger cronTrigger;
    private Map<Long, Monitor> monitorMap = Maps.newHashMap();
    private Map<Long, FailedStates> monitorFailedMap = Maps.newHashMap();

    @PostConstruct
    public void init() throws SchedulerException, FileNotFoundException,
            IOException {
        log.info("==================================================");
        log.info("================= URL Monitor ====================");
        log.info("==================================================");

        log.info("Initialising jobs...");

        cronTrigger = new CronTrigger();

        for (Monitor monitor : loadMonitorFiles()) {
            if (cronTrigger.scheduleMonitor(monitor)) {
                monitorMap.put(monitor.getId(), monitor);
            }
        }
    }

    private Set<Monitor> loadMonitorFiles() throws FileNotFoundException,
            IOException {
        Set<Monitor> result = Sets.newHashSet();

        File dir = new File(appConfiguration.getFilesPath());
        if (dir.exists()) {
            FileFilter fileFilter = new WildcardFileFilter(REGEX_PROPERTIES);
            File[] files = dir.listFiles(fileFilter);
            if (!ArrayUtils.isEmpty(files)) {
                for (File file : files) {
                    if (!file.isDirectory()) {
                        Properties prop = new Properties();
                        prop.load(new FileInputStream(file));
                        try {
                            Monitor monitor =
                                    MonitorEntityBuilder
                                            .buildFromProperties(prop);
                            if (!result.contains(monitor)) // remove duplicate
                            {
                                result.add(monitor);
                            }
                        } catch (InvalidMonitorFileException e) {
                            log.info("Ingoring incomplete monitor info: "
                                    + file.getName());
                        }

                    }
                }
            }
        } else {
            log.warn("Monitor files not found {0}",
                    appConfiguration.getFilesPath());
        }
        return result;
    }

    public List<Monitor> getPublicMonitorList() {
        List<Monitor> monitorList = Lists.newArrayList(monitorMap.values());
        Collections.sort(monitorList, MonitorComparator);
        return monitorList;
    }

    @Override
    public List<Monitor> getPublicMonitorList(String filterText) {
        List<Monitor> list = getPublicMonitorList();
        if (StringUtils.isEmpty(filterText)) {
            return list;
        }

        List<Monitor> filteredList = Lists.newArrayList();
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
    public List<MonitorInfo> getMonitorInfoList() {
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
    public void createMonitor(Monitor monitor) {
        monitorDAO.saveOrUpdate(monitor);
    }
}
