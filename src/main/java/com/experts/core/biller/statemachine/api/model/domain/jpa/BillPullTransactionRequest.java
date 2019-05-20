package com.experts.core.biller.statemachine.api.model.domain.jpa;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.annotation.pojo.SpacePersist;
import com.gigaspaces.annotation.pojo.SpaceVersion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@Table(name  = "bill_pull_request")
@XmlRootElement(name  = "bill_pull_request_creation")
@XmlAccessorType
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SpaceClass
public class BillPullTransactionRequest extends AbstractEntity implements Serializable {


    @Column(name  = "acc_info" , nullable = false)
    @XmlElement(name = "acc_info" , required = true)
    private String acctInfo;

    @Column(name  = "billing_no" , nullable = false)
    @XmlElement(required = true , name = "billing_no" )
    private String billingNo;

    @Column(name  = "bill_no" , nullable = false)
    @XmlElement(name = "bill_no" , required = true)
    private String billNo;

    @Column(name  = "service_type" , nullable = true)
    @XmlElement(name  = "service_type" , required = false)
    private String serviceType;

    @Column(name  = "payer_info" , nullable = true)
    @XmlElement(name = "payer_info" , required = false)
    private String payerInfo;

    @Column(name  = "id_type" , nullable = true)
    @XmlElement(name  = "id_type" , required = false)
    private String idType;


    @Column(name  = "nation" , nullable = true)
    @XmlElement(name  = "nation" , required = false)
    private String nation;

    @Column(name  = "name" , nullable = true)
    @XmlElement(name = "name" , required = false)
    private String name;

    @Column(name  = "phone" , nullable = true)
    @XmlElement(name  = "phone" , required = false)
    private String phone;

    @Column(name  = "address" , nullable = true)
    @XmlElement(name =  "address" , required = false)
    private String address;

    @Column(name  = "email" , nullable = true)
    @XmlElement(name  = "email" , required = false)
    private String email;

    @Column(name  = "joebppsno" , nullable = false)
    @XmlElement(name  = "joebppsno" , required = false)
    private String joebppsno;

    public String getAcctInfo() {
        return acctInfo;
    }

    public void setAcctInfo(String acctInfo) {
        this.acctInfo = acctInfo;
    }

    public String getBillingNo() {
        return billingNo;
    }

    public void setBillingNo(String billingNo) {
        this.billingNo = billingNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getPayerInfo() {
        return payerInfo;
    }

    public void setPayerInfo(String payerInfo) {
        this.payerInfo = payerInfo;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJoebppsno() {
        return joebppsno;
    }

    public void setJoebppsno(String joebppsno) {
        this.joebppsno = joebppsno;
    }
}
