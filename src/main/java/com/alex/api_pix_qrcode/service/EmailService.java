package com.alex.api_pix_qrcode.service;

import com.alex.api_pix_qrcode.dto.PixRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmailService {

    List<String> list = new ArrayList<>();
    private int cont = 0;

    @Autowired
    private JavaMailSender mailSender;

    // Armazena nome e e-mail do cliente
    public void storeUserInfo(PixRequestDto dto) {
        list.add(dto.email());
    }

    // Envia o e-mail de confirmação de pagamento
    public void sendEmailNotification(String customerName, Double value) {
        String email = list.get(cont);
        cont++;
        String subject = "Confirmação de pagamento via Pix";
        // Formata o valor para moeda brasileira
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String formattedValue = formatter.format(value);
        String text = String.format(
                "Olá %s,\n\nRecebemos seu pagamento de %s via Pix.\n\nObrigado por utilizar nosso serviço!\n\nAtenciosamente,\nEquipe Pix QRCode",
                customerName, value
        );
        sendEmail(email, subject, text);
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("alex.diasritter@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
