package org.aeng.urlMonitor.server.service;

import java.net.UnknownHostException;

import org.aeng.urlMonitor.server.exception.DatabaseException;
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
         mongoClient = new MongoClient(host , Integer.parseInt(port));

         DB db = mongoClient.getDB(name);
//         DBCollection collection = db.getCollection("yourCollection");
//
//         // create a document to store key and value
//         BasicDBObject document = new BasicDBObject();
//         document.put("id", 1001);
//         document.put("msg", "hello world mongoDB in Java");
//
//         // search query
//         BasicDBObject searchQuery = new BasicDBObject();
//         searchQuery.put("id", 1001);
//
//         // query it
//         DBCursor cursor = collection.find(searchQuery);

         // loop over the cursor and display the retrieved result
//         while (cursor.hasNext())
//         {
//            System.out.println(cursor.next());
//         }
      }
      catch (UnknownHostException e)
      {
         throw new DatabaseException("Error establishing database connection -" + host + ":" + port, e);
      }
   }
}
