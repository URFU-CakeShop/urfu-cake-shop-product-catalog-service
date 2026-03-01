package ru.urfu.cake.shop.product.catalog.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
/**
 * Тип продукта в API
 */
@Data
@AllArgsConstructor
public class ProductTypeDto {
    private UUID id;
    private String name;
}
