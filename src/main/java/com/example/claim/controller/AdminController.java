package com.example.claim.controller;

import com.example.claim.model.Claim;
import com.example.claim.model.ClaimStatus;
import com.example.claim.repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ClaimRepository claimRepository;

    @PostMapping("/review/{c"
    		+ "laimId}")
    public Claim review(@PathVariable Long claimId, @RequestParam boolean approve, @RequestParam(required=false) String reason) {
        Claim claim = claimRepository.findById(claimId).orElseThrow();
        if (approve) {
            claim.setStatus(ClaimStatus.APPROVED);
        } else {
            claim.setStatus(ClaimStatus.REJECTED);
            claim.setRejectionReason(reason);
        }
        return claimRepository.save(claim);
    }
}
