package ru.urfu.cake.shop.product.catalog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;
/**
 * Продукт в API
 */
@Data
@AllArgsConstructor
public class ProductDto {
    @Schema(description = "Идентификатор продукта", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;
    @Schema(description = "Наименование продукта", example = "Торт Наполеон")
    private String name;
    @Schema(description = "Описание продукта", example = "Слоеный торт с заварным кремом")
    private String description;
    @Schema(description = "Идентификатор типа продукта", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID typeId;
    @Schema(description = "Список идентификаторов категорий продукта")
    private List<UUID> categoryId;
}
