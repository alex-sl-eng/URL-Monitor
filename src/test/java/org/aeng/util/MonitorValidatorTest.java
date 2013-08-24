package org.aeng.util;

import java.util.Properties;

import org.junit.Test;
import org.urlMonitor.exception.InvalidMonitorFileException;
import org.urlMonitor.model.Monitor;
import org.urlMonitor.util.MonitorValidator;

public class MonitorValidatorTest
{
   @Test(expected = InvalidMonitorFileException.class)  
   public void testEmptyMandatoryFields() throws InvalidMonitorFileException
   {
      MonitorValidator.isMandatoryFieldsPresent(buildMonitor("", "", ""));
   }
   
   @Test(expected = InvalidMonitorFileException.class)  
   public void testEmptyNameFields() throws InvalidMonitorFileException
   {
      MonitorValidator.validateNameRegexAndTag(buildMonitor("", "http://localhost", "0/5 * * * * ?"));
   }
   
   @Test(expected = InvalidMonitorFileException.class)  
   public void testInvalidUrlFields() throws InvalidMonitorFileException
   {
      MonitorValidator.validateNameRegexAndTag(buildMonitor("name", "httplocalhost", "0/5 * * * * ?"));
   }
   
   @Test(expected = InvalidMonitorFileException.class)  
   public void testInvalidCronFields() throws InvalidMonitorFileException
   {
      MonitorValidator.validateNameRegexAndTag(buildMonitor("name", "http://localhost", "invalid cron"));
   }
   
   @Test
   public void testMandatoryFieldExist() throws InvalidMonitorFileException
   {
      MonitorValidator.isMandatoryFieldsPresent(buildMonitor("name", "http://localhost.com", "0/5 * * * * ?"));
   }
   
   
   private Monitor buildMonitor(String name, String url, String cronExpression) throws InvalidMonitorFileException
   {
      Properties properties = new Properties();
      properties.put("name", name);
      properties.put("url", url);
      properties.put("cronExpression", cronExpression);
      
      return new Monitor(properties);
   }
}
