/**
 * 
 */
package org.urlMonitor.service;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.urlMonitor.exception.InvalidMonitorFileException;
import org.urlMonitor.model.FailedStates;
import org.urlMonitor.model.Monitor;
import org.urlMonitor.model.type.StatusType;
import org.urlMonitor.service.events.MonitorUpdateEvent;
import org.urlMonitor.service.quartz.CronTrigger;

/**
 * @author Alex Eng(aeng)  loones1595@gmail.com
 *
 */
@Service
@Scope("singleton")
@Slf4j
public class UrlMonitorService implements ApplicationListener<MonitorUpdateEvent>
{
   @Value("${monitor.files.path}")
   private String dirPath;

   @Value("${retry.count}")
   private int retryCount;

   @Autowired
   private EmailService emailService;

   private final static String REGEX_PROPERTIES = "*.properties";

   private CronTrigger cronTrigger;
   private Map<Long, Monitor> monitorMap = new HashMap<Long, Monitor>();
   private Map<Long, FailedStates> monitorFailedMap = new HashMap<Long, FailedStates>();

   @PostConstruct
   public void init() throws SchedulerException, FileNotFoundException, IOException, InvalidMonitorFileException
   {
      log.info("==================================================");
      log.info("================= URL Monitor ====================");
      log.info("==================================================");

      initJobs();
   }

   private void initJobs() throws SchedulerException, FileNotFoundException, IOException, InvalidMonitorFileException
   {
      log.info("Initialising jobs...");

      cronTrigger = new CronTrigger();

      for (Monitor monitor : loadMonitorFiles())
      {
         if (cronTrigger.scheduleMonitor(monitor))
         {
            monitorMap.put(monitor.getId(), monitor);
         }
      }
   }

   private Set<Monitor> loadMonitorFiles() throws FileNotFoundException, IOException, InvalidMonitorFileException
   {
      Set<Monitor> result = new HashSet<Monitor>();

      File dir = new File(dirPath);
      if (dir.exists())
      {
         FileFilter fileFilter = new WildcardFileFilter(REGEX_PROPERTIES);
         File[] files = dir.listFiles(fileFilter);
         if (!ArrayUtils.isEmpty(files))
         {
            for (File file : files)
            {
               if (!file.isDirectory())
               {
                  Properties prop = new Properties();
                  prop.load(new FileInputStream(file));
                  Monitor monitor = new Monitor(prop);
                  if (!result.contains(monitor)) // remove duplicate
                  {
                     result.add(new Monitor(prop));
                  }
               }
            }
         }
      }
      else
      {
         log.warn("Monitor files not found {0}", dirPath);
      }
      return result;
   }

   public List<Monitor> getMonitorList()
   {
      return new ArrayList<Monitor>(monitorMap.values());
   }

   public List<Monitor> getMonitorList(String filterText)
   {
      List<Monitor> list = getMonitorList();
      if (StringUtils.isEmpty(filterText))
      {
         return list;
      }

      List<Monitor> filteredList = new ArrayList<Monitor>();
      String[] filters = filterText.split(";");

      for (Monitor monitor : list)
      {
         if (isMatchTagOrName(monitor.getName(), monitor.getTag(), filters))
         {
            filteredList.add(monitor);
         }
      }
      return filteredList;
   }

   private boolean isMatchTagOrName(String name, List<String> tags, String[] filterTextList)
   {
      for (String filterText : filterTextList)
      {
         if (name.contains(filterText))
         {
            return true;
         }
         else
         {
            for (String tag : tags)
            {
               if (tag.contains(filterText))
               {
                  return true;
               }
            }
         }
      }
      return false;
   }

   public void onApplicationEvent(MonitorUpdateEvent event)
   {
      if (monitorMap.containsKey(event.getId()))
      {
         Monitor monitor = monitorMap.get(event.getId());
         monitor.update(event.getStatus());

         try
         {
            updateStates(monitor);
         }
         catch (EmailException e)
         {
            log.error("Unable to send notification email-" + e);
            e.printStackTrace();
         }
      }
   }

   /**
    * retry MAX_RETRY_COUNT times if failed or unknown, then send email
    * @param monitor
    * @throws EmailException 
    */
   private void updateStates(Monitor monitor) throws EmailException
   {
      Long id = monitor.getId();
      if (monitor.getStatus() == StatusType.Pass)
      {
         if (monitorFailedMap.remove(id) != null)
         {
            emailService.sendSuccessEmail(monitor, monitor.getLastCheck());
         }
      }
      else if (monitor.getStatus() == StatusType.Unknown || monitor.getStatus() == StatusType.Failed)
      {
         if (monitorFailedMap.containsKey(id))
         {
            FailedStates failedStates = monitorFailedMap.get(id);
            failedStates.addCount();
            if (failedStates.getCount() == retryCount)
            {
               emailService.sendFailedEmail(monitor, monitor.getLastCheck());
            }
         }
         else
         {
            monitorFailedMap.put(id, new FailedStates());
         }
      }
   }
}
