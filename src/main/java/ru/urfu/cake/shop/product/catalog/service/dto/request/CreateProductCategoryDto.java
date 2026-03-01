package ru.urfu.cake.shop.product.catalog.service.dto.request;

import lombok.Data;

import java.util.UUID;
/**
 * Запрос на создание категории продукта
 */
@Data
public class CreateProductCategoryDto {
    private UUID id;
    private String name;
}
