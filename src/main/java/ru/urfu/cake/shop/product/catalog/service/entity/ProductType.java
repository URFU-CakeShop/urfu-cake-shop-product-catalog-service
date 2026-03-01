package ru.urfu.cake.shop.product.catalog.service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "product_type")
@Data
public class ProductType {
    @Id
    private UUID id;
    private String name;
}
