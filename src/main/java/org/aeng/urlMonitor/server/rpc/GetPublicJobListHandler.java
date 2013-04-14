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

package org.aeng.urlMonitor.server.rpc;

import java.util.HashMap;

import org.aeng.urlMonitor.server.service.DBService;
import org.aeng.urlMonitor.shared.GetPublicJobListAction;
import org.aeng.urlMonitor.shared.GetPublicJobListResult;
import org.aeng.urlMonitor.shared.model.UrlMonitor;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetPublicJobListHandler implements ActionHandler<GetPublicJobListAction, GetPublicJobListResult>
{
   private final DBService dbService;

   @Inject
   public GetPublicJobListHandler(DBService dbService)
   {
      this.dbService = dbService;
   }

   @Override
   public GetPublicJobListResult execute(GetPublicJobListAction action, ExecutionContext context) throws ActionException
   {
      HashMap<Long, UrlMonitor> jobMap = new HashMap<Long, UrlMonitor>();
      jobMap = dbService.getPublicJobList();
      return new GetPublicJobListResult(jobMap);
   }

   @Override
   public Class<GetPublicJobListAction> getActionType()
   {
      return GetPublicJobListAction.class;
   }

   @Override
   public void undo(GetPublicJobListAction action, GetPublicJobListResult result, ExecutionContext context) throws ActionException
   {
   }
}
