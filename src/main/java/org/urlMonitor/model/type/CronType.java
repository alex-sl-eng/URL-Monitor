package org.urlMonitor.model.type;

import lombok.Getter;

public enum CronType
{
   THRITY_SECONDS("30 seconds", "0/30 * * * * ?"),
   ONE_MINUTE("1 minute", "1 * * * * ?"),
   FIVE_MINUTES("5 minutes", "5 * * * * ?"),
   TEN_MINUTES("10 minutes", "10 * * * * ?"),
   FIFTEEN_MINUTES("15 minutes", "15 * * * * ?");

   @Getter
   private String display;

   @Getter
   private String expression;

   CronType(String display, String expression)
   {
      this.display = display;
      this.expression = expression;
   }

   public static CronType getType(String expression)
   {
      if (expression.equals(THRITY_SECONDS.getExpression()))
      {
         return THRITY_SECONDS;
      }
      else if (expression.equals(ONE_MINUTE.getExpression()))
      {
         return ONE_MINUTE;
      }
      else if (expression.equals(FIVE_MINUTES.getExpression()))
      {
         return FIVE_MINUTES;
      }
      else if (expression.equals(TEN_MINUTES.getExpression()))
      {
         return TEN_MINUTES;
      }
      else if (expression.equals(FIFTEEN_MINUTES.getExpression()))
      {
         return FIFTEEN_MINUTES;
      }
      
      return FIVE_MINUTES;

   }
}
