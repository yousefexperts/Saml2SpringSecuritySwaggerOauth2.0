package com.experts.core.biller.statemachine.api.model.domain.jpa.hr.engine;

import com.experts.core.biller.statemachine.api.model.domain.jpa.UsersCore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name  = "adsoffer_store")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL , region = "com.experts.core.biller.statemachine.api.model.domain.jpa.hr.engine.AdsOffers")
public class AdsOffers implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy="uuid2")
    private UUID id;


    @ManyToOne(fetch = FetchType.LAZY , cascade =  CascadeType.ALL)
    @JoinColumn(name  = "store_id" , nullable = false)
    private Store stores;

    @LastModifiedDate
    private Date creationDate;

    @LastModifiedBy
    private UsersCore usersCore;

    @Column(name  = "offer_name" , nullable = false)
    private String offereName;

    @Column(name  = "dead_line" , nullable = true)
    private Date deadLine;

    @Version
    private int version;



    @Column(name  = "notes" , nullable = true)
    private String notes;

    @Column(name  = "description" , nullable = true)
    private String description;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Store getStores() {
        return stores;
    }

    public void setStores(Store stores) {
        this.stores = stores;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public UsersCore getUsersCore() {
        return usersCore;
    }

    public void setUsersCore(UsersCore usersCore) {
        this.usersCore = usersCore;
    }

    public String getOffereName() {
        return offereName;
    }

    public void setOffereName(String offereName) {
        this.offereName = offereName;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
