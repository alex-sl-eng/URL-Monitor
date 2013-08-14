package org.aeng.urlMonitor.exception;

public class HttpReadContentException extends Exception
{
   private static final long serialVersionUID = 1L;

   public HttpReadContentException(String message, Throwable e)
   {
      super(message, e);
   }

   public HttpReadContentException(String message)
   {
      super(message);
   }
}
