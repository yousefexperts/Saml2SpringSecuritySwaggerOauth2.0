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
@Table(name  = "store_item_image")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL , region =  "com.experts.core.biller.statemachine.api.model.domain.jpa.hr.engine.Images")
public class Images implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy="uuid2")
    private UUID id;


    @Column(name  = "file_name" , nullable = false)
    private String fileName;

    @Column(name  = "image_content" , nullable = false)
    private byte[] content;

    @Column(name  = "file_ext" , nullable = false)
    private String fileExtension;


    @Column(name  = "notes" , nullable = true)
    private String notes;

    @Column(name  = "description" , nullable = true)
    private String description;


    @LastModifiedDate
    private Date creationDate;

    @LastModifiedBy
    private UsersCore usersCore;


    @Version
    private int version;

    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id" , nullable = false)
    private Items items;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
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

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }
}
