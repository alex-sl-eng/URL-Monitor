package org.urlMonitor.controller;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@NoArgsConstructor
@AllArgsConstructor
public class DataForm {

    @Getter
    private Map<String, String> data = Maps.newHashMap();

    @Getter
    private Map<String, Boolean> booleanData = Maps.newHashMap();
}
