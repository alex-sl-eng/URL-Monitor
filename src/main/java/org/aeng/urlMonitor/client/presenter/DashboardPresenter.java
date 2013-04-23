package org.aeng.urlMonitor.client.presenter;

import java.util.HashMap;

import org.aeng.urlMonitor.client.NameTokens;
import org.aeng.urlMonitor.client.event.JobListUpdateEvent;
import org.aeng.urlMonitor.client.event.JobListUpdateEventHandler;
import org.aeng.urlMonitor.client.service.JobListProvider;
import org.aeng.urlMonitor.client.view.DashboardUiHandlers;
import org.aeng.urlMonitor.shared.model.UrlMonitor;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.Title;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class DashboardPresenter extends Presenter<DashboardPresenter.MyView, DashboardPresenter.MyProxy> implements DashboardUiHandlers, JobListUpdateEventHandler
{
   /**
    * {@link DashboardPresenter}'s proxy.
    */
   @ProxyCodeSplit
   @NameToken(NameTokens.dashboard)
   @Title("Dashboard")
   public interface MyProxy extends ProxyPlace<DashboardPresenter>
   {
   }

   /**
    * {@link DashboardPresenter}'s view.
    */
   public interface MyView extends View, HasUiHandlers<DashboardUiHandlers>
   {
      void initPublicJobs(HashMap<Long, UrlMonitor> publicJobMap);

      void initUserJobs(HashMap<Long, UrlMonitor> userJobMap);
   }

   private PlaceManager placeManager;

   private final JobListProvider jobListProvider;

   @Inject
   public DashboardPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy, final PlaceManager placeManager, final JobListProvider jobListProvider)
   {
      super(eventBus, view, proxy);
      this.placeManager = placeManager;
      this.jobListProvider = jobListProvider;

      view.setUiHandlers(this);

      eventBus.addHandler(JobListUpdateEvent.TYPE, this);
   }

   @Override
   protected void revealInParent()
   {
      RevealContentEvent.fire(this, BreadcrumbsPresenter.TYPE_SetMainContent, this);
   }

   @Override
   public void revealPublicJobsList()
   {
      if (jobListProvider.getPublicJobMap().isEmpty())
      {
         jobListProvider.initPublicJob();
      }
      // placeManager.revealRelativePlace(new
      // PlaceRequest(NameTokens.jobList).with(JobListPresenter.TOKEN_TYPE,
      // JobListPresenter.TYPE_PUBLIC_JOBS));
   }

   @Override
   public void revealUserJobsList()
   {
      if (jobListProvider.getUserJobMap().isEmpty())
      {
         jobListProvider.initUserJob(new Long(1));
      }
      // placeManager.revealRelativePlace(new
      // PlaceRequest(NameTokens.jobList).with(JobListPresenter.TOKEN_TYPE,
      // JobListPresenter.TYPE_MY_JOBS));
   }

   @Override
   public void onJobListUpdate(JobListUpdateEvent event)
   {
      if (event.isUserJob())
      {
         getView().initUserJobs(jobListProvider.getUserJobMap());
      }
      else
      {
         getView().initPublicJobs(jobListProvider.getPublicJobMap());
      }
   }

}