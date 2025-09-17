package com.alex.api_pix_qrcode.controller;
import com.alex.api_pix_qrcode.dto.PixRequestDto;
import com.alex.api_pix_qrcode.service.PixService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/pix")
public class PixController {

    @Autowired
    private PixService pixService;

    @GetMapping("/pagamento")
    public String pagamento() {
        // retorna o nome do arquivo HTML sem extensão
        return "pagamentos";
    }

    @PostMapping
    public ResponseEntity pix(@RequestBody @Valid PixRequestDto client) {
        var response = pixService.createClientAndPaymentPix(client);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}