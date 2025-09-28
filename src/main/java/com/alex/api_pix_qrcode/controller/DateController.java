package com.alex.api_pix_qrcode.controller;

import com.alex.api_pix_qrcode.models.Customer;
import com.alex.api_pix_qrcode.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments/registry")
public class DateController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<List<Customer>> listAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(customers);
    }

}
