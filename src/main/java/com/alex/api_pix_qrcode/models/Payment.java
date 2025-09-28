package com.alex.api_pix_qrcode.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK interna

    @Column(name = "payment_id", unique = true, nullable = false)
    private String payment_id; // ID do Asaas

    private Double value;

    @ManyToOne
    @JoinColumn(name = "id_customer", nullable = false)
    @JsonBackReference
    private Customer customer;

    public Payment() {
    }

    public Payment(Double value, String payment_id, Customer customer) {
        this.value = value;
        this.payment_id = payment_id;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
