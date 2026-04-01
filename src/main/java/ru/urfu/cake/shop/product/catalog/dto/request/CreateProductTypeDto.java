package ru.urfu.cake.shop.product.catalog.dto.request;

import lombok.Data;
/**
 * Запрос на создание типа продукта
 */
@Data
public class CreateProductTypeDto {
    private String name;
}
