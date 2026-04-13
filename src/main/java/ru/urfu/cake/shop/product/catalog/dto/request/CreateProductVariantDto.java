package ru.urfu.cake.shop.product.catalog.dto.request;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;
/**
 * Запрос на создание разновидности продукта
 */
@Data
@Schema(description = "Запрос на создание варианта продукта")
public class CreateProductVariantDto {
    @Schema(description = "Идентификатор продукта", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
    private UUID productId;
    @Schema(description = "SKU (артикул) варианта продукта", example = "CAKE-001", required = true)
    private String sku;
    @Schema(description = "Цена варианта продукта", example = "1250.50", required = true)
    private BigDecimal price;
    @Schema(description = "Валюта цены", example = "RUB", required = true)
    private String currency;
    @Schema(description = "Вес варианта продукта в граммах", example = "1000.0", required = true)
    private double weight;
    @Schema(description = "Флаг активности варианта продукта", example = "true")
    private boolean isActive = true;
}