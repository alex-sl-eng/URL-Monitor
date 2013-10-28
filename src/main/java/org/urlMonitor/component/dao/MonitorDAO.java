package org.urlMonitor.component.dao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.urlMonitor.model.Monitor;
import org.urlMonitor.model.User;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Set;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Repository
public class MonitorDAO extends AbstractDAO<Monitor, Long> {
    protected MonitorDAO() {
        super(Monitor.class);
    }
}
