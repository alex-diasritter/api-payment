package com.alex.api_pix_qrcode.dto;
import jakarta.validation.constraints.NotNull;

public record PixRequestDto(@NotNull String name, @NotNull String cpfCnpj, String value){}
