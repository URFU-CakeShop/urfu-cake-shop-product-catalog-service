package ru.urfu.cake.shop.product.catalog.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urfu.cake.shop.product.catalog.service.entity.ProductType;

import java.util.UUID;
/**
 * Хранилище типов продуктов
 */
public interface ProductTypeRepository extends JpaRepository<ProductType, UUID> {
}
