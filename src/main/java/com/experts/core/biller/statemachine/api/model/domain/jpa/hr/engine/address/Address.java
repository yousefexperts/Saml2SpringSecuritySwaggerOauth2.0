package com.experts.core.biller.statemachine.api.model.domain.jpa.hr.engine.address;


import com.experts.core.biller.statemachine.api.model.domain.jpa.UsersCore;
import com.experts.core.biller.statemachine.api.model.domain.jpa.hr.engine.Store;
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
@Table(name  = "store_address")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL , region = "com.experts.core.biller.statemachine.api.model.domain.jpa.hr.engine.address.Address")
public class Address implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy="uuid2")
    private UUID id;

    @Column(name  = "address_name" , nullable = false)
    private String addressName;


    @ManyToOne(fetch = FetchType.LAZY , cascade =  CascadeType.ALL)
    @JoinColumn(name  = "store_id" , nullable = false)
    private Store stores;


    @LastModifiedDate
    private Date creationDate;

    @LastModifiedBy
    private UsersCore usersCore;


    @Version
    private int version;


    @Column(name  = "notes" , nullable = true)
    private String notes;

    @Column(name  = "description" , nullable = true)
    private String description;

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "addresses")
    private List<PlaceInterest> placeInterests;

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "addresses")
    private List<Street> streets;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
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

    public List<PlaceInterest> getPlaceInterests() {
        return placeInterests;
    }

    public void setPlaceInterests(List<PlaceInterest> placeInterests) {
        this.placeInterests = placeInterests;
    }

    public List<Street> getStreets() {
        return streets;
    }

    public void setStreets(List<Street> streets) {
        this.streets = streets;
    }
}
