package com.alex.api_pix_qrcode.controller;
import com.alex.api_pix_qrcode.dto.PixResponseDto;
import com.alex.api_pix_qrcode.security.CustomUserDetailsService;
import com.alex.api_pix_qrcode.service.EmailService;
import com.alex.api_pix_qrcode.service.PixService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/pix")
public class PixController {

    private static final Logger logger = LoggerFactory.getLogger(PixController.class);

    @Autowired
    private PixService pixService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping
    public ResponseEntity<PixResponseDto> pix(Authentication authentication) {

        logger.info("Enpoint PIX acionado. Gerando pix para: " + authentication.getName());

        var qrcode = pixService.createStaticQRCode();

        emailService.storeUserInfo(userDetailsService.getUserEmail(authentication.getName()));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(qrcode);
    }
}