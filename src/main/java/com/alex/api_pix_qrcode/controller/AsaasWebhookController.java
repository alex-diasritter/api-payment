package com.alex.api_pix_qrcode.controller;

import com.alex.api_pix_qrcode.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/webhook/asaas")
public class AsaasWebhookController {

    private static final Logger logger = LoggerFactory.getLogger(AsaasWebhookController.class);

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<String> receberWebhook(@RequestBody Map<String, Object> payload) {

        String event = (String) payload.get("event");
        Map<String, Object> payment = (Map<String, Object>) payload.get("payment");

        if ("PAYMENT_RECEIVED".equals(event)) {
            String paymentId = (String) payment.get("id");
            String customerName = (String) payment.get("customer");
            Double value = Double.valueOf(payment.get("value").toString());

            emailService.storePaymentInfo(customerName, value);
            emailService.sendEmailNotification(customerName);

            logger.info("Pagamento recebido! ID: " + paymentId + ", Cliente: " + customerName + ", Valor: R$" + value);
        }

        return ResponseEntity.ok("Webhook recebido com sucesso");
    }
}
