package com.example.claim.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.claim.model.Claim;
import com.example.claim.model.ClaimStatus;
import com.example.claim.model.User;
import com.example.claim.repository.ClaimRepository;
import com.example.claim.repository.UserRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ClaimService {
    private static final Logger logger = LoggerFactory.getLogger(ClaimService.class);

    private final ClaimRepository claimRepository;
    private final UserRepository userRepository;

    // Submit Claim
    public Claim submitClaim(Long userId, Claim claim) {
        logger.info("Submitting claim for userId={}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found for id={}", userId);
                    return new RuntimeException("User not found");
                });

        logger.debug("User found: {}", user.getUsername());

        claim.setClaimant(user);
        claim.setStatus(ClaimStatus.SUBMITTED);

        Claim saved = claimRepository.save(claim);

        logger.info("Claim submitted successfully. claimId={}", saved.getId());
        return saved;
    }

    // Update Claim Amount
    public Claim updateClaimAmount(Long claimId, Double newAmount) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        claim.setAmount(newAmount);
        return claimRepository.save(claim);
    }

    // Update selected fields
    public Map<String, Object> updateClaim(Long claimId, Claim updatedClaim) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        Map<String, Object> changedFields = new HashMap<>();

        if (updatedClaim.getHospital() != null
                && !updatedClaim.getHospital().equals(claim.getHospital())) {
            claim.setHospital(updatedClaim.getHospital());
            changedFields.put("hospital", updatedClaim.getHospital());
        }

        if (updatedClaim.getTreatment() != null
                && !updatedClaim.getTreatment().equals(claim.getTreatment())) {
            claim.setTreatment(updatedClaim.getTreatment());
            changedFields.put("treatment", updatedClaim.getTreatment());
        }

        if (updatedClaim.getAmount() != null
                && !updatedClaim.getAmount().equals(claim.getAmount())) {
            claim.setAmount(updatedClaim.getAmount());
            changedFields.put("amount", updatedClaim.getAmount());
        }

        if (updatedClaim.getRejectionReason() != null
                && !updatedClaim.getRejectionReason().equals(claim.getRejectionReason())) {
            claim.setRejectionReason(updatedClaim.getRejectionReason());
            changedFields.put("rejectionReason", updatedClaim.getRejectionReason());
        }

        claimRepository.save(claim);
        return changedFields;
    }

    // Fetch all claims by user
    public List<Claim> fetchClaimsByUser(Long userId) {
        return claimRepository.findByClaimantId(userId);
    }
}
