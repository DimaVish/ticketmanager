package com.smartticket.ticketmanager.controller;

import com.smartticket.ticketmanager.repository.entities.Fine;
import com.smartticket.ticketmanager.service.FineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fines")
public class FineController {


    private final FineService fineService;

    @GetMapping("/user/{userId}")
    public List<Fine> getFinesForUser(@PathVariable Long userId) {
        return fineService.getFinesForUser(userId);
    }

    @PostMapping("/create/{userId}")
    public Fine createFine(@PathVariable Long userId, @RequestParam BigDecimal value) {
        return fineService.createFine(userId, value);
    }

    @PostMapping("/pay/{fineId}")
    public Fine payFine(@PathVariable Long fineId, @RequestParam String paymentMethod) {
        return fineService.payFine(fineId, paymentMethod);
    }
}
