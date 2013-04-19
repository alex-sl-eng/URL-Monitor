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

package org.aeng.urlMonitor.client.view;

import java.util.Map;

import org.aeng.urlMonitor.client.NameTokens;
import org.aeng.urlMonitor.client.presenter.JobListPresenter.MyView;
import org.aeng.urlMonitor.client.presenter.ProductPresenter;
import org.aeng.urlMonitor.shared.model.UrlMonitor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

/**
 * 
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 * 
 */
public class JobListView extends ViewImpl implements MyView
{

   interface JobListViewUiBinder extends UiBinder<Widget, JobListView>
   {
   }

   private static JobListViewUiBinder uiBinder = GWT.create(JobListViewUiBinder.class);

   @UiField
   Hyperlink backLink;

   @UiField
   FlowPanel jobList;

   @UiField
   HeadingElement title;

   private final PlaceManager placeManager;

   private final Widget widget;

   @Inject
   public JobListView(PlaceManager placeManager)
   {
      widget = uiBinder.createAndBindUi(this);
      this.placeManager = placeManager;
   }

   @Override
   public Widget asWidget()
   {
      return widget;
   }

   @Override
   public void setBackLinkHistoryToken(String historyToken)
   {
      backLink.setTargetHistoryToken(historyToken);
   }
  
   @Override
   public void setMessage(String string)
   {
      jobList.clear();
      jobList.add(new Label(string));
   }

   @Override
   public void setTitle(String title)
   {
      this.title.setInnerHTML(title);
   }

   @Override
   public void setMyJobList(Map<Long, UrlMonitor> myJobList)
   {
      jobList.clear();
      for (UrlMonitor urlMonitor: myJobList.values())
      {
         PlaceRequest request = new PlaceRequest(NameTokens.job).with(ProductPresenter.TOKEN_ID, Integer.toString(urlMonitor.getId().intValue()));
         jobList.add(new Hyperlink(urlMonitor.getName(), placeManager.buildRelativeHistoryToken(request)));
      }

   }

   @Override
   public void setPublicJobList(Map<Long, UrlMonitor> publicJobList)
   {
      jobList.clear();
      for (UrlMonitor urlMonitor: publicJobList.values())
      {
         PlaceRequest request = new PlaceRequest(NameTokens.job).with(ProductPresenter.TOKEN_ID, Integer.toString(urlMonitor.getId().intValue()));
         jobList.add(new Hyperlink(urlMonitor.getName(), placeManager.buildRelativeHistoryToken(request)));
      }
   }
}