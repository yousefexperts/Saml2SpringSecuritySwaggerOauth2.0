package com.experts.core.biller.statemachine.api.model.domain.jpa.settlement;

import com.experts.core.biller.statemachine.api.model.domain.jpa.AbstractEntity;
import com.gigaspaces.annotation.pojo.SpaceClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name  = "bank_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SpaceClass
public class Bank extends AbstractEntity implements Serializable {

    @Column(name  = "bank_name" , nullable = false)
    private String bankName;

    @Column(name  = "bank_code" , nullable = false)
    private String bankCode;

    @Column(name  = "bank_email" , nullable = true)
    private String bankEmail;

    @Column(name  = "bank_phone" , nullable = true)
    private String bankPhone;

    @Column(name  = "bank_address" , nullable = false)
    private String bankAddress;

    @Column(name = "bank_address_street" , nullable = false)
    private String bankAddressStreet;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankEmail() {
        return bankEmail;
    }

    public void setBankEmail(String bankEmail) {
        this.bankEmail = bankEmail;
    }

    public String getBankPhone() {
        return bankPhone;
    }

    public void setBankPhone(String bankPhone) {
        this.bankPhone = bankPhone;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getBankAddressStreet() {
        return bankAddressStreet;
    }

    public void setBankAddressStreet(String bankAddressStreet) {
        this.bankAddressStreet = bankAddressStreet;
    }
}
