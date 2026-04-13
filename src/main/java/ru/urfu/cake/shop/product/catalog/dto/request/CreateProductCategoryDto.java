package ru.urfu.cake.shop.product.catalog.dto.request;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;
/**
 * Запрос на создание категории продукта
 */
@Data
@Schema(description = "Запрос на создание категории продукта")
public class CreateProductCategoryDto {
    @Schema(description = "Наименование категории продукта", example = "Торты", required = true)
    private String name;
}
