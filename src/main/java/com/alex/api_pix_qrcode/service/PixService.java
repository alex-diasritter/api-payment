package com.alex.api_pix_qrcode.service;
import com.alex.api_pix_qrcode.dto.PixRequestDto;
import com.alex.api_pix_qrcode.dto.PixResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

@Service
public class PixService {

    private static final Logger logger = LoggerFactory.getLogger(PixService.class);

    @Value("${KEY}")
    private String KEY;

    private static final String USER_AGENT = "MeuAppPIX/1.0";

    // cria qr code estático, não sendo necessário criar um cliente e uma cobrança.
    // o cliente ao usar escanear o qr code escolhe o valor que será transferido.
    public PixResponseDto createStaticQRCode() {

        logger.info("Método createStaticQRCode acionado.");

        JSONObject json = new JSONObject();

        json.put("addressKey", "0f0c9151-c05f-4c3e-80df-5b68e64a2c9c");
        json.put("format", "ALL");
        json.put("allowsMultiplePayments", false);
        json.put("expirationSeconds", 1000);

        String jsonBody = json.toString();

        logger.info("JSON para requisição gerado: " + jsonBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.asaas.com/v3/pix/qrCodes/static"))
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("access_token", KEY)
                .header("User-Agent", USER_AGENT)
                .method("POST", HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();


        HttpResponse<String> response = null;

        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            PixResponseDto pixDto = objectMapper.readValue(response.body(), PixResponseDto.class);
            return pixDto;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
