package com.experts.core.biller.statemachine.api.model.domain.jpa;

import com.experts.core.biller.statemachine.api.model.EntityRevisionListener_;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.annotation.pojo.SpacePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name  = "completed_state")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners({EntityRevisionListener_.class})
@SpaceClass
public class CompletedState extends AbstractEntity {

    public static final String DB_TABLE = "com/experts/core/biller/statemachine/api/vertxloader";

    @Column(name  = "name" , nullable = false)
    private String name;

    @Column(name  = "code" , nullable = false)
    private String code;

    @Column(name  = "message" , nullable = false)
    private String message;

    @Column(name  = "execption" , nullable = true)
    private String execption;

    @Column(name  = "sucess" , nullable = false)
    private boolean isSuccess;

    @OneToOne
    @JoinColumn(name  = "processstate_completedState" , nullable = false)
    private ProcessState processstate_completedState;

    @Column(name  = "bill_no" , nullable = false)
    private String billNo;

    @Column(name  = "bill_code" , nullable = false)
    private String billCode;

    @Column(name  = "transaction_id" , nullable = false)
    private String transactionId;

    @Column(name  = "biller_name" , nullable = false)
    private String billerName;

    @Column(name  = "biller_code" , nullable = false)
    private String billerCode;

    public static String getDbTable() {
        return DB_TABLE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExecption() {
        return execption;
    }

    public void setExecption(String execption) {
        this.execption = execption;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public ProcessState getProcessstate_completedState() {
        return processstate_completedState;
    }

    public void setProcessstate_completedState(ProcessState processstate_completedState) {
        this.processstate_completedState = processstate_completedState;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getBillerName() {
        return billerName;
    }

    public void setBillerName(String billerName) {
        this.billerName = billerName;
    }

    public String getBillerCode() {
        return billerCode;
    }

    public void setBillerCode(String billerCode) {
        this.billerCode = billerCode;
    }
}
