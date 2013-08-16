package org.urlMonitor.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;

public class FailedStates implements Serializable
{
   private static final long serialVersionUID = 1L;

   @Getter
   private int count = 0;
   
   @Getter
   private Date lastFailedTime = new Date();

   public void addCount()
   {
      count++;
      lastFailedTime = new Date();
   }

   public void resetCount()
   {
      count = 0;
   }
}
