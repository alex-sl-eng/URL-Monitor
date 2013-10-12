package org.urlMonitor.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.urlMonitor.model.type.StatusType;
import org.urlMonitor.model.type.VisibilityType;
import org.urlMonitor.util.CronHelper;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @author Alex Eng - loones1595@gmail.com
 * 
 */
@Entity
@NoArgsConstructor
@Access(AccessType.FIELD)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, of = { "name", "url", "contentRegex",
        "cron" })
public class Monitor extends ModelBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 100)
    private String name;

    @Size(max = 255)
    private String description;

    @NotNull
    @URL
    @Size(max = 2083)
    private String url;

    @Enumerated(EnumType.STRING)
    @Length(max = 20)
    private StatusType status = StatusType.Unknown;

    @Enumerated(EnumType.STRING)
    @Length(max = 20)
    private VisibilityType visibility = VisibilityType.Public;

    /**
     * see http://en.wikipedia.org/wiki/Cron#CRON_expression
     */
    @NotNull
    @Size(max = 100)
    // DEFAULT: every 5 minutes
    private String cron = CronHelper.CronType.FIVE_MINUTES.getExpression();

    @Size(max = 255)
    @Column(name = "content_regex")
    private String contentRegex; // check for text exist if return http 200

    @Size(max = 255)
    @Column(name = "email_to_list")
    private String emailToList;

    @Size(max = 255)
    private String tag;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_failed")
    private Date lastFailed;

    @ManyToMany
    @JoinTable(name = "Monitor_Maintainer", joinColumns = @JoinColumn(
            name = "monitor_id"), inverseJoinColumns = @JoinColumn(
            name = "user_id"))
    private Set<User> maintainers = Sets.newHashSet();

    @Transient
    public List<String> getEmailToList() {
        if (!StringUtils.isEmpty(emailToList)) {
            return Arrays.asList(emailToList.split(";"));
        }
        return Lists.newArrayList();
    }

    @Transient
    public List<String> getTagList() {
        if (!StringUtils.isEmpty(tag)) {
            return Arrays.asList(tag.split(";"));
        }
        return Lists.newArrayList();
    }

    public void update(StatusType status) {
        this.status = status;

        Date now = new Date();
        if (status == StatusType.Failed) {
            lastFailed = now;
        }

        // TODO: remove this once loaded from db
        lastChanged = now;
    }

    public void addMaintainer(User maintainer) {
        this.getMaintainers().add(maintainer);
        maintainer.getMaintainerMonitor().add(this);
    }
}
