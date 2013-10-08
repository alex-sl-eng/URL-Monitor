package org.urlMonitor.controller.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@AllArgsConstructor
@Getter
@Setter
public class JsonResponse {
    public static final String STATUS_SUCCESS = "Success";
    public static final String STATUS_FAILED = "Failed";

    private String status = null;

    private Object result = null;
}
