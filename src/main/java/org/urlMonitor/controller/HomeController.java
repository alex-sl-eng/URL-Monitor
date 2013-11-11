package org.urlMonitor.controller;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.urlMonitor.model.Monitor;
import org.urlMonitor.model.MonitorInfo;
import org.urlMonitor.service.UrlMonitorService;

import com.google.common.collect.Lists;

@Controller
public class HomeController extends BaseController {

    @Autowired
    private UrlMonitorService urlMonitorServiceImpl;

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
        return urlMonitorServiceImpl.getPublicMonitorInfoList();
    }

    @RequestMapping(value = "/filterList", method = RequestMethod.GET)
    public String filterList(@RequestParam(required = false) String filterText,
            ModelMap model) {

        Set<Monitor> monitorList =
                urlMonitorServiceImpl.getPublicMonitorList(filterText);
        if (isUserLoggedIn()) {
            monitorList.addAll(urlMonitorServiceImpl.getPrivateMonitorList(
                    filterText, getUserDetails().getUsername()));
        }

        List<Monitor> sortedList = Lists.newArrayList(monitorList);
        Collections.sort(sortedList, UrlMonitorService.MonitorComparator);

        model.addAttribute("monitorList", sortedList);

        insertUtilInSession(model);
        return "view/content";
    }
}
