package com.alex.api_pix_qrcode.service;
import com.alex.api_pix_qrcode.dto.PixRequestDto;
import org.json.JSONObject;
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

    @Value("${KEY}")
    private String key;

    public String createClient(PixRequestDto pix) {

        JSONObject json = new JSONObject();
        json.put("name", pix.name());
        json.put("cpfCnpj", pix.cpfCnpj());

        String jsonBody = json.toString();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api-sandbox.asaas.com/v3/customers"))
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("access_token", key)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = null;

        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(response.body());

        var responseCreateClientJson = new JSONObject(response.body());
        var paymentId = responseCreateClientJson.getString("id");
        var responseCreatePaymentJson = createPayment(paymentId, pix.value());
        System.out.println(responseCreatePaymentJson);

        return responseCreatePaymentJson.get("invoiceUrl").toString();
    }

    public JSONObject createPayment(String id, String value) {

        JSONObject json = new JSONObject();

        json.put("billingType", "PIX" ); // por causa desse tipo não mandamos o valor.
        json.put("customer", id);
        json.put("value", value);
        json.put("dueDate", LocalDate.now().toString());

        String jsonBody = json.toString();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api-sandbox.asaas.com/v3/payments"))
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("access_token", key)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        HttpResponse<String> response = null;

        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(response.body());

        return new JSONObject(response.body());
    }
}
