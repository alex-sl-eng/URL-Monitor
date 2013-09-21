package org.urlMonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.urlMonitor.service.AppConfiguration;

@Controller
public class PageController extends BaseController
{
   @Autowired
   private AppConfiguration appConfiguration;
   
   @RequestMapping(value = "/about", method = RequestMethod.GET)
   public String getAboutPage(ModelMap model)
   {
      model.addAttribute("replyTo", appConfiguration.getEmailReplyTo());
      model.addAttribute("buildVersion", appConfiguration.getBuildVersion());
      model.addAttribute("buildDate", appConfiguration.getBuildDate());
      return "about";
   }
}
