package ru.urfu.cake.shop.product.catalog.dto.request;

import lombok.Data;

import java.util.UUID;
/**
 * Запрос на создание категории продукта
 */
@Data
public class CreateProductCategoryDto {
    private String name;
}
