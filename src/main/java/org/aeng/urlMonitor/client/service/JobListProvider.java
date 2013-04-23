/**
 * 
 */
package org.aeng.urlMonitor.client.service;

import java.util.HashMap;

import org.aeng.urlMonitor.client.event.JobListUpdateEvent;
import org.aeng.urlMonitor.client.event.NotificationEvent;
import org.aeng.urlMonitor.client.event.NotificationEvent.Severity;
import org.aeng.urlMonitor.shared.GetPublicJobListAction;
import org.aeng.urlMonitor.shared.GetPublicJobListResult;
import org.aeng.urlMonitor.shared.GetUserJobListAction;
import org.aeng.urlMonitor.shared.GetUserJobListResult;
import org.aeng.urlMonitor.shared.model.UrlMonitor;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;

/**
 * 
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 * 
 */
@Singleton
public class JobListProvider
{
   private final DispatchAsync dispatcher;
   private final EventBus eventBus;

   private HashMap<Long, UrlMonitor> userJobMap;
   private HashMap<Long, UrlMonitor> publicJobMap;

   @Inject
   public JobListProvider(final DispatchAsync dispatcher, final EventBus eventBus)
   {
      this.dispatcher = dispatcher;
      this.eventBus = eventBus;
   }

   public void initUserJob(Long userId)
   {
      dispatcher.execute(new GetUserJobListAction(new Long(userId)), new AsyncCallback<GetUserJobListResult>()
      {
         @Override
         public void onFailure(Throwable caught)
         {
            eventBus.fireEvent(new NotificationEvent(Severity.Error, "Unable to get user job list"));
         }

         @Override
         public void onSuccess(GetUserJobListResult result)
         {
            userJobMap = result.getUserJobMap();
            eventBus.fireEvent(new JobListUpdateEvent(true));
         }
      });
   }
   
   public void initPublicJob()
   {
      dispatcher.execute(new GetPublicJobListAction(), new AsyncCallback<GetPublicJobListResult>()
      {
         @Override
         public void onFailure(Throwable caught)
         {
            eventBus.fireEvent(new NotificationEvent(Severity.Error, "Unable to get public job list"));
         }

         @Override
         public void onSuccess(GetPublicJobListResult result)
         {
            publicJobMap = result.getPublicJobMap();
            eventBus.fireEvent(new JobListUpdateEvent(false));
         }
      });
   }

   public HashMap<Long, UrlMonitor> getUserJobMap()
   {
      return userJobMap;
   }

   public HashMap<Long, UrlMonitor> getPublicJobMap()
   {
      return publicJobMap;
   }
}
