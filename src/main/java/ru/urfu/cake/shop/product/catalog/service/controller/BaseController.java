package ru.urfu.cake.shop.product.catalog.service.controller;

import ru.urfu.cake.shop.product.catalog.service.dto.response.ProductDto;
import ru.urfu.cake.shop.product.catalog.service.dto.response.ProductTypeDto;
import ru.urfu.cake.shop.product.catalog.service.entity.Product;
import ru.urfu.cake.shop.product.catalog.service.entity.ProductType;
/**
 * Базовый класс для построения контроллеров
 */
public abstract class BaseController {
    /**
     * Привести продукт к DTO
     *
     * @param product Продукт
     * @return DTO
     */
    public static ProductDto toDto(Product product) {
        return new ProductDto(
            product.getId(),
            product.getName(),
            product.getDescription(),
            toDto(product.getType())
        );
    }
    /**
     * Привести тип продукта к DTO
     *
     * @param productType Тип продукта
     * @return DTO
     */
    public static ProductTypeDto toDto(ProductType productType) {
        return new ProductTypeDto(
            productType.getId(),
            productType.getName()
        );
    }
}
