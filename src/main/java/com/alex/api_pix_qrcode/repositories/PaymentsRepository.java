package com.alex.api_pix_qrcode.repositories;

import com.alex.api_pix_qrcode.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsRepository extends JpaRepository<Payment, Long> {
}
