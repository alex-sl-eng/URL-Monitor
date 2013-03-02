package org.aeng.urlMonitor.server.exception;


public class DatabaseException extends Exception
{
   private static final long serialVersionUID = 1L;
   
   public DatabaseException(String message, Throwable e)
   {
      super(message, e);
   }

   public DatabaseException(String message)
   {
      super(message);
   }

}
