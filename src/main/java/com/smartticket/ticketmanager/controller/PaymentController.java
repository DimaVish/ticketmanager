package com.smartticket.ticketmanager.controller;


import com.smartticket.ticketmanager.dto.PaymentRequestDTO;
import com.smartticket.ticketmanager.dto.PaymentResponseDTO;
import com.smartticket.ticketmanager.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<PaymentResponseDTO> processPayment(@RequestBody PaymentRequestDTO paymentRequest) {
        try {
            PaymentResponseDTO paymentResponse = paymentService.processPayment(paymentRequest);
            if (paymentResponse != null && "success".equals(paymentResponse.getStatus())) {
                return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
            } else {
                // Assumindo que a resposta pode incluir mensagens de erro ou falhas no pagamento
                return new ResponseEntity<>(paymentResponse, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("Error occurred: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
