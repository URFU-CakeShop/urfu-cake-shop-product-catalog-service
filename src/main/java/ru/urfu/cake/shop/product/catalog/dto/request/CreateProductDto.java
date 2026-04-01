package ru.urfu.cake.shop.product.catalog.dto.request;

import lombok.Data;

import java.util.List;
import java.util.UUID;
/**
 * Запрос на создание продукта
 */
@Data
public class CreateProductDto {
    private String name;
    private String description;
    private UUID typeId;
    private List<UUID> categoryIdList;
}
