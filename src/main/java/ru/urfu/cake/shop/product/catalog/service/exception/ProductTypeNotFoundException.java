package ru.urfu.cake.shop.product.catalog.service.exception;

import java.util.UUID;
/**
 * Указанный тип продукта не найден
 */
public class ProductTypeNotFoundException extends RuntimeException {
    /**
     * Конструктор исключения
     *
     * @param productTypeId Идентификатор типа продукта
     */
    public ProductTypeNotFoundException(UUID productTypeId) {
        super("Product type is not found = " + productTypeId);
    }
}
