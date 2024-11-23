package com.malmitas.backend.model;

import com.malmitas.backend.model.enuns.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String street;
    private String county;
    private String city;
    private String state;
    private String price;
    private int quantity;
    private double latitude;
    private double longitude;

    @Enumerated(EnumType.STRING)
    private Status status; // Status do pedido (nota gerada ou não)

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now(); // Define a data/hora de inclusão automaticamente
    }
}
