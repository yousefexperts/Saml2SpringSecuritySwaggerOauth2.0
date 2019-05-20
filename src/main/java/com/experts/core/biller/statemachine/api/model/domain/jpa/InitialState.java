package com.experts.core.biller.statemachine.api.model.domain.jpa;

import com.experts.core.biller.statemachine.api.persist.OpsEntityListener;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.annotation.pojo.SpacePersist;
import com.gigaspaces.annotation.pojo.SpaceVersion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Entity
@Table(name  = "initial_state")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners({AuditingEntityListener.class , OpsEntityListener.class})
@SpaceClass
public class InitialState extends AbstractEntity {

    public static final String DB_TABLE = "com/experts/core/biller/statemachine/api/vertxloader";
    /*private String id;*/

    @Column(name  = "bill_code" , nullable = false)
    private String billCode;

    @Column(name = "billName" , nullable = false)
    private String billName;

    @Column(name  = "biller_name" , nullable = false)
    private String billerName;

    @Column(name  = "biller_code" , nullable = false)
    private String billerCode;

    @Column(name  = "biller_address" , nullable = true)
    private String billerAddress;

    @Column(name  = "client_id" , nullable = false)
    private String clientId;

    @Column(name  = "service_used" , nullable = false)
    private String onServiceBy;

    @Column(name = "bank_id" , nullable = false)
    private String bankId;

    @Column(name  = "bank_name" , nullable = false)
    private String bankName;

    @Column(name  = "bank_addrs" , nullable =  true)
    private String bankAddress;

    @Column(name  = "bank_code" , nullable = false)
    private String bankCode;

    public static String getDbTable() {
        return DB_TABLE;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
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

    public String getBillerAddress() {
        return billerAddress;
    }

    public void setBillerAddress(String billerAddress) {
        this.billerAddress = billerAddress;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getOnServiceBy() {
        return onServiceBy;
    }

    public void setOnServiceBy(String onServiceBy) {
        this.onServiceBy = onServiceBy;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
