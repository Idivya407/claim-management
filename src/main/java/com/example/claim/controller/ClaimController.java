package com.example.claim.controller;

import com.example.claim.model.Claim;
import com.example.claim.model.ClaimStatus;
import com.example.claim.model.User;
import com.example.claim.repository.ClaimRepository;
import com.example.claim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
        claim.setSubmittedAt(LocalDateTime.now());
        return claimRepository.save(claim);
    }

    @GetMapping("/byUser/{userId}")
    public List<Claim> byUser(@PathVariable Long userId) {
        return claimRepository.findByClaimantId(userId);
    }
}
