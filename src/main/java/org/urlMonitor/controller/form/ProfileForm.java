package org.urlMonitor.controller.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@NoArgsConstructor
@Getter
@Setter
public class ProfileForm {

    @Size(max = 255)
    @NotEmpty
    private String name;

    @Email
    @NotEmpty
    private String email;

    private String joinedDate;

    private boolean admin;

    private boolean user;
}
