package org.urlMonitor.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.urlMonitor.component.Identity;
import org.urlMonitor.service.UserService;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Controller
@RequestMapping(value = "/profile")
@Slf4j
public class ProfileController extends BaseController
{
   @Autowired
   private Identity identity;

   @Autowired
   private UserService userService;

   @RequestMapping(value = "/", method = RequestMethod.GET)
   public String getMyProfile(ModelMap model)
   {
      return "profile/index";
   }

   @RequestMapping(value = "/edit", method = RequestMethod.GET)
   public String getEditProfile(ModelMap model)
   {
      return "profile/edit";
   }

   @RequestMapping(value = "/redirect", method = RequestMethod.GET)
   public String getRedirect(ModelMap model)
   {
      if (!userService.isDetailsComplete(identity.getUsername()))
      {
         return getEditProfile(model);
      }
      else
      {
         return super.gotoIndexPage("", model);
      }
   }
}
