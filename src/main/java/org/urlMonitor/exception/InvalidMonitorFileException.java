package org.urlMonitor.exception;

public class InvalidMonitorFileException extends Exception
{
   private static final long serialVersionUID = 1L;

   public InvalidMonitorFileException(String message, Throwable e)
   {
      super(message, e);
   }

   public InvalidMonitorFileException(String message)
   {
      super(message);
   }
}
