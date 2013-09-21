package org.urlMonitor.controller;

import org.urlMonitor.util.*;

import lombok.*;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
public class BaseController
{
   @Getter
   private final CronHelper cronHelper = new CronHelper();

   @Getter
   private final DateUtil dateUtil = new DateUtil();
}
