package org.urlMonitor.model;

import java.io.Serializable;

import lombok.Getter;

public class FailedStates implements Serializable
{
   private static final long serialVersionUID = 1L;

   @Getter
   private int count = 0;

   public void addCount()
   {
      count++;
   }
}
