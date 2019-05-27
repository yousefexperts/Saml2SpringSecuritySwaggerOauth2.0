package com.experts.core.biller.statemachine.api.rovo.awsxray.domain.entities.jpa;

import javax.persistence.*;

@Entity
@Table(name = "audit")
@NamedQueries({
        @NamedQuery(name = "findLogsViaUser", query = "SELECT log FROM AuditLogEntity log WHERE log.user = :user ORDER BY log.id")
})
public class AuditLogEntity extends BaseJpaEntity {

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private LogUserEntity user;
    private String log;

    public LogUserEntity getUser() {
        return this.user;
    }

    public void setUser(LogUserEntity user) {
        this.user = user;
    }

    public String getLog() {
        return this.log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
