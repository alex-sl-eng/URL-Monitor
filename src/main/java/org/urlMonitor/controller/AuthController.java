package org.urlMonitor.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Controller
@RequestMapping(value = "/auth")
@Slf4j
public class AuthController
{
   @RequestMapping(value = "/login", method = RequestMethod.GET)
   public String getLoginPage(@RequestParam(value = "error", required = false) boolean error, ModelMap model)
   {
      if (error)
      {
         model.put("error", "Please enter valid username or password!");
      }
      else
      {
         model.put("error", "");
      }
      return "auth/login";
   }

   @RequestMapping(value = "/denied", method = RequestMethod.GET)
   public String getDeniedPage()
   {
      log.warn("Received request to show denied page");
      return "auth/denied";
   }
}
