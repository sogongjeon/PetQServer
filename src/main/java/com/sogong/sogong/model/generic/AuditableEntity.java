package com.sogong.sogong.model.generic;

import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditableEntity extends BaseEntity {
    private static final long serialVersionUID = -8004805937272414953L;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedBy
    private String modifiedBy;

    @LastModifiedDate
    private LocalDateTime modifiedAt = LocalDateTime.now();

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object == this) {
            return true;
        }

        if (Hibernate.getClass(object) != Hibernate.getClass(this)) {
            return false;
        }

        AuditableEntity entity = (AuditableEntity) object;
        Long id = getId();

        if (id == null) {
            return false;
        }

        return id.equals(entity.getId());
    }

    @Override
    public int hashCode() {
        int hash = 7;

        Long id = getId();
        hash = 31 * hash + ((id == null) ? 0 : id.hashCode());

        return hash;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}