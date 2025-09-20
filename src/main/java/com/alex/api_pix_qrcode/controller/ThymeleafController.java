package com.alex.api_pix_qrcode.controller;

import com.alex.api_pix_qrcode.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeleafController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        String email = userDetailsService.getUserEmail(authentication.getName());
        model.addAttribute("userEmail", email);
        return "pagamentos";
    }
}

