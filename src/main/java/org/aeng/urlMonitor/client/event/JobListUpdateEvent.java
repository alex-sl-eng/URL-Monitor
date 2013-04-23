/**
 * 
 */
package org.aeng.urlMonitor.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * 
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 * 
 */
public class JobListUpdateEvent extends GwtEvent<JobListUpdateEventHandler>
{
   public static Type<JobListUpdateEventHandler> TYPE = new Type<JobListUpdateEventHandler>();

   private boolean isUserJob;
   
   
   public JobListUpdateEvent(boolean isUserJob)
   {
      this.isUserJob = isUserJob;  
   }

   @Override
   public com.google.gwt.event.shared.GwtEvent.Type<JobListUpdateEventHandler> getAssociatedType()
   {
      return TYPE;
   }

   @Override
   protected void dispatch(JobListUpdateEventHandler handler)
   {
      handler.onJobListUpdate(this);
   }

   public boolean isUserJob()
   {
      return isUserJob;
   }
}
