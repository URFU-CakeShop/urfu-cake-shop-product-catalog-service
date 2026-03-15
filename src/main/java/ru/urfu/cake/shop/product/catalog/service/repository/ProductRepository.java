package ru.urfu.cake.shop.product.catalog.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urfu.cake.shop.product.catalog.service.entity.Product;

import java.util.UUID;
/**
 * Хранилище продуктов
 */
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
