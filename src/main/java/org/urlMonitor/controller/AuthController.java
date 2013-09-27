package org.urlMonitor.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.urlMonitor.component.Identity;
import org.urlMonitor.service.UserService;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Controller
@RequestMapping(value = "/auth")
@Slf4j
public class AuthController extends BaseController
{
   @Autowired
   private Identity identity;

   @Autowired
   private UserService userService;

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

   @RequestMapping(value = "/redirect", method = RequestMethod.GET)
   public String getRedirect(ModelMap model)
   {
      if (!userService.isDetailsComplete(identity.getUsername()))
      {
         return "auth/edit_profile";
      }
      else
      {
         return super.gotoIndexPage("", model);
      }
   }
}
