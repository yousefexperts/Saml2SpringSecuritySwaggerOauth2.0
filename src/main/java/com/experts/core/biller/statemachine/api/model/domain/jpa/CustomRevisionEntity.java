package com.experts.core.biller.statemachine.api.model.domain.jpa;


import lombok.Data;

import javax.persistence.*;


@Entity
@Table(name = "CustomRevisionEntity")
@Data
public class CustomRevisionEntity extends AbstractEntity {
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    @ManyToOne(fetch = FetchType.LAZY , cascade =  CascadeType.ALL )
    private CustomTrackingEntity customTrackingEntity;

    public CustomTrackingEntity getCustomTrackingEntity() {
        return customTrackingEntity;
    }

    public void setCustomTrackingEntity(CustomTrackingEntity customTrackingEntity) {
        this.customTrackingEntity = customTrackingEntity;
    }
}
