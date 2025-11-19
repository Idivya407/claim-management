package com.example.claim.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hospital;
    private String treatment;
    private Double amount;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime submittedAt;

    @CreatedBy
    @Column(updatable = false)
    private String submittedBy;
    
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
      
    
    @LastModifiedBy
    private String updatedBy;
    
    @Enumerated(EnumType.STRING)
    private ClaimStatus status;

    private String rejectionReason;

    @ManyToOne
    private User claimant;
}
