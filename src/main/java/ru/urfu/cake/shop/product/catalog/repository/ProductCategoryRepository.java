package ru.urfu.cake.shop.product.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urfu.cake.shop.product.catalog.entity.ProductCategory;

import java.util.UUID;
/**
 * Хранилище категорий продуктов
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, UUID> {
}
