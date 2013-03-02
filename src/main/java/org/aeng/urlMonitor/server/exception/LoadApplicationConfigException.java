package org.aeng.urlMonitor.server.exception;

import java.io.IOException;

public class LoadApplicationConfigException extends IOException
{
   private static final long serialVersionUID = 1L;
   
   public LoadApplicationConfigException(String message, Throwable e)
   {
      super(message, e);
   }

   public LoadApplicationConfigException(String message)
   {
      super(message);
   }

}
