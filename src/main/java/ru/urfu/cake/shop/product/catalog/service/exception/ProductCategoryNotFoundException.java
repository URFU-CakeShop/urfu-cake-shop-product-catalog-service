package ru.urfu.cake.shop.product.catalog.service.exception;

import java.util.UUID;
/**
 * Указанная категория продукта не найдена
 */
public class ProductCategoryNotFoundException extends RuntimeException {
    /**
     * Конструктор исключения
     *
     * @param productTypeId Идентификатор категории продукта
     */
    public ProductCategoryNotFoundException(UUID productTypeId) {
        super("Category not found = " + productTypeId);
    }
}
