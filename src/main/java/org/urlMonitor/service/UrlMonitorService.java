/**
 * 
 */
package org.urlMonitor.service;

import java.io.*;
import java.util.*;

import javax.annotation.*;

import lombok.extern.slf4j.*;

import org.apache.commons.io.filefilter.*;
import org.apache.commons.lang3.*;
import org.apache.commons.mail.*;
import org.quartz.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;
import org.urlMonitor.exception.*;
import org.urlMonitor.model.*;
import org.urlMonitor.model.type.*;
import org.urlMonitor.service.events.*;
import org.urlMonitor.service.quartz.CronTrigger;
import org.urlMonitor.util.*;

import com.google.common.collect.*;

/**
 * @author Alex Eng(aeng)  loones1595@gmail.com
 *
 */
@Service
@Scope("singleton")
@Slf4j
public class UrlMonitorService implements ApplicationListener<MonitorUpdateEvent>
{
   @Autowired
   private AppConfiguration appConfiguration;

   @Autowired
   private EmailService emailService;

   private final static String REGEX_PROPERTIES = "*.properties";

   private CronTrigger cronTrigger;
   private Map<Long, Monitor> monitorMap = Maps.newHashMap();
   private Map<Long, FailedStates> monitorFailedMap = Maps.newHashMap();

   public static final Comparator<Monitor> MonitorComparator = new Comparator<Monitor>()
   {
      @Override
      public int compare(Monitor o1, Monitor o2)
      {
         return o1.getName().compareTo(o2.getName());
      }
   };

   @PostConstruct
   public void init() throws SchedulerException, FileNotFoundException, IOException
   {
      log.info("==================================================");
      log.info("================= URL Monitor ====================");
      log.info("==================================================");

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

   private Set<Monitor> loadMonitorFiles() throws FileNotFoundException, IOException
   {
      Set<Monitor> result = Sets.newHashSet();

      File dir = new File(appConfiguration.getFilesPath());
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
                  try
                  {
                     Monitor monitor = MonitorEntityBuilder.buildFromProperties(prop);
                     if (!result.contains(monitor)) // remove duplicate
                     {
                        result.add(monitor);
                     }
                  }
                  catch (InvalidMonitorFileException e)
                  {
                     log.info("Ingoring incomplete monitor info: " + file.getName());
                  }

               }
            }
         }
      }
      else
      {
         log.warn("Monitor files not found {0}", appConfiguration.getFilesPath());
      }
      return result;
   }

   public List<Monitor> getMonitorList()
   {
      List<Monitor> monitorList = Lists.newArrayList(monitorMap.values());
      Collections.sort(monitorList, MonitorComparator);
      return monitorList;
   }

   public List<Monitor> getMonitorList(String filterText)
   {
      List<Monitor> list = getMonitorList();
      if (StringUtils.isEmpty(filterText))
      {
         return list;
      }

      List<Monitor> filteredList = Lists.newArrayList();
      String[] filters = filterText.split(";");

      for (Monitor monitor : list)
      {
         if (isMatchTagOrName(monitor.getName(), monitor.getTagList(), filters))
         {
            filteredList.add(monitor);
         }
      }
      return filteredList;
   }

   public List<MonitorInfo> getMonitorInfoList()
   {
      List<MonitorInfo> result = Lists.newArrayList();
      for (Monitor monitor : getMonitorList())
      {
         MonitorInfo info = new MonitorInfo(monitor.hashCode(), monitor.getStatus(), monitor.getLastChanged(), monitor.getLastFailed());
         result.add(info);
      }
      return result;
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
         if (monitorFailedMap.containsKey(id) && monitorFailedMap.get(id).getCount() >= appConfiguration.getRetryCount())
         {
            monitorFailedMap.remove(id);
            emailService.sendSuccessEmail(monitor, monitor.getLastChanged());
         }
      }
      else if (monitor.getStatus() == StatusType.Unknown || monitor.getStatus() == StatusType.Failed)
      {
         if (monitorFailedMap.containsKey(id))
         {
            FailedStates failedStates = monitorFailedMap.get(id);
            failedStates.addCount();
            if (failedStates.getCount() == appConfiguration.getRetryCount())
            {
               log.info("Failed check max-" + monitor.getUrl());
               emailService.sendFailedEmail(monitor, monitor.getLastChanged());
            }
         }
         else
         {
            monitorFailedMap.put(id, new FailedStates());
         }
      }
   }
}
