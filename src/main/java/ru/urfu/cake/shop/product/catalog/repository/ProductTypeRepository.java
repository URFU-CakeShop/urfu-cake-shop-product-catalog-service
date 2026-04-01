package ru.urfu.cake.shop.product.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urfu.cake.shop.product.catalog.entity.ProductType;

import java.util.UUID;
/**
 * Хранилище типов продуктов
 */
public interface ProductTypeRepository extends JpaRepository<ProductType, UUID> {
}
