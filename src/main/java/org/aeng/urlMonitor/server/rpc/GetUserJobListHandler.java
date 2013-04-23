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
import org.aeng.urlMonitor.shared.GetUserJobListAction;
import org.aeng.urlMonitor.shared.GetUserJobListResult;
import org.aeng.urlMonitor.shared.model.UrlMonitor;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetUserJobListHandler implements ActionHandler<GetUserJobListAction, GetUserJobListResult>
{
   private final DBService dbService;

   @Inject
   public GetUserJobListHandler(DBService dbService)
   {
      this.dbService = dbService;
   }

   @Override
   public GetUserJobListResult execute(GetUserJobListAction action, ExecutionContext context) throws ActionException
   {
      HashMap<Long, UrlMonitor> myJobMap = new HashMap<Long, UrlMonitor>();
      myJobMap = dbService.getMyJobList(action.getAccountId());
      return new GetUserJobListResult(myJobMap);
   }

   @Override
   public Class<GetUserJobListAction> getActionType()
   {
      return GetUserJobListAction.class;
   }

   @Override
   public void undo(GetUserJobListAction action, GetUserJobListResult result, ExecutionContext context) throws ActionException
   {
   }
}
