package ru.urfu.cake.shop.product.catalog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;
/**
 * Категория продукта в API
 */
@Data
@AllArgsConstructor
@Schema(description = "Категория продукта в API")
public class ProductCategoryDto {
    @Schema(description = "Идентификатор категории продукта", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;
    @Schema(description = "Наименование категории продукта", example = "Торты")
    private String name;
    @Schema(description = "Идентификатор родительской категории (null если корневая)", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID parent;
    @Schema(description = "Список идентификаторов подкатегорий")
    private List<UUID> subcategories;
}