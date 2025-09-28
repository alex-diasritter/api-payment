package com.alex.api_pix_qrcode.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer")
    private long id_customer;

    @Column(name = "customer_name", unique = true)
    private String customerName;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<Payment> payments;

    public Customer() {
    }

    public Customer(String customerName) {
        this.customerName = customerName;
    }

    public long getIdCustomer() {
        return id_customer;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<Payment> getPayments() {
        return payments;
    }
}
