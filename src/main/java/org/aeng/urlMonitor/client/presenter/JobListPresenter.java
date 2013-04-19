/**
 * Copyright 2011 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.aeng.urlMonitor.client.presenter;

import java.util.Map;
import java.util.logging.Logger;

import org.aeng.urlMonitor.client.NameTokens;
import org.aeng.urlMonitor.client.event.JobListUpdateEvent;
import org.aeng.urlMonitor.client.event.JobListUpdateEventHandler;
import org.aeng.urlMonitor.client.service.JobListProvider;
import org.aeng.urlMonitor.shared.model.UrlMonitor;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.TitleFunction;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;


public class JobListPresenter extends Presenter<JobListPresenter.MyView, JobListPresenter.MyProxy> implements JobListUpdateEventHandler
{
   /**
    * {@link JobListPresenter}'s proxy.
    */
   @ProxyCodeSplit
   @NameToken(NameTokens.jobList)
   public interface MyProxy extends ProxyPlace<JobListPresenter>
   {
   }

   /**
    * {@link JobListPresenter}'s view.
    */
   public interface MyView extends View
   {
      void setBackLinkHistoryToken(String historyToken);

      void setMessage(String string);

      void setTitle(String title);
      
      void setMyJobList(Map<Long, UrlMonitor> myJobList);
      
      void setPublicJobList(Map<Long, UrlMonitor> publicJobList);
   }
   
   public final Logger logger = Logger.getLogger(JobListPresenter.class.getName());

   public static final String TOKEN_TYPE = "type";

   public static final String TYPE_PUBLIC_JOBS = "pub";

   public static final String TYPE_MY_JOBS = "my";

   @TitleFunction
   public static String getListTitle(PlaceRequest request)
   {
      return getTitleFor(request.getParameter(TOKEN_TYPE, null));
   }

   private static String getTitleFor(String type)
   {
      if (type.equals(TYPE_MY_JOBS))
      {
         return "My Jobs";
      }
      else
      {
         return "Public Jobs";
      }
   }

   private String currentType = TYPE_PUBLIC_JOBS;

   private final DispatchAsync dispatcher;

   private final PlaceManager placeManager;
   
   private final JobListProvider jobListProvider;

   @Inject
   public JobListPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy, final PlaceManager placeManager, final DispatchAsync dispatcher, final JobListProvider jobListProvider)
   {
      super(eventBus, view, proxy);
      this.placeManager = placeManager;
      this.dispatcher = dispatcher;
      this.jobListProvider= jobListProvider;
      
      eventBus.addHandler(JobListUpdateEvent.TYPE, this);
   }

   @Override
   public void prepareFromRequest(PlaceRequest request)
   {
      super.prepareFromRequest(request);
      String type = request.getParameter(TOKEN_TYPE, TYPE_PUBLIC_JOBS);
      if (type.equals(TYPE_MY_JOBS))
      {
         currentType = TYPE_MY_JOBS;
      }

      setViewTitle();
   }

   @Override
   protected void onReset()
   {
      super.onReset();
      getView().setMessage("Loading list...");
      getView().setBackLinkHistoryToken(placeManager.buildRelativeHistoryToken(-1));
      
      jobListProvider.initMyJob();
   }

   @Override
   protected void revealInParent()
   {
      RevealContentEvent.fire(this, BreadcrumbsPresenter.TYPE_SetMainContent, this);
   }

   private void setViewTitle()
   {
      getView().setTitle(getTitleFor(currentType));
   }

   @Override
   public void onJobListUpdate(JobListUpdateEvent event)
   {
      if(event.isMyJob())
      {
         getView().setMyJobList(jobListProvider.getMyJobMap());
      }
      else
      {
         getView().setPublicJobList(jobListProvider.getMyJobMap());
      }
   }

}
