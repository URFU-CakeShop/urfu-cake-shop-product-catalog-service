package ru.urfu.cake.shop.product.catalog.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;
/**
 * Категория продукта в API
 */
@Data
@AllArgsConstructor
public class ProductCategoryDto {
    private UUID id;
    private String name;
    private UUID parent;
    private List<UUID> subcategories;
}
