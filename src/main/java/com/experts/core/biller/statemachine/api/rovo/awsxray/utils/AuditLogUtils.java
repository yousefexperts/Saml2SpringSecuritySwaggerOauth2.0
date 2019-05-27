package com.experts.core.biller.statemachine.api.rovo.awsxray.utils;

import com.experts.core.biller.statemachine.api.rovo.awsxray.domain.AuditLogService;
import com.experts.core.biller.statemachine.api.rovo.awsxray.domain.entities.jpa.AuditLogEntity;
import com.experts.core.biller.statemachine.api.rovo.awsxray.domain.entities.jpa.LogUserEntity;

import javax.annotation.Resource;

public class AuditLogUtils {

    @Resource
    private AuditLogService auditLogService;

    public void auditLog(String userId, String logMessage) {

        LogUserEntity logUser = auditLogService.findUserByUserId(userId);

        AuditLogEntity auditLog = new AuditLogEntity();
        auditLog.setUser(logUser);
        auditLog.setLog(logMessage);
        auditLogService.persist(auditLog);
    }
}
