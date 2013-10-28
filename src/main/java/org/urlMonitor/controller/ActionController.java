package org.urlMonitor.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.urlMonitor.component.MessageResource;
import org.urlMonitor.controller.form.MonitorForm;
import org.urlMonitor.model.Monitor;
import org.urlMonitor.model.type.SeverityType;
import org.urlMonitor.service.UrlMonitorService;
import org.urlMonitor.util.MonitorEntityBuilder;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Controller
@RequestMapping(value = "/action")
public class ActionController extends BaseController {

    @Autowired
    private MessageResource messageResource;

    @Autowired
    private UrlMonitorService urlMonitorServiceImpl;

    @RequestMapping(value = "/new-monitor", method = RequestMethod.GET)
    public String getNewMonitorPage(ModelMap model) {

        MonitorForm monitorForm = new MonitorForm();
        model.put("monitorForm", monitorForm);
        return "action/new-monitor";
    }

    @RequestMapping(value = "/new-monitor", method = RequestMethod.POST)
    public String onSubmitNewMonitor(
            @Valid @ModelAttribute("monitorForm") MonitorForm monitorForm,
            BindingResult result, ModelMap model) {

        if (result.hasErrors()) {
            return "action/new-monitor";
        }

        Monitor monitor =
                MonitorEntityBuilder.builderFromMonitorForm(monitorForm);

        urlMonitorServiceImpl.createMonitor(monitor);

        addMessages(
            SeverityType.info,
            messageResource.getMessage("jsp.Monitor.created",
                monitor.getName()), model);

        return "/home";
    }
}
