package ru.urfu.cake.shop.product.catalog.service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;
/**
 * Тип продукта
 */
@Entity
@Table(name = "product_type")
@Data
public class ProductType {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    private String name;
}
