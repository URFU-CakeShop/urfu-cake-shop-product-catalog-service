package ru.urfu.cake.shop.product.catalog.service.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "product")
@Entity
@Data
public class Product {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID id;
    private String name;
    private String description;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "product_product_type",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "product_type_id")
    )
    private ProductType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer version;
}
