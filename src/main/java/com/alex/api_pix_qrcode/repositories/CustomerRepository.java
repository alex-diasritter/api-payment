package com.alex.api_pix_qrcode.repositories;

import com.alex.api_pix_qrcode.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByCustomerName(String customerName);

}
