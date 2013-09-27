package org.urlMonitor.controller;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.urlMonitor.util.CronHelper;
import org.urlMonitor.util.DateUtil;
import com.google.common.base.Optional;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
public abstract class BaseController
{
   @Getter
   private final CronHelper cronHelper = new CronHelper();

   @Getter
   private final DateUtil dateUtil = new DateUtil();

   protected String gotoIndexPage(String filterText, ModelMap model)
   {
      if (!StringUtils.isEmpty(filterText))
      {
         model.put("filterText", filterText);
      }
      insertUtilInSession(model);
      return "index";
   }

   protected void insertUtilInSession(ModelMap model)
   {
      model.put("cronHelper", getCronHelper());
   }
}
