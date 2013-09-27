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
         model.put("messages", "Invalid username or password");
      }
      else
      {
         model.put("messages", "");
      }
      return "auth/login";
   }

   @RequestMapping(value = "/denied", method = RequestMethod.GET)
   public String getDeniedPage()
   {
      return "auth/denied";
   }
}
