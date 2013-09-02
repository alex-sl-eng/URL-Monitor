package org.urlMonitor.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.UrlValidator;
import org.urlMonitor.exception.InvalidMonitorFileException;
import org.urlMonitor.model.Monitor;

public class MonitorValidator
{
   public static void isMandatoryFieldsPresent(Monitor monitor) throws InvalidMonitorFileException
   {
      if (monitor.getId() == null || StringUtils.isEmpty(monitor.getName()) || StringUtils.isEmpty(monitor.getUrl()) 
            || monitor.getCron() == null)
      {
         throw new InvalidMonitorFileException("Missing mandatory field(s) in monitor file.");
      }
   }
   
   public static void validateNameRegexAndTag(Monitor monitor) throws InvalidMonitorFileException
   {
      if (StringUtils.containsWhitespace(monitor.getName()))
      {
         throw new InvalidMonitorFileException("Invalid name-" + monitor.getName());
      }

      UrlValidator urlValidator = new UrlValidator();
      if (!urlValidator.isValid(monitor.getUrl()))
      {
         throw new InvalidMonitorFileException("Invalid url-" + monitor.getUrl());
      }

      if (!org.quartz.CronExpression.isValidExpression(monitor.getCron().getExpression()))
      {
         throw new InvalidMonitorFileException("Invalid cron expression-" + monitor.getCron());
      }
   }
}
