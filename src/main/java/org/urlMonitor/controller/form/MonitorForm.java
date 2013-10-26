package org.urlMonitor.controller.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@NoArgsConstructor
@Getter
@Setter
public class MonitorForm {

    @Size(max = 100)
    @NotEmpty
    private String name;

    @Size(max = 255)
    private String description;

    @NotNull
    @URL
    @Size(max = 2083)
    private String url;

    @Size(max = 20)
    private String visibility;

    @NotNull
    @Size(max = 100)
    private String cron;

    @Size(max = 255)
    private String contentRegex;

    @Size(max = 255)
    private String emailToList;

    @Size(max = 255)
    private String tag;

}
