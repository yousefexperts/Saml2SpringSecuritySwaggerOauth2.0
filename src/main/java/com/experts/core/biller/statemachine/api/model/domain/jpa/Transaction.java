package com.experts.core.biller.statemachine.api.model.domain.jpa;

import com.experts.core.biller.statemachine.api.model.EntityRevisionListener_;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name  = "transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners({EntityRevisionListener_.class})
public class Transaction extends AbstractEntity {


    @Version
    private int version;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name  = "state_id" , nullable = false)
    private PayState states;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name  = "state_process_id" , nullable = false)
    private ProcessState processes;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name  = "status_id" , nullable = false)
    private TransactionStatus status;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public PayState getStates() {
        return states;
    }

    public void setStates(PayState states) {
        this.states = states;
    }

    public ProcessState getProcesses() {
        return processes;
    }

    public void setProcesses(ProcessState processes) {
        this.processes = processes;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
