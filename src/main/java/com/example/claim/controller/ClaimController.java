package com.example.claim.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.example.claim.model.Claim;
import com.example.claim.service.ClaimService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/claims")
@RequiredArgsConstructor
public class ClaimController {

    private final ClaimService claimService;

    @Operation(
            summary = "Submit a new claim",
            description = "This API allows a user to submit a claim with amount and details."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Claim submitted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/submit/{userId}")
    public Claim submitClaim(@PathVariable Long userId, @RequestBody Claim claim) {
        return claimService.submitClaim(userId, claim);
    }

    @PatchMapping("/{claimId}/updateAmount")
    public Claim updateClaimAmount(@PathVariable Long claimId, @RequestBody Double newAmount) {
        return claimService.updateClaimAmount(claimId, newAmount);
    }

    @PutMapping("/{claimId}")
    public Map<String, Object> updateClaim(
            @PathVariable Long claimId,
            @RequestBody Claim updatedClaim) {
        return claimService.updateClaim(claimId, updatedClaim);
    }

    @GetMapping("/byUser/{claimantId}")
    public List<Claim> fetchClaimDetailsByUser(@PathVariable Long claimantId) {
        return claimService.fetchClaimsByUser(claimantId);
    }

    @GetMapping("/testurl")
    public String getMessage() {
        return "Test Response";
    }
}
