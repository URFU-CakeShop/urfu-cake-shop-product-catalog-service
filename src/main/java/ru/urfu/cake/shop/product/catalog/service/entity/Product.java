package ru.urfu.cake.shop.product.catalog.service.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;
/**
 * Продукт
 */
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
    @ManyToOne(fetch = FetchType.EAGER)
    private ProductType type;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "product_categories",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private ProductCategory category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer version;
}
