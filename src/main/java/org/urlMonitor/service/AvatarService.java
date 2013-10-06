package org.urlMonitor.service;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
public interface AvatarService {

    static final String GRAVATAR_URL = "http://www.gravatar.com/avatar/";

    static final String GRAVATAR_URL_POSTFIX = "?d=mm";

    /**
     * Get user avatar by email and size
     * 
     * @param email
     * @param size
     * @return
     */
    String getUserAvatar(String email, Integer size);
}
