package com.alex.api_pix_qrcode.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PixResponseDto {

    private String id;
    private String encodedImage;
    private String payload;
    private boolean allowsMultiplePayments;
    private String expirationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public boolean isAllowsMultiplePayments() {
        return allowsMultiplePayments;
    }

    public void setAllowsMultiplePayments(boolean allowsMultiplePayments) {
        this.allowsMultiplePayments = allowsMultiplePayments;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
