package org.urlMonitor.component;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class WebClientManager
{
   private Map<Long, WebClient> webClientList = new HashMap<Long, WebClient>();

   public WebClient getOrCreateWebClient(Long id, String url)
   {
      if (webClientList.containsKey(id))
      {
         return webClientList.get(id);
      }
      else
      {
         return registerNewWebClient(id, url);
      }
   }

   private WebClient registerNewWebClient(Long id, String url)
   {
      WebClient newClient = new WebClientImpl(url);
      webClientList.put(id, newClient);
      
      return newClient;
   }
}
