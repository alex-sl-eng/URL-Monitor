package org.aeng.urlMonitor.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author aeng
 *
 */
public class Service implements IsSerializable
{
   private String description;
   private String name;
   
   private String url;
   private int visibility;
   private String regexcCheck;

   private String emailToIfFail;
   
   private String currentStatus;
   
   private boolean enabled;
   
   
   
}
