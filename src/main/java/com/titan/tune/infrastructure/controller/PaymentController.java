package com.titan.tune.infrastructure.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    @PostMapping("/payment/mobile-money/initiate")
    public ResponseEntity<?> initiateMobileMoney() {
        return ResponseEntity.ok().build(); // À implémenter avec un service de paiement
    }

    @PostMapping("/payment/mobile-money/confirm")
    public ResponseEntity<?> confirmMobileMoney() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/subscription/premium")
    public ResponseEntity<?> subscribePremium() {
        return ResponseEntity.ok().build();
    }
}