package ru.urfu.cake.shop.product.catalog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.urfu.cake.shop.product.catalog.dto.request.CreateProductTypeDto;
import ru.urfu.cake.shop.product.catalog.dto.response.ApiResponse;
import ru.urfu.cake.shop.product.catalog.dto.response.ProductTypeDto;
import ru.urfu.cake.shop.product.catalog.entity.ProductType;
import ru.urfu.cake.shop.product.catalog.repository.ProductTypeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
/**
 * Контроллер типов продуктов
 */
@RestController
@RequestMapping("/product/type")
@RequiredArgsConstructor
@Tag(name = "Product Type", description = "Управление типами продуктов")
public class ProductTypeController extends BaseController {
    private final ProductTypeRepository productTypeRepository;
    /**
     * Создать тип продукта
     *
     * @param request Запрос
     * @return Результат обработки запроса
     */
    @PostMapping
    @Operation(summary = "Создать тип продукта", description = "Создание нового типа продукта")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Тип продукта успешно создан", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Неверный запрос")
    })
    public ResponseEntity<ApiResponse<ProductTypeDto>> createProductType(@RequestBody CreateProductTypeDto request) {
        var productType = new ProductType();
        productType.setName(request.getName());
        var productTypeSaved = productTypeRepository.save(productType);
        var result = toDto(productTypeSaved);
        return buildSuccessResponse(result);
    }
    /**
     * Получить тип продукта по идентификатору
     *
     * @param typeId Идентификатор типа продукта
     * @return Тип продукта
     */
    @GetMapping("{id}")
    @Operation(summary = "Получить тип продукта по ID", description = "Возвращает тип продукта по указанному идентификатору")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Тип продукта успешно получен", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Тип продукта не найден")
    })
    public ResponseEntity<ApiResponse<ProductTypeDto>> getProductType(
            @PathVariable(name = "id")
            @Parameter(description = "Идентификатор типа продукта", required = true)
            UUID typeId) {
        var typeOptional = productTypeRepository.findById(typeId);
        if (typeOptional.isEmpty()) {
            return buildFailResponse(HttpStatus.NOT_FOUND, "Product type not found");
        }
        var type = typeOptional.get();
        var response = toDto(type);
        return buildSuccessResponse(response);
    }
    /**
     * Получить все возможные типы продуктов
     *
     * @return Результат обработки запроса
     */
    @GetMapping
    @Operation(summary = "Получить все типы продуктов", description = "Возвращает список всех типов продуктов")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Список типов продуктов успешно получен", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    public ResponseEntity<ApiResponse<List<ProductTypeDto>>> getProductTypes() {
        var productTypeList = productTypeRepository.findAll();
        var productTypeDtoList = new LinkedList<ProductTypeDto>();
        for (var productType : productTypeList) {
            var productTypeDto = toDto(productType);
            productTypeDtoList.addLast(productTypeDto);
        }
        return buildSuccessResponse(productTypeDtoList);
    }
}
