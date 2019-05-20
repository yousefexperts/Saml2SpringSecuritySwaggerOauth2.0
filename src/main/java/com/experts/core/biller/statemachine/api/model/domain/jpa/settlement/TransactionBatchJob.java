package com.experts.core.biller.statemachine.api.model.domain.jpa.settlement;


import com.experts.core.biller.statemachine.api.model.domain.jpa.AbstractEntity;
import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.annotation.pojo.SpacePersist;
import com.gigaspaces.annotation.pojo.SpaceVersion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name  = "transaction_batch_job")
@SpaceClass
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TransactionBatchJob extends AbstractEntity implements Serializable {

    @Column(name  = "name" , nullable = false)
    private String name;

    @Column(name  = "description" , nullable = true)
    private String description;

    @Column(name   = "success" , nullable = false)
    private boolean isSuccess;

    @Column(name  = "request" , nullable = false)
    private String request;

    @Column(name = "response" , nullable = false)
    private String response;

    @Column(name  = "exception" , nullable = true)
    private String exception;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name  = "transaction_id" , nullable = false)
    private TransactionCreation transactionCreation;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public TransactionCreation getTransactionCreation() {
        return transactionCreation;
    }

    public void setTransactionCreation(TransactionCreation transactionCreation) {
        this.transactionCreation = transactionCreation;
    }
}
