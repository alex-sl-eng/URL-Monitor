package org.urlMonitor.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.urlMonitor.model.*;
import org.urlMonitor.service.*;

@Controller
public class HomeController
{
   @Autowired
   private UrlMonitorService urlMonitorService;

   @RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
   public String getIndexPage()
   {
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
      return "view/content";
   }
}
