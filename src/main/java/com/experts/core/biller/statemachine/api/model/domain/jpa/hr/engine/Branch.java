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
import java.util.List;
import java.util.UUID;

@Entity
@Table(name  = "store_branchs")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL , region = "com.experts.core.biller.statemachine.api.model.domain.jpa.hr.engine.Branch")
public class Branch implements Serializable {


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



    @OneToMany(fetch = FetchType.LAZY , mappedBy = "stores")
    private List<AdsOffers> storesOffers;


    @Column(name  = "notes" , nullable = true)
    private String notes;

    @Column(name  = "description" , nullable = true)
    private String description;



    @Version
    private int version;

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

    public List<AdsOffers> getStoresOffers() {
        return storesOffers;
    }

    public void setStoresOffers(List<AdsOffers> storesOffers) {
        this.storesOffers = storesOffers;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
