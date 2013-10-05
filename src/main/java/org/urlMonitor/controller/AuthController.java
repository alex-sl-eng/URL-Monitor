package org.urlMonitor.controller;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.urlMonitor.component.Identity;
import org.urlMonitor.model.User;
import org.urlMonitor.service.UserService;
import org.urlMonitor.util.DateUtil;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Controller
@RequestMapping(value = "/auth")
@Slf4j
public class AuthController extends BaseController {

    @Autowired
    private Identity identity;

    @Autowired
    private UserService userServiceImpl;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(
            @RequestParam(value = "error", required = false) boolean error,
            ModelMap model) {
        if (error) {
            model.put("messages", "Invalid username or password");
        } else {
            model.put("messages", "");
        }
        return "auth/login";
    }

    @RequestMapping(value = "/denied", method = RequestMethod.GET)
    public String getDeniedPage() {
        return "auth/denied";
    }

    @RequestMapping(value = "/settings/**", method = RequestMethod.GET)
    public String getSettingsPage(ModelMap model) {
        DataForm dataForm = new DataForm();
        refreshData(dataForm, null);

        model.put("dataForm", dataForm);
        return "auth/settings";
    }

    @RequestMapping(value = "/settings/profile", method = RequestMethod.POST)
    public String onUpdateProfile(
            @ModelAttribute("dataForm") DataForm dataForm, ModelMap model) {

        String email = dataForm.getData().get("email");
        String updatedName = dataForm.getData().get("name");
        Map<String, Boolean> roles = dataForm.getBooleanData();

        User user = null;
        if (roles.isEmpty()) {
            user = userServiceImpl.updateUserByEmail(email, updatedName);
        } else {
            user = userServiceImpl.updateUserByEmail(email, updatedName, roles);
        }
        refreshData(dataForm, user);

        model.put("dataForm", dataForm);
        model.put("messages", "Profile updated.");
        return "auth/settings";
    }

    private void refreshData(DataForm dataForm, User user) {
        if (user == null) {
            dataForm.getData().put("name", identity.getName());
            dataForm.getData().put("email", identity.getEmail());
            dataForm.getBooleanData().clear();
            dataForm.getBooleanData().putAll(
                    userServiceImpl.getUserRoles(identity.getEmail()));
            dataForm.getData().put("joinedDate", identity.getJoinedDate());
        } else {
            dataForm.getData().put("name", user.getName());
            dataForm.getData().put("email", user.getEmail());
            dataForm.getBooleanData().clear();
            dataForm.getBooleanData().putAll(
                    userServiceImpl.getUserRoles(user.getEmail()));
            dataForm.getData().put("joinedDate",
                    DateUtil.getMonthAndYear(user.getCreationDate()));
        }
    }
}
