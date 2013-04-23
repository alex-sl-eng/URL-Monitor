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

import java.util.HashMap;
import java.util.Map.Entry;

import org.aeng.urlMonitor.client.presenter.DashboardPresenter.MyView;
import org.aeng.urlMonitor.shared.model.UrlMonitor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class DashboardView extends ViewWithUiHandlers<DashboardUiHandlers> implements MyView
{

   interface DashboardViewUiBinder extends UiBinder<Widget, DashboardView>
   {
   }

   private static DashboardViewUiBinder uiBinder = GWT.create(DashboardViewUiBinder.class);

   @UiField
   TabPanel jobsContainer;

   ScrollPanel publicJobContainer;

   ScrollPanel userJobContainer;

   private final Widget widget;

   @Inject
   public DashboardView()
   {
      widget = uiBinder.createAndBindUi(this);
      
      publicJobContainer = new ScrollPanel();
      userJobContainer = new ScrollPanel();
      
      jobsContainer.add(publicJobContainer, "Public");
      jobsContainer.add(userJobContainer, "My");
   }

   @UiHandler("jobsContainer")
   public void onSelectionChanged(SelectionEvent<Integer> event)
   {
      if (event.getSelectedItem() == 0)
      {
         getUiHandlers().revealPublicJobsList();
      }
      else
      {
         getUiHandlers().revealUserJobsList();
      }
   }

   @Override
   public Widget asWidget()
   {
      return widget;
   }

   @Override
   public void initPublicJobs(HashMap<Long, UrlMonitor> publicJobMap)
   {
      publicJobContainer.clear();
      for(Entry<Long, UrlMonitor> entry: publicJobMap.entrySet())
      {
         publicJobContainer.add(new Label(entry.getValue().getName()));
      }
   }

   @Override
   public void initUserJobs(HashMap<Long, UrlMonitor> userJobMap)
   {
      userJobContainer.clear();
      for (Entry<Long, UrlMonitor> entry : userJobMap.entrySet())
      {
         userJobContainer.add(new Label(entry.getValue().getName()));
      }
   }
}