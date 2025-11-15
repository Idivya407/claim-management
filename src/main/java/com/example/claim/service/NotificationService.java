package com.example.claim.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    public void notifyUser(String to, String message) {
        // For demo: just log to console. Integrate email/SMS providers in real app.
        System.out.println("Notify " + to + ": " + message);
    }
}
