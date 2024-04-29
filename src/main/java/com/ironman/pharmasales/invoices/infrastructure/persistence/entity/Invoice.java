package com.ironman.pharmasales.invoices.infrastructure.persistence.entity;

import com.ironman.pharmasales.clients.infrastructure.persistence.entity.Client;
import com.ironman.pharmasales.users.infrastructure.persistence.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_date")
    private LocalDateTime invoiceDate;

    @Column(name = "client_id")
    private Long clientId;

    @ManyToOne
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    private Client client;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;

    private String state;

    @OneToMany(mappedBy = "invoice", cascade = {CascadeType.PERSIST}, orphanRemoval = true)
    private List<InvoiceDetail> invoiceDetails;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
