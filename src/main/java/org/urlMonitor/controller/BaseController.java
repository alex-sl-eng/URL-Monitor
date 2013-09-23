package org.urlMonitor.controller;

import org.urlMonitor.util.CronHelper;
import org.urlMonitor.util.DateUtil;

import lombok.Getter;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
public abstract class BaseController
{
   @Getter
   private final CronHelper cronHelper = new CronHelper();

   @Getter
   private final DateUtil dateUtil = new DateUtil();
}
