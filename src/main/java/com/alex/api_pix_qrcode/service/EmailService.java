package com.alex.api_pix_qrcode.service;

import com.alex.api_pix_qrcode.dto.PixRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class EmailService {

    private final Map<String, Double> paymentMap = new HashMap<>();
    private final Map<String, String> emailMap = new HashMap<>();

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // Armazena nome e valor do pagamento
    public void storePaymentInfo(String customerName, Double value) {
        paymentMap.put(customerName, value);
    }

    // Armazena nome e e-mail do cliente
    public void storeUserInfo(PixRequestDto dto) {
        emailMap.put(dto.name(), dto.email());
    }

    // Envia o e-mail de confirmação de pagamento
    public void sendEmailNotification(String customerName) {
        String email = emailMap.get(customerName);
        Double value = paymentMap.get(customerName);

        if (email != null && value != null) {
            String subject = "Confirmação de pagamento via Pix";

            // Formata o valor para moeda brasileira
            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            String formattedValue = formatter.format(value);

            String text = String.format(
                    "Olá %s,\n\nRecebemos seu pagamento de %s via Pix.\n\nObrigado por utilizar nosso serviço!\n\nAtenciosamente,\nEquipe Pix QRCode",
                    customerName, formattedValue
            );

            sendEmail(email, subject, text);
        }
    }

    // Método genérico para envio de e-mail
    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("seuemail@gmail.com"); // Altere para seu e-mail real
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
