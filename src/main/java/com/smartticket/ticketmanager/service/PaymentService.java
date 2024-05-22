package com.smartticket.ticketmanager.service;

import com.smartticket.ticketmanager.dto.PaymentRequestDTO;
import com.smartticket.ticketmanager.dto.PaymentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final RestTemplate restTemplate;

    private static final String PAYMENT_PROVIDER_URL = "https://api.paymentprovider.com/payments";

    public PaymentResponseDTO processPayment(PaymentRequestDTO paymentRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(paymentRequest.getApiToken());

        HttpEntity<PaymentRequestDTO> requestEntity = new HttpEntity<>(paymentRequest, headers);

        ResponseEntity<PaymentResponseDTO> responseEntity = restTemplate.postForEntity(PAYMENT_PROVIDER_URL, requestEntity, PaymentResponseDTO.class);
        return responseEntity.getBody();
    }
}