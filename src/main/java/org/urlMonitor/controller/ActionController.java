package org.urlMonitor.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.urlMonitor.controller.form.MonitorForm;
import org.urlMonitor.model.Monitor;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Controller
@RequestMapping(value = "/action")
public class ActionController {

    @RequestMapping(value = "/new-monitor", method = RequestMethod.GET)
    public String getNewMonitorPage(ModelMap model) {

        MonitorForm monitorForm = new MonitorForm();
        model.put("monitorForm", monitorForm);
        return "action/new-monitor";
    }

    @RequestMapping(value = "/new-monitor", method = RequestMethod.POST)
    public String onSubmitNewMonitor(
            @Valid @ModelAttribute("monitorForm") Monitor profileForm,
            BindingResult result, ModelMap model) {

        if (result.hasErrors()) {
            return "action/new-monitor";
        }

        //insert into db

        return "/home";
    }
}
