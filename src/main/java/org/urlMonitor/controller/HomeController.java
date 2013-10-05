package org.urlMonitor.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.urlMonitor.model.MonitorInfo;
import org.urlMonitor.service.UrlMonitorService;

@Controller
public class HomeController extends BaseController {

    @Autowired
    private UrlMonitorService urlMonitorService;

    @RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
    public String getIndexPage(
            @RequestParam(required = false) String filterText, ModelMap model) {
        if (!StringUtils.isEmpty(filterText)) {
            model.put("filterText", filterText);
        }
        insertUtilInSession(model);
        return "index";
    }

    @RequestMapping(value = "/updateStatus", method = RequestMethod.GET)
    public @ResponseBody
    List<MonitorInfo> refreshPage() {
        return urlMonitorService.getMonitorInfoList();
    }

    @RequestMapping(value = "/filterList", method = RequestMethod.GET)
    public String filterList(@RequestParam(required = false) String filterText,
            ModelMap model) {
        model.addAttribute("monitorList",
                urlMonitorService.getMonitorList(filterText));
        insertUtilInSession(model);
        return "view/index_content";
    }
}
