package org.urlMonitor.service.Impl;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.urlMonitor.service.AvatarService;
import org.urlMonitor.util.HashUtil;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Service
@Slf4j
public class AvatarServiceImpl implements AvatarService {
    @Override
    public String getUserAvatar(String email, Integer size) {
        if (StringUtils.isEmpty(email)) {
            return GRAVATAR_URL + GRAVATAR_URL_POSTFIX;
        }

        if (size != null) {
            return GRAVATAR_URL + HashUtil.md5Hex(email.toLowerCase()) + "?s="
                    + size + GRAVATAR_URL_POSTFIX;
        } else {
            return GRAVATAR_URL + HashUtil.md5Hex(email.toLowerCase())
                    + GRAVATAR_URL_POSTFIX;
        }

    }
}
