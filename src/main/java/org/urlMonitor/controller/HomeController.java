package org.urlMonitor.controller;

import java.util.*;

import org.apache.commons.lang3.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.urlMonitor.model.*;
import org.urlMonitor.service.*;
import org.urlMonitor.util.*;

@Controller
public class HomeController extends BaseController
{
   @Autowired
   private UrlMonitorService urlMonitorService;

   @RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
   public String getIndexPage(@RequestParam(required = false) String filterText, ModelMap model)
   {
      if(!StringUtils.isEmpty(filterText))
      {
         model.put("filterText", filterText);
      }
      model.put("cronHelper", getCronHelper());
      return "index";
   }

   @RequestMapping(value = "/updateStatus", method = RequestMethod.GET)
   public @ResponseBody List<Monitor> refreshPage()
   {
      return urlMonitorService.getMonitorList();
   }

   @RequestMapping(value = "/filterList", method = RequestMethod.GET)
   public String filterList(@RequestParam(required = false) String filterText, ModelMap model)
   {
      model.addAttribute("monitorList", urlMonitorService.getMonitorList(filterText));
      model.put("cronHelper", getCronHelper());
      return "view/content";
   }
}
