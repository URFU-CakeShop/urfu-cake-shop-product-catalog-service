package ru.urfu.cake.shop.product.catalog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
/**
 * Разновидность продукта в API
 */
@Data
@AllArgsConstructor
public class ProductVariantDto {
    private UUID id;
    private UUID productId;
    private String sku;
    private BigDecimal price;
    private String currency;
    private double weight;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer version;
}