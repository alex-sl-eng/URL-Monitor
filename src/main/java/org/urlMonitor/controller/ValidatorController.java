package org.urlMonitor.controller;

import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.EmailValidator;
import org.apache.commons.validator.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.urlMonitor.component.MessageResource;
import org.urlMonitor.controller.form.JsonResponse;
import org.urlMonitor.model.Monitor;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Controller
@Slf4j
public class ValidatorController {

    @Autowired
    private MessageResource messageResource;

    private UrlValidator urlValidator = new UrlValidator();

    @RequestMapping(value = "/validateName", method = RequestMethod.POST)
    public @ResponseBody
    JsonResponse validateName(
            @RequestParam(value = "name", required = true) String name) {

        String error = "";
        if (StringUtils.isEmpty(name)) {
            error =
                    messageResource
                            .getMessage("org.hibernate.validator.constraints.NotEmpty.message");
        } else if (StringUtils.length(name) > 255) {
            error =
                    messageResource.getMessage(
                            "validation.constraints.Size.message", "1", "255");
        }
        return getResponse(error);
    }

    @RequestMapping(value = "/validateUrl", method = RequestMethod.POST)
    public @ResponseBody
    JsonResponse validateUrl(
            @RequestParam(value = "url", required = true) String url) {

        String error = "";
        if (StringUtils.isEmpty(url)) {
            error =
                    messageResource
                            .getMessage("org.hibernate.validator.constraints.NotEmpty.message");
        } else if (!urlValidator.isValid(url)) {
            error =
                    messageResource
                            .getMessage(
                                "org.hibernate.validator.constraints.URL.message");

        }

        return getResponse(error);
    }

    @RequestMapping(value = "/validateEmailList", method = RequestMethod.POST)
    public @ResponseBody
    JsonResponse validateEmailList(@RequestParam(value = "emailToList",
            required = true) String emailToList) {

        String error = "";
        if (!StringUtils.isEmpty(emailToList)) {
            List<String> emailList =
                    Arrays.asList(emailToList.split(Monitor.SEPARATOR));
            for (String email : emailList) {
                if (!EmailValidator.getInstance().isValid(email)) {
                    error +=
                            messageResource
                                    .getMessage(
                                            "org.hibernate.validator.constraints.Email.message",
                                            email);
                }
            }
        }

        return getResponse(error);
    }

    private JsonResponse getResponse(String error) {
        JsonResponse response = null;

        if (StringUtils.isEmpty(error)) {
            response = new JsonResponse(JsonResponse.STATUS_SUCCESS, null);
        } else {
            response = new JsonResponse(JsonResponse.STATUS_FAILED, error);
        }
        return response;
    }

}
