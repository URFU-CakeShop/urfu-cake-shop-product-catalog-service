package ru.urfu.cake.shop.product.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urfu.cake.shop.product.catalog.entity.ProductVariant;

import java.util.List;
import java.util.UUID;
/**
 * Хранилище разновидностей продуктов
 */
public interface ProductVariantRepository extends JpaRepository<ProductVariant, UUID> {
    List<ProductVariant> findByProductId(UUID productId);
}