package org.urlMonitor.controller;

import java.util.Map;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.urlMonitor.component.Identity;
import org.urlMonitor.controller.form.ProfileForm;
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
        ProfileForm profileForm = new ProfileForm();
        refreshData(profileForm, null);

        model.put("profileForm", profileForm);
        return "auth/settings";
    }

    @RequestMapping(value = "/settings/profile", method = RequestMethod.POST)
    public String onUpdateProfile(@Valid ProfileForm profileForm,
            BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "auth/settings";
        }

        User user =
                userServiceImpl.updateUserByEmail(profileForm.getEmail(),
                        profileForm.getName(), profileForm.isAdmin(),
                        profileForm.isUser());
        refreshData(profileForm, user);

        model.put("profileForm", profileForm);
        model.put("messages", "Profile updated.");
        return "auth/settings";
    }

    private void refreshData(ProfileForm profileForm, User user) {
        if (user == null) {
            profileForm.setName(identity.getName());
            profileForm.setEmail(identity.getEmail());
            profileForm.setJoinedDate(DateUtil.getMonthAndYear(identity
                    .getJoinedDate()));

            Map<String, Boolean> userRoles =
                    userServiceImpl.getUserRoles(identity.getEmail());
            boolean isAdmin =
                    userRoles.containsKey(UserService.USER_ADMIN) ? userRoles
                            .get(UserService.USER_ADMIN) : false;
            boolean isUser =
                    userRoles.containsKey(UserService.USER_ROLE) ? userRoles
                            .get(UserService.USER_ROLE).booleanValue() : false;
            profileForm.setAdmin(isAdmin);
            profileForm.setUser(isUser);

        } else {
            profileForm.setName(user.getName());
            profileForm.setEmail(user.getEmail());
            profileForm.setJoinedDate(DateUtil.getMonthAndYear(user
                    .getCreationDate()));

            Map<String, Boolean> userRoles =
                    userServiceImpl.getUserRoles(user.getEmail());
            boolean isAdmin =
                    userRoles.containsKey(UserService.USER_ADMIN) ? userRoles
                            .get(UserService.USER_ADMIN) : false;
            boolean isUser =
                    userRoles.containsKey(UserService.USER_ROLE) ? userRoles
                            .get(UserService.USER_ROLE).booleanValue() : false;
            profileForm.setAdmin(isAdmin);
            profileForm.setUser(isUser);
        }
    }
}
