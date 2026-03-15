package ru.urfu.cake.shop.product.catalog.service.service;

import ru.urfu.cake.shop.product.catalog.service.dto.request.CreateProductCategoryDto;
import ru.urfu.cake.shop.product.catalog.service.dto.request.CreateProductDto;
import ru.urfu.cake.shop.product.catalog.service.entity.Product;
import ru.urfu.cake.shop.product.catalog.service.entity.ProductCategory;

import java.util.UUID;
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
    /**
     * Создать категорию продуктов
     *
     * @param request Запрос
     * @return Категория продуктов
     */
    ProductCategory create(CreateProductCategoryDto request);
    /**
     * Привязать категорию продуктов к другой категории
     *
     * @param sourceCategoryId Исходная категория продуктов
     * @param targetCategoryId Категория продуктов для привязки
     */
    void attach(UUID sourceCategoryId, UUID targetCategoryId);
    /**
     * Отвязать родителя указанной категории продукта
     *
     * @param categoryId Идентификатор категории продуктов
     */
    void detach(UUID categoryId);
}
