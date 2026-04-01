package ru.urfu.cake.shop.product.catalog;

import ru.urfu.cake.shop.product.catalog.dto.request.CreateProductVariantDto;
import ru.urfu.cake.shop.product.catalog.entity.ProductVariant;

import java.util.List;
import java.util.UUID;
/**
 * Сервис разновидностей продуктов
 */
public interface ProductVariantService {
    /**
     * Создать разновидность продукта
     *
     * @param request Запрос
     * @return Разновидность продукта
     */
    ProductVariant create(CreateProductVariantDto request);
    /**
     * Получить разновидность продукта по идентификатору
     *
     * @param id Идентификатор
     * @return Разновидность продукта
     */
    ProductVariant getById(UUID id);
    /**
     * Получить все разновидности продукта по идентификатору продукта
     *
     * @param productId Идентификатор продукта
     * @return Список разновидностей продукта
     */
    List<ProductVariant> getByProductId(UUID productId);
}