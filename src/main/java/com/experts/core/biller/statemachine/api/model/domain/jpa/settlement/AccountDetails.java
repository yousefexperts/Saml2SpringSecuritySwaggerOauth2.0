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
import javax.persistence.Version;
import java.io.Serializable;

@Entity
@Table(name  = "account_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SpaceClass
public class AccountDetails extends AbstractEntity implements Serializable {


    @Column(name  = "account_name" , nullable = false)
    private String accountName;

    @Column(name  = "account_number" , nullable = false)
    private String accountNumber;

    @Column(name  = "isbn" , nullable = false)
    private String isbn;

    @Column(name  = "maxTx" , nullable = false)
    private int maxTx;

    @Column(name  = "max_queue_tx" , nullable = false)
    private int maxQueueTx;

    @Column(name  = "balance" , nullable = false)
    private int balance;

    @Version
    private int version;


    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getMaxTx() {
        return maxTx;
    }

    public void setMaxTx(int maxTx) {
        this.maxTx = maxTx;
    }

    public int getMaxQueueTx() {
        return maxQueueTx;
    }

    public void setMaxQueueTx(int maxQueueTx) {
        this.maxQueueTx = maxQueueTx;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
