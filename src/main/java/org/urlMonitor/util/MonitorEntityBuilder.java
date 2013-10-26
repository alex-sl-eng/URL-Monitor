package org.urlMonitor.util;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.UrlValidator;
import org.urlMonitor.exception.InvalidMonitorFileException;
import org.urlMonitor.model.Monitor;

public final class MonitorEntityBuilder {
    public static Monitor buildFromProperties(Properties properties)
            throws InvalidMonitorFileException {
        isMandatoryFieldsPresent(properties);
        validateNameRegexAndTag(properties);

        Monitor monitor = new Monitor();
        monitor.setName(StringUtils.trimToEmpty(properties.getProperty("name")));
        monitor.setUrl(StringUtils.trimToEmpty(properties.getProperty("url")));
        monitor.setCron(StringUtils.trimToEmpty(properties
                .getProperty("cronExpression")));
        monitor.setContentRegex(StringUtils.trimToEmpty(properties
                .getProperty("contentRegex")));
        monitor.setDescription(StringUtils.trimToEmpty(properties
                .getProperty("description")));
        monitor.setTag(StringUtils.trimToEmpty(properties.getProperty("tag")));
        monitor.setEmailToList(StringUtils.trimToEmpty(properties
                .getProperty("emailToList")));

        // TODO: remove this once loaded from db
        monitor.setId(new Long(monitor.hashCode()));
        monitor.onPersist();

        return monitor;
    }

    public static void isMandatoryFieldsPresent(Properties properties)
            throws InvalidMonitorFileException {
        String name = StringUtils.trimToEmpty(properties.getProperty("name"));
        String url = StringUtils.trimToEmpty(properties.getProperty("url"));
        CronHelper.CronType cronType =
                CronHelper.getTypeFromExpression(StringUtils
                        .trimToEmpty(properties.getProperty("cronExpression")));

        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(url)
                || cronType == null) {
            throw new InvalidMonitorFileException(
                    "Missing mandatory field(s) in monitor file.");
        }
    }

    public static void validateNameRegexAndTag(Properties properties)
            throws InvalidMonitorFileException {
        String name = StringUtils.trimToEmpty(properties.getProperty("name"));
        String url = StringUtils.trimToEmpty(properties.getProperty("url"));
        CronHelper.CronType cronType =
                CronHelper.getTypeFromExpression(StringUtils
                        .trimToEmpty(properties.getProperty("cronExpression")));

        if (StringUtils.containsWhitespace(name)) {
            throw new InvalidMonitorFileException("Invalid name-" + name);
        }

        UrlValidator urlValidator = new UrlValidator();
        if (!urlValidator.isValid(url)) {
            throw new InvalidMonitorFileException("Invalid url-" + url);
        }

        if (!org.quartz.CronExpression.isValidExpression(cronType
                .getExpression())) {
            throw new InvalidMonitorFileException("Invalid cron expression-" + cronType);
      }
   }
}
