package org.urlMonitor.service;

import java.util.Date;

import org.apache.commons.mail.EmailException;
import org.urlMonitor.model.Monitor;

public interface EmailService {
    /**
     * Send email when check failed
     *
     * @param monitor
     * @param lastCheck
     * @throws EmailException
     */
    void sendFailedEmail(Monitor monitor, Date lastCheck) throws EmailException;

    /**
     * Send email when check pass again after failed
     *
     * @param monitor
     * @param lastCheck
     * @throws EmailException
     */
    void sendSuccessEmail(Monitor monitor, Date lastCheck)
            throws EmailException;
}
