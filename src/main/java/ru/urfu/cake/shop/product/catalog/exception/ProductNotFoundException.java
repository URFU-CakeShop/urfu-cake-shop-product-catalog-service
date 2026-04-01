package ru.urfu.cake.shop.product.catalog.exception;

import java.util.UUID;
/**
 * Указанный продукт не найден
 */
public class ProductNotFoundException extends RuntimeException {
    /**
     * Конструктор исключения
     *
     * @param productId Идентификатор продукта
     */
    public ProductNotFoundException(UUID productId) {
        super("Product not found = " + productId);
    }
}