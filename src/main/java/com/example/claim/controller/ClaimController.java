package com.example.claim.controller;

import com.example.claim.model.Claim;
import com.example.claim.model.ClaimStatus;
import com.example.claim.model.User;
import com.example.claim.repository.ClaimRepository;
import com.example.claim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/claims")
public class ClaimController {

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/submit/{userId}")
    public Claim submitClaim(@PathVariable Long userId, @RequestBody Claim claim) {
        User user = userRepository.findById(userId).orElseThrow();
        claim.setClaimant(user);
        claim.setStatus(ClaimStatus.SUBMITTED);
        //claim.setSubmittedAt(LocalDateTime.now());
        return claimRepository.save(claim);
    }
    
    
    @PatchMapping("/{claimId}/updateAmount")
    public Claim updateClaimAmount(@PathVariable Long claimId, @RequestBody Double newAmount) {
        Claim claim = claimRepository.findById(claimId).orElseThrow();
        claim.setAmount(newAmount);
        return claimRepository.save(claim);
    }
    
    @PutMapping("/{claimId}")
    public Map<String, Object> updateClaim(
            @PathVariable Long claimId,
            @RequestBody Claim updatedClaim) {

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

        return changedFields; // returns only what changed
    }

    
    

    @GetMapping("/byUser/{userId}")
    public List<Claim> byUser(@PathVariable Long userId) {
        return claimRepository.findByClaimantId(userId);
    }
    
    @GetMapping("/testurl")
    public String getMessage() {
        return "Test Response";
    }
}
