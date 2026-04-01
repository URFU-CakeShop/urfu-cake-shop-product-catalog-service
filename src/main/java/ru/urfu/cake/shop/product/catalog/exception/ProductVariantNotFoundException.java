package ru.urfu.cake.shop.product.catalog.exception;

import java.util.UUID;
/**
 * Указанная разновидность продукта не найдена
 */
public class ProductVariantNotFoundException extends RuntimeException {
    /**
     * Конструктор исключения
     *
     * @param productVariantId Идентификатор разновидности продукта
     */
    public ProductVariantNotFoundException(UUID productVariantId) {
        super("Product variant not found = " + productVariantId);
    }
}