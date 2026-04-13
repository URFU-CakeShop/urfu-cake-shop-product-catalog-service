package ru.urfu.cake.shop.product.catalog.dto.request;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;
/**
 * Запрос на создание продукта
 */
@Data
@Schema(description = "Запрос на создание продукта")
public class CreateProductDto {
    @Schema(description = "Наименование продукта", example = "Торт Наполеон", required = true)
    private String name;
    @Schema(description = "Описание продукта", example = "Слоеный торт с заварным кремом")
    private String description;
    @Schema(description = "Идентификатор типа продукта", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
    private UUID typeId;
    @Schema(description = "Список идентификаторов категорий продукта")
    private List<UUID> categoryIdList;
}
