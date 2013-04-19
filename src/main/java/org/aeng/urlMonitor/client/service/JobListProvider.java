/**
 * 
 */
package org.aeng.urlMonitor.client.service;

import java.util.HashMap;

import org.aeng.urlMonitor.client.event.JobListUpdateEvent;
import org.aeng.urlMonitor.client.event.NotificationEvent;
import org.aeng.urlMonitor.client.event.NotificationEvent.Severity;
import org.aeng.urlMonitor.shared.GetMyJobListAction;
import org.aeng.urlMonitor.shared.GetMyJobListResult;
import org.aeng.urlMonitor.shared.GetPublicJobListAction;
import org.aeng.urlMonitor.shared.GetPublicJobListResult;
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

   private HashMap<Long, UrlMonitor> myJobMap;
   private HashMap<Long, UrlMonitor> publicJobMap;

   @Inject
   public JobListProvider(final DispatchAsync dispatcher, final EventBus eventBus)
   {
      this.dispatcher = dispatcher;
      this.eventBus = eventBus;
   }

   public void initMyJob()
   {
      dispatcher.execute(new GetMyJobListAction(new Long(1)), new AsyncCallback<GetMyJobListResult>()
      {
         @Override
         public void onFailure(Throwable caught)
         {
            eventBus.fireEvent(new NotificationEvent(Severity.Error, "Unable to get user job list"));
         }

         @Override
         public void onSuccess(GetMyJobListResult result)
         {
            myJobMap = result.getMyJobMap();
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

   public HashMap<Long, UrlMonitor> getMyJobMap()
   {
      return myJobMap;
   }

   public HashMap<Long, UrlMonitor> getPublicJobMap()
   {
      return publicJobMap;
   }
}
