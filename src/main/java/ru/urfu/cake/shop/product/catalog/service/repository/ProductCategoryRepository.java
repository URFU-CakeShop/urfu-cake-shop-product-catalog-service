package ru.urfu.cake.shop.product.catalog.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urfu.cake.shop.product.catalog.service.entity.ProductCategory;

import java.util.UUID;
/**
 * Хранилище категорий продуктов
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, UUID> {
}
