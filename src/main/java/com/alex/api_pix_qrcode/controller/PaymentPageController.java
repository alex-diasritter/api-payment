package com.alex.api_pix_qrcode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentPageController {

    @GetMapping("/pagamento")
    public String pagamento() {
        // retorna o nome do arquivo HTML sem extensão
        return "pagamentos";  // src/main/resources/templates/pagamentos.html
    }


    @GetMapping("/")
    public String home() {
        return "pagamentos"; // retorna pagamentos.html
    }
}

