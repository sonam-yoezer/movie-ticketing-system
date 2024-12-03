package com.java.movieticketingsystem.utils;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class AuditEntity {
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private Long createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private Long updatedBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
//        var authenticatedService = BeanUtils.getBean(IAuthenticatedUserService.class);
        this.createdAt = LocalDateTime.now();
        this.createdBy = 1L;
    }

    @PreUpdate
    public void preUpdate() {
//        var authenticatedService = BeanUtils.getBean(IAuthenticatedUserService.class);
        this.updatedBy = 1L;
    }
}
