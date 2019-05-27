package com.experts.core.biller.statemachine.api.rovo.awsxray.domain.entities.mongo;

import com.experts.core.biller.statemachine.api.rovo.awsxray.security.AESEncryptor;
import com.experts.core.biller.statemachine.api.rovo.awsxray.utils.EncryptedString;
import com.google.common.base.Strings;
import org.mongodb.morphia.annotations.*;

import java.util.List;

@Entity(value = "user", noClassnameStored = true)
@Indexes(@Index(fields = {@Field("userId")}, options = @IndexOptions(unique = true)))
public class UserEntity extends BaseEntity implements SaltHash {

    private static final long serialVersionUID = 1L;

    private String userId;

    @Transient
    private String userKey; // Set with KeyGenerator.generateUserKey(userId)
    @Embedded
    private EncryptedString userKeyEncrypted;

    @Transient
    private String password;
    private String passwordHash;

    private String firstName;
    private String lastName;

    private List<String> roles;

    @Reference(lazy = true)
    private CompanyEntity company;

    public UserEntity() {
        super();
        disabled = false;
    }

    public UserEntity(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public UserEntity(String userId, String password, String userKey) {
        this.userId = userId;
        this.password = password;
        this.userKey = userKey;
    }

    public String getUserId() {
        return this.userId;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public String getPasswordHash() {
        return passwordHash;
    }

    @Override
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<String> getRoles() {
        return this.roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getUserKey() {
        if ((userKey == null) && (userKeyEncrypted != null)) {
            userKey = AESEncryptor.decrypt(userKeyEncrypted.getEncryptedAttribute(), userKeyEncrypted.getSalt());
        }
        return this.userKey;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    @Override
    @PrePersist
    public void prePersist() {
        super.prePersist();

        // Only store the email in lowercase so we can do exact matches
        this.userId = Strings.nullToEmpty(this.userId).toLowerCase();

        // Let the encryption do its magic
        if (userKey != null) {
            if (userKeyEncrypted == null) {
                userKeyEncrypted = new EncryptedString();
            }
            userKeyEncrypted
                    .setEncryptedAttribute(AESEncryptor.encrypt(userKey, userKeyEncrypted.getSalt()));
        }
    }

    @Override
    public String toString() {
        return "UserEntity [uuid=" + this.uuid + ", userId=" + this.userId + ", userKey=" + this.userKey
               + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", role=" + this.roles + "]";
    }
}
