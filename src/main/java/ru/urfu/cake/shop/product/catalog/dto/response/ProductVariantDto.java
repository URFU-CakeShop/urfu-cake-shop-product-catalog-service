package ru.urfu.cake.shop.product.catalog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
/**
 * Разновидность продукта в API
 */
@Data
@AllArgsConstructor
public class ProductVariantDto {
    @Schema(description = "Идентификатор варианта продукта", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;
    @Schema(description = "Идентификатор продукта", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID productId;
    @Schema(description = "SKU (артикул) варианта продукта", example = "CAKE-001")
    private String sku;
    @Schema(description = "Цена варианта продукта", example = "1250.50")
    private BigDecimal price;
    @Schema(description = "Валюта цены", example = "RUB")
    private String currency;
    @Schema(description = "Вес варианта продукта в граммах", example = "1000.0")
    private double weight;
    @Schema(description = "Флаг активности варианта продукта", example = "true")
    private boolean isActive;
    @Schema(description = "Дата и время создания записи", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;
    @Schema(description = "Дата и время последнего обновления записи", example = "2024-01-15T10:30:00")
    private LocalDateTime updatedAt;
    @Schema(description = "Версия записи", example = "1")
    private Integer version;
}