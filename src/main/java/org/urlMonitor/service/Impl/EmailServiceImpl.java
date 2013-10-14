package org.urlMonitor.service.Impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.urlMonitor.component.MessageResource;
import org.urlMonitor.model.Monitor;
import org.urlMonitor.service.AppConfiguration;
import org.urlMonitor.service.EmailService;
import org.urlMonitor.util.DateUtil;

@Service
@Scope("singleton")
@Slf4j
public class EmailServiceImpl implements EmailService {
    @Autowired
    private AppConfiguration appConfiguration;

    @Autowired
    private MessageResource messageResource;

    private Email getEmailClient() {
        Email email = new SimpleEmail();
        email.setHostName(appConfiguration.getEmailHost());
        email.setSmtpPort(appConfiguration.getEmailPort());
        email.setSSLOnConnect(appConfiguration.isEmailUseSsl());
        email.setStartTLSEnabled(appConfiguration.isEmailUseTsl());
        email.setAuthenticator(new DefaultAuthenticator(appConfiguration
                .getEmailUsername(), appConfiguration.getEmailPassword()));

        return email;
    }

    @Override
    public void sendFailedEmail(Monitor monitor, Date lastCheck)
            throws EmailException {
        String subject =
                messageResource.getMessage("email.subjectPrefix")
                        + monitor.getName();
        sendEmail(
                subject,
                getFailedMessage(monitor.getUrl(), monitor.getContentRegex(),
                        lastCheck), monitor.getEmailToList());
    }

    @Override
    public void sendSuccessEmail(Monitor monitor, Date lastCheck)
            throws EmailException {
        String subject =
                messageResource.getMessage("email.subjectPrefix")
                        + monitor.getName();
        sendEmail(subject, getSuccessMessage(monitor.getUrl(), lastCheck),
                monitor.getEmailToList());
    }

    private void sendEmail(String subject, String message,
            List<String> toEmailList) throws EmailException {
        if (toEmailList != null && !toEmailList.isEmpty()
                && !StringUtils.isEmpty(message)) {
            Email email = getEmailClient();

            email.setFrom(appConfiguration.getEmailFrom());

            for (String toEmail : toEmailList) {
                email.addTo(toEmail);
            }

            email.setSubject(subject);
            email.setMsg(message);

            email.send();
            EmailServiceImpl.log.info("sent email to - " + toEmailList);
        } else {
            EmailServiceImpl.log
                    .info("send email ignored - no email recipients defined.");
        }
    }

    private String getSuccessMessage(String url, Date lastCheck) {
        StringBuilder sb = new StringBuilder();
        sb.append(getEmailHeader());

        sb.append(messageResource.getMessage("email.successMessage", url));
        sb.append("\n");
        sb.append(messageResource.getMessage("email.lastChecked",
                DateUtil.formatShortDate(lastCheck)));

        sb.append(getEmailFooter());

        return sb.toString();
    }

    private String getFailedMessage(String url, String searchString,
            Date lastFailedTime) {
        StringBuilder sb = new StringBuilder();
        sb.append(getEmailHeader());

        sb.append(messageResource.getMessage("email.failMessage", url));
        sb.append("\n");
        sb.append(messageResource.getMessage("email.lastChecked",
                DateUtil.formatShortDate(lastFailedTime)));
        if (!StringUtils.isEmpty(searchString)) {
            sb.append(messageResource.getMessage("email.contentSearched",
                    searchString));
        }
        sb.append(getEmailFooter());

        return sb.toString();
    }

    private String getEmailHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append(messageResource.getMessage("email.header"));
        sb.append("\n");
        sb.append("\n");

        return sb.toString();
    }

    private String getEmailFooter() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("\n");
            sb.append("\n");
            InetAddress addr = InetAddress.getLocalHost();
            sb.append(addr.getCanonicalHostName());
            sb.append("\n");
        } catch (UnknownHostException e) {
            log.warn("Can't retrieve hostname-" + e);
        } finally {
            if (!StringUtils.isEmpty(appConfiguration.getEmailReplyTo())) {
                sb.append(appConfiguration.getEmailReplyTo());
            }
            return sb.toString();
        }
    }
}
