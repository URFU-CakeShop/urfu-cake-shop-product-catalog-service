package ru.urfu.cake.shop.product.catalog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;
/**
 * Тип продукта в API
 */
@Data
@AllArgsConstructor
@Schema(description = "Тип продукта в API")
public class ProductTypeDto {
    @Schema(description = "Идентификатор типа продукта", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;
    @Schema(description = "Наименование типа продукта", example = "Десерт")
    private String name;
}
