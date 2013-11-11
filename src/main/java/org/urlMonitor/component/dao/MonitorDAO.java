package org.urlMonitor.component.dao;

import java.util.List;

import javax.persistence.Query;

import lombok.NonNull;

import org.springframework.stereotype.Repository;
import org.urlMonitor.model.Monitor;
import org.urlMonitor.model.type.VisibilityType;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Repository
public class MonitorDAO extends AbstractDAO<Monitor, Long> {
    protected MonitorDAO() {
        super(Monitor.class);
    }

    public List<Monitor> getAllActiveMonitor() {
        Query query = getEntityManager().createQuery("from Monitor");
        return query.getResultList();
    }

    public List<Monitor> getPublicMonitor() {
        Query query =
                getEntityManager().createQuery(
                        "from Monitor m where m.visibility=:visibility");
        query.setParameter("visibility", VisibilityType.Public);
        return query.getResultList();
    }

    public List<Monitor> getUserMonitor(@NonNull String email) {
        Query query =
                getEntityManager()
                        .createQuery(
                                "select u.maintainerMonitor from User u where u.email=:email");
        query.setParameter("email", email);
        return query.getResultList();
    }
}
