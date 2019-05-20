package com.experts.core.biller.statemachine.api.model.domain.jpa;


import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.annotation.pojo.SpacePersist;
import com.gigaspaces.annotation.pojo.SpaceVersion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jooq.Allow;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NegativeOrZero;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name  = "bill_pull_request")
@XmlRootElement(name  = "bill_pull_request_creation")
@XmlAccessorType
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SpaceClass
public class BillPullTransactionResponse extends AbstractEntity implements Serializable {


    @XmlElement(name  = "res_count" , required = true)
    @Column(name  = "res_count" , nullable = false)
    private String resCount;


    @XmlElement(name  = "bills_rec" , required = true)
    @Column(name  = "bills_rec" , nullable = false)
    private String billsRec;

    @XmlElement(name  = "bill_rec" , required = true)
    @Column(name  = "bill_rec" , nullable = false)
    private String billRec;


    @Column(name  = "result" , nullable = false)
    @XmlElement(name = "result" , required = true)
    private String result;

    @Column(name  = "error_code" , nullable = false)
    @XmlElement(name  = "error_code" , required = true)
    private String errorCode;


    @XmlElement(name  = "error_desc" , required = true)
    @Column(name  = "error_desc" , nullable = false)
    private String errorDesc;


    @XmlElement(name  = "erverity" , required = true)
    @Column(name  = "erverity" , nullable = false)
    private String serverity;


    @Column(name  = "acc_info" , nullable = false)
    @XmlElement(name  = "acc_inf" , required = true)
    private String accInfo;


    @Column(name  = "bill_no" , nullable = false)
    @XmlElement(name  = "bill_no" , required = true)
    private String billNo;


    @Column(name  = "billing_no" , nullable = false)
    @XmlElement(name  = "billing_no" , required = true)
    private String billingNo;


    @Column(name  = "bill_status" , nullable = false)
    @XmlElement(name  = "bill_status" , required = true)
    private String billStatus;

    @Column(name = "due_anount" , nullable = false)
    @XmlElement(name  = "due_amount" , required = true)
    private String dueAmount;


    @Column(name  = "currency" , nullable = false)
    @XmlElement(name = "currency" , required = true)
    private String currency;

    @XmlElement(name  = "issue_date" , required = true)
    @Column(name  = "issue_date" , nullable = false)
    private String issueDate;


    @Column(name  = "open_date" , nullable = true)
    @XmlElement(name  = "open_date" , required = false)
    private Date openDate;


    @Column(name  = "due_date" , nullable = false)
    @XmlElement(name  = "due_date" , required = true)
    private Date dueDate;

    @Column(name  = "expiry_date" , nullable = true)
    @XmlElement(name  = "expiry_date" , required = false)
    private Date expiryDate;

    @Column(name  = "close_date" , nullable = true)
    @XmlElement(name  = "close_date" , required = false)
    private Date closeDate;


   @Column(name  = "service_type" , nullable = false)
   @XmlElement(name  = "service_type" , required = true)
    private String serviceType;

    @Column(name  = "bill_type" , nullable = true)
    @XmlElement(name  = "bill_type" , required = false)
    private String billType;


    @Column(name  = "pmt_const" , nullable = true)
    @XmlElement(name  = "pmt_const" , required = false)
    private String pmtConst;

    @Column(name= "allow_part" , nullable = false)
    @XmlElement(name  = "allow_part" , required = true)
    private String allowPart;

    @Column(name  = "lower" , nullable = false)
    @XmlElement(name  = "lower" , required = true)
    private String lower;

    @Column(name  = "upper" , nullable = false)
    @XmlElement(name  = "upper" , required = true)
    private String upper;


    @Column(name  = "sub_pmts" , nullable = false)
    @XmlElement(name  = "sub_pmts" , required = true)
    private String subPmts;

    @Column(name  = "sub_pmt" , nullable = false)
    @XmlElement(name  = "sub_pmt" , required = true)
    private String subPmt;

    @Column(name  = "amount" , nullable = false)
    @XmlElement(name  = "amount" , required = true)
    private String amount;


    @Column(name  = "bank_code" , nullable = false)
    @XmlElement(name  = "bank_code" , required = true)
    private String bankCode;


    @Column(name  = "acc_no" , nullable = false)
    @XmlElement(name  = "acc_no" , required = true)
    private String accNo;

    public String getResCount() {
        return resCount;
    }

    public void setResCount(String resCount) {
        this.resCount = resCount;
    }

    public String getBillsRec() {
        return billsRec;
    }

    public void setBillsRec(String billsRec) {
        this.billsRec = billsRec;
    }

    public String getBillRec() {
        return billRec;
    }

    public void setBillRec(String billRec) {
        this.billRec = billRec;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public String getServerity() {
        return serverity;
    }

    public void setServerity(String serverity) {
        this.serverity = serverity;
    }

    public String getAccInfo() {
        return accInfo;
    }

    public void setAccInfo(String accInfo) {
        this.accInfo = accInfo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBillingNo() {
        return billingNo;
    }

    public void setBillingNo(String billingNo) {
        this.billingNo = billingNo;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public String getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(String dueAmount) {
        this.dueAmount = dueAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getPmtConst() {
        return pmtConst;
    }

    public void setPmtConst(String pmtConst) {
        this.pmtConst = pmtConst;
    }

    public String getAllowPart() {
        return allowPart;
    }

    public void setAllowPart(String allowPart) {
        this.allowPart = allowPart;
    }

    public String getLower() {
        return lower;
    }

    public void setLower(String lower) {
        this.lower = lower;
    }

    public String getUpper() {
        return upper;
    }

    public void setUpper(String upper) {
        this.upper = upper;
    }

    public String getSubPmts() {
        return subPmts;
    }

    public void setSubPmts(String subPmts) {
        this.subPmts = subPmts;
    }

    public String getSubPmt() {
        return subPmt;
    }

    public void setSubPmt(String subPmt) {
        this.subPmt = subPmt;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }
}
