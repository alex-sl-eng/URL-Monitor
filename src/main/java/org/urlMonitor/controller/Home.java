package org.urlMonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.urlMonitor.service.UrlMonitorService;

@Controller
@RequestMapping("/")
public class Home
{
   @Autowired
   private UrlMonitorService urlMonitorService;
   
   @RequestMapping(method = RequestMethod.GET)
   public String GetIndexPage(ModelMap model)
   {
      model.addAttribute("monitorList", urlMonitorService.getMonitorList());
      return "index";
   }
}
