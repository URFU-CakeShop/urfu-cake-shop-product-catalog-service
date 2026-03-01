package ru.urfu.cake.shop.product.catalog.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
/**
 * Продукт в API
 */
@Data
@AllArgsConstructor
public class ProductDto {
    private UUID id;
    private String name;
    private String description;
    private ProductTypeDto type;
}
