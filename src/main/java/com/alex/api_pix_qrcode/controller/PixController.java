package com.alex.api_pix_qrcode.controller;
import com.alex.api_pix_qrcode.dto.PixRequestDto;
import com.alex.api_pix_qrcode.dto.PixResponseDto;
import com.alex.api_pix_qrcode.service.EmailService;
import com.alex.api_pix_qrcode.service.PixService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/pix")
public class PixController {

    private static final Logger logger = LoggerFactory.getLogger(PixController.class);

    @Autowired
    private PixService pixService;

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<PixResponseDto> pix(@RequestBody PixRequestDto dto) {
        logger.info("Enpoint PIX acionado. Gerando pix para: " + dto.email());
        var response = pixService.createStaticQRCode();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}