package org.aeng.urlMonitor.server.service;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.aeng.urlMonitor.server.exception.DatabaseException;
import org.aeng.urlMonitor.shared.model.Account;
import org.aeng.urlMonitor.shared.model.UrlMonitor;
import org.aeng.urlMonitor.shared.model.type.AccessType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Singleton;
import com.mongodb.DB;
import com.mongodb.MongoClient;

@Singleton
public class DBService
{
   private final Logger logger = LoggerFactory.getLogger(this.getClass());

   private MongoClient mongoClient;

   public void loadConnection() throws DatabaseException
   {
      String host = ApplicationConfigFactory.getDBHost();
      String port = ApplicationConfigFactory.getDBPort();
      String name = ApplicationConfigFactory.getDBName();
      logger.info("Establishing database connection: " + host + ":" + port + "/" + name);

      try
      {
         mongoClient = new MongoClient(host, Integer.parseInt(port));

         DB db = mongoClient.getDB(name);
         // DBCollection collection = db.getCollection("yourCollection");
         //
         // // create a document to store key and value
         // BasicDBObject document = new BasicDBObject();
         // document.put("id", 1001);
         // document.put("msg", "hello world mongoDB in Java");
         //
         // // search query
         // BasicDBObject searchQuery = new BasicDBObject();
         // searchQuery.put("id", 1001);
         //
         // // query it
         // DBCursor cursor = collection.find(searchQuery);

         // loop over the cursor and display the retrieved result
         // while (cursor.hasNext())
         // {
         // System.out.println(cursor.next());
         // }
      }
      catch (UnknownHostException e)
      {
         throw new DatabaseException("Error establishing database connection -" + host + ":" + port, e);
      }
   }

   public HashMap<Long, UrlMonitor> getMyJobList(Long accountId)
   {
      HashMap<Long, UrlMonitor> result = new HashMap<Long, UrlMonitor>();
      
      for(UrlMonitor urlMonitor: loadAllJobs())
      {
         if(urlMonitor.getCreatedBy().getId().equals(accountId))
         {
            result.put(urlMonitor.getId(), urlMonitor);
         }
      }
      return result;
   }
   
   public HashMap<Long, UrlMonitor> getPublicJobList()
   {
      HashMap<Long, UrlMonitor> result = new HashMap<Long, UrlMonitor>();
      
      for(UrlMonitor urlMonitor: loadAllJobs())
      {
         if(urlMonitor.getAccess().equals(AccessType.Public))
         {
            result.put(urlMonitor.getId(), urlMonitor);
         }
      }
      return result;
   }
   
   public List<UrlMonitor> loadAllJobs()
   {
      return getDummyJob();
   }

   private List<UrlMonitor> getDummyJob()
   {
      List<UrlMonitor> result = new ArrayList<UrlMonitor>();

      for (int i = 0; i < 10; i++)
      {
         UrlMonitor urlMonitor = new UrlMonitor();
         urlMonitor.setId(new Long(i));
         urlMonitor.setName("Name:" + i);
         urlMonitor.setDescription("dummy job");
         urlMonitor.setUrl("http://google.com");
         urlMonitor.setCreatedDate(new Date());
         urlMonitor.setAccess(AccessType.Public);
         urlMonitor.setEmailTo("loones1595@gmail.com");
         urlMonitor.setContentRegex("google");

         Account DummyAccount = new Account();
         DummyAccount.setId(new Long(1));
         DummyAccount.setActivate(true);
         DummyAccount.setUsername("dummy person");

         urlMonitor.setCreatedBy(DummyAccount);
         result.add(urlMonitor);
      }
      return result;
   }

   
}
