package org.urlMonitor.component;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class ContentReaderManager
{
   private Map<Long, ContentReader> readerList = new HashMap<Long, ContentReader>();

   public ContentReader getOrCreateContentReader(Long id, String url)
   {
      if (readerList.containsKey(id))
      {
         return readerList.get(id);
      }
      else
      {
         return registerNewContentReader(id, url);
      }
   }

   private ContentReader registerNewContentReader(Long id, String url)
   {
      ContentReader newReader = new ContentReaderImpl(url);
      readerList.put(id, newReader);
      
      return newReader;
   }
}
