package com.example.claim.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.claim.model.Claim;
import com.example.claim.model.ClaimStatus;
import com.example.claim.model.User;
import com.example.claim.repository.ClaimRepository;
import com.example.claim.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/claims")
public class ClaimController {

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private UserRepository userRepository;

    @Operation(
            summary = "Submit a new claim",
            description = "This API allows a user to submit a claim with amount and details."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Claim submitted successfully",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
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

    
    

    @GetMapping("/byUser/{claimantId}")
    public List<Claim> fetchClaimDetailsbyClaimantId(@PathVariable Long claimantId) {
        return claimRepository.findByClaimantId(claimantId);
    }
    
    @GetMapping("/testurl")
    public String getMessage() {
        return "Test Response";
    }
}
