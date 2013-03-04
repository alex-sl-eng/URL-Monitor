/**
 * 
 */
package org.aeng.urlMonitor.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aeng.urlMonitor.server.exception.DatabaseException;
import org.aeng.urlMonitor.server.exception.LoadApplicationConfigException;
import org.aeng.urlMonitor.server.service.quartz.CronTrigger;
import org.aeng.urlMonitor.shared.model.Account;
import org.aeng.urlMonitor.shared.model.UrlMonitor;
import org.aeng.urlMonitor.shared.model.type.AccessType;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author aeng Alex Eng - loones1595@gmail.com
 * 
 */
@Singleton
public class UrlMonitorService
{
   private final Logger logger = LoggerFactory.getLogger(UrlMonitorService.class);

   // this is client side logging
   // public final Logger logger =
   // Logger.getLogger(UrlMonitorService.class.getName());

   private CronTrigger cronTrigger;

   private List<UrlMonitor> urlMonitorList = new ArrayList<UrlMonitor>();
   private Map<JobKey, UrlMonitor> urlMonitorMap = new HashMap<JobKey, UrlMonitor>();

   @Inject
   private DBService dbService;

   public void init()
   {
      try
      {
         logger.info("======URL Monitor========");
         initApplicationConfig();
         initJobs();
      }
      catch (LoadApplicationConfigException e)
      {
         logger.error(e.getMessage());
      }
      catch (DatabaseException e)
      {
         logger.error(e.getMessage());
      }
      catch (SchedulerException e)
      {
         logger.error(e.getMessage());
      }
   }

   private void initApplicationConfig() throws LoadApplicationConfigException
   {
      logger.info("initialising application config... ");
      ApplicationConfigFactory.init();
   }

   private void initDBConnection() throws DatabaseException
   {
      logger.info("initialising database connection...");
      dbService.loadConnection();
   }

   private void initJobs() throws DatabaseException, SchedulerException
   {
      logger.info("initialising jobs...");
      initDummyJob();
//      initDBConnection();

      cronTrigger = new CronTrigger();

      for (UrlMonitor urlMonitor : urlMonitorList)
      {
         JobKey jobKey = cronTrigger.scheduleMonitor(urlMonitor);
         urlMonitorMap.put(jobKey, urlMonitor);
      }

   }

   private void initDummyJob()
   {
      for (int i = 0; i < 2; i++)
      {
         UrlMonitor urlMonitor = new UrlMonitor();
         urlMonitor.setName("Name:" + i);
         urlMonitor.setDescription("dummy job");
         urlMonitor.setUrl("http://google.com");
         urlMonitor.setCreatedDate(new Date());
         urlMonitor.setAccess(AccessType.Public);
         urlMonitor.setEmailTo("loones1595@gmail.com");
         urlMonitor.setContentRegex("google");

         Account DummyAccount = new Account();
         DummyAccount.setActivate(true);
         DummyAccount.setUsername("dummy person");

         urlMonitor.setCreatedBy(DummyAccount);

         urlMonitorList.add(urlMonitor);
      }
   }

}
