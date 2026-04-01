package ru.urfu.cake.shop.product.catalog.exception;

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
        super("Type not found = " + productTypeId);
    }
}
