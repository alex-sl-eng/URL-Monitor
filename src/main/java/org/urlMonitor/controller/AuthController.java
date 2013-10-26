package org.urlMonitor.controller;

import java.security.Principal;
import java.util.Map;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.urlMonitor.component.MessageResource;
import org.urlMonitor.controller.form.ProfileForm;
import org.urlMonitor.model.User;
import org.urlMonitor.model.type.SeverityType;
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
    private UserService userServiceImpl;

    @Autowired
    private MessageResource messageResource;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(
            @RequestParam(value = "error", required = false) boolean error,
            ModelMap model) {
        if (error) {
            addMessages(SeverityType.error,
                    messageResource.getMessage("error.login.invalid"), model);
        } else {
            clearMessages(model);
        }
        return "auth/login";
    }

    @RequestMapping(value = "/denied", method = RequestMethod.GET)
    public String getDeniedPage() {
        return "auth/denied";
    }

    @RequestMapping(value = "/settings/**", method = RequestMethod.GET)
    public String getSettingsPage(ModelMap model, Principal principal) {

        ProfileForm profileForm = new ProfileForm();

        org.springframework.security.core.userdetails.User activeUser =
                (org.springframework.security.core.userdetails.User) ((Authentication) principal)
                        .getPrincipal();

        User user = userServiceImpl.findByEmail(activeUser.getUsername());
        refreshData(profileForm, user);

        model.put("profileForm", profileForm);
        return "auth/settings";
    }

    @RequestMapping(value = "/settings/profile", method = RequestMethod.POST)
    public String onUpdateProfile(
            @Valid @ModelAttribute("profileForm") ProfileForm profileForm,
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
        addMessages(SeverityType.info,
                messageResource.getMessage("jsp.Profile.Updated"), model);
        return "auth/settings";
    }

    private void refreshData(ProfileForm profileForm, User user) {
        profileForm.setName(user.getName());
        profileForm.setEmail(user.getEmail());
        profileForm.setJoinedDate(DateUtil.getMonthAndYear(user
                .getCreationDate()));

        Map<String, Boolean> userRoles =
                userServiceImpl.getUserRoles(user.getEmail());
        boolean isAdmin =
                userRoles.containsKey(UserService.ROLE_ADMIN) ? userRoles
                        .get(UserService.ROLE_ADMIN) : false;
        boolean isUser =
                userRoles.containsKey(UserService.ROLE_USER) ? userRoles.get(
                        UserService.ROLE_USER).booleanValue() : false;

        profileForm.setAdmin(isAdmin);
        profileForm.setUser(isUser);
    }
}
