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

package org.aeng.urlMonitor.server.guice.module;

import org.aeng.urlMonitor.server.rpc.GetMyJobListHandler;
import org.aeng.urlMonitor.server.rpc.GetProductHandler;
import org.aeng.urlMonitor.server.rpc.GetProductListHandler;
import org.aeng.urlMonitor.server.rpc.GetPublicJobListHandler;

import com.gwtplatform.dispatch.server.guice.HandlerModule;

import org.aeng.urlMonitor.shared.GetMyJobListAction;
import org.aeng.urlMonitor.shared.GetProductAction;
import org.aeng.urlMonitor.shared.GetProductListAction;
import org.aeng.urlMonitor.shared.GetPublicJobListAction;

/**
 * Module which binds the handlers and configurations.
 *
 * @author Philippe Beaudoin
 */
public class ServerHandlerModule extends HandlerModule {

  @Override
  protected void configureHandlers() {
    bindHandler(GetProductAction.class, GetProductHandler.class);
    bindHandler(GetProductListAction.class, GetProductListHandler.class);
    
    bindHandler(GetMyJobListAction.class, GetMyJobListHandler.class);
    bindHandler(GetPublicJobListAction.class, GetPublicJobListHandler.class);
  }
}
