package ru.urfu.cake.shop.product.catalog.service.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;
/**
 * Запрос на создание разновидности продукта
 */
@Data
public class CreateProductVariantDto {
    private UUID productId;
    private String sku;
    private BigDecimal price;
    private String currency;
    private double weight;
    private boolean isActive = true;
}