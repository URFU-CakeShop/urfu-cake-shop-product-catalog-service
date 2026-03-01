package ru.urfu.cake.shop.product.catalog.service.service;

import ru.urfu.cake.shop.product.catalog.service.dto.request.CreateProductDto;
import ru.urfu.cake.shop.product.catalog.service.entity.Product;
/**
 * Сервис продуктов
 */
public interface ProductService {
    /**
     * Создать продукт
     *
     * @param request Запрос
     * @return Продукт
     */
    Product create(CreateProductDto request);
}
