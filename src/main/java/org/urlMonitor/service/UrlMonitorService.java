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
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang3.ArrayUtils;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.urlMonitor.model.Monitor;
import org.urlMonitor.service.quartz.CronTrigger;

/**
 * @author aeng Alex Eng - loones1595@gmail.com
 * 
 */
@Service
@Slf4j
public class UrlMonitorService
{
   @Value("${properties.path}")
   private String dirPath;

   private final static String REGEX_PROPERTIES = "*.properties";

   private CronTrigger cronTrigger;
   private Map<JobKey, Monitor> urlMonitorMap = new HashMap<JobKey, Monitor>();

   @PostConstruct
   public void init() throws SchedulerException, FileNotFoundException, IOException
   {
      log.info("==================================================");
      log.info("================= URL Monitor ====================");
      log.info("==================================================");
                                                 
      initJobs();
   }

   private void initJobs() throws SchedulerException, FileNotFoundException, IOException
   {
      log.info("Initialising jobs...");

      cronTrigger = new CronTrigger();

      for (Monitor monitor : loadResourceFiles())
      {
         JobKey jobKey = cronTrigger.scheduleMonitor(monitor);
         if (jobKey != null)
         {
            urlMonitorMap.put(jobKey, monitor);
         }
      }
   }

   private List<Monitor> loadResourceFiles() throws FileNotFoundException, IOException
   {
      List<Monitor> result = new ArrayList<Monitor>();

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
                  result.add(new Monitor(prop));
               }
            }
         }
      }
      return result;
   }

   public List<Monitor> getMonitorList()
   {
      return new ArrayList<Monitor>(urlMonitorMap.values());
   }
}
