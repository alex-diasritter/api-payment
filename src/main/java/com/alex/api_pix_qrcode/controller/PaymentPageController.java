package com.alex.api_pix_qrcode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentPageController {

    @GetMapping("/")
    public String home() {
        return "pagamentos"; // retorna pagamentos.html
    }
}

