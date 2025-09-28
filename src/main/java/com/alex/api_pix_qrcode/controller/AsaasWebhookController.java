package com.alex.api_pix_qrcode.controller;

import com.alex.api_pix_qrcode.models.Customer;
import com.alex.api_pix_qrcode.models.Payment;
import com.alex.api_pix_qrcode.repositories.CustomerRepository;
import com.alex.api_pix_qrcode.repositories.PaymentsRepository;
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

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PaymentsRepository paymentsRepository;

    @PostMapping
    public ResponseEntity<String> receberWebhook(@RequestBody Map<String, Object> payload) {

        logger.info(payload.toString());

        String event = (String) payload.get("event");
        Map<String, Object> payment = (Map<String, Object>) payload.get("payment");

        if ("PAYMENT_RECEIVED".equals(event)) {
            String paymentId = (String) payment.get("id");
            String customerName = (String) payment.get("customer");
            Double value = Double.valueOf(payment.get("value").toString());
            // emailService.sendEmailNotification(customerName, value);

            // Busca o cliente pelo nome, se não existir cria
            Customer customer = customerRepository.findByCustomerName(customerName)
                    .orElseGet(() -> customerRepository.save(new Customer(customerName)));

            // Salva o pagamento vinculado ao cliente
            paymentsRepository.save(new Payment(value, paymentId, customer));

            logger.info("Pagamento recebido! ID: " + paymentId + ", Cliente: " + customerName + ", Valor: R$" + value);
        }

        return ResponseEntity.ok("Webhook recebido com sucesso");
    }

}