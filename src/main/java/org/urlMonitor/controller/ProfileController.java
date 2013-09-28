package org.urlMonitor.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Controller
@RequestMapping(value = "/profile")
@Slf4j
public class ProfileController extends BaseController
{
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
}
