package ru.urfu.cake.shop.product.catalog.dto.request;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * Запрос на создание типа продукта
 */
@Data
public class CreateProductTypeDto {
    @Schema(description = "Наименование типа продукта", example = "Десерт", required = true)
    private String name;
}
