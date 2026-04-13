package ru.urfu.cake.shop.product.catalog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.urfu.cake.shop.product.catalog.dto.request.CreateProductDto;
import ru.urfu.cake.shop.product.catalog.dto.response.ProductDto;
import ru.urfu.cake.shop.product.catalog.exception.ProductTypeNotFoundException;
import ru.urfu.cake.shop.product.catalog.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
/**
 * Контроллер продуктов
 */
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Tag(name = "Product", description = "Управление продуктами")
public class ProductController extends BaseController {
    private final ProductService productService;
    /**
     * Создать продукт
     *
     * @param request Запрос
     * @return Результат обработки запроса
     */
    @PostMapping
    @Operation(summary = "Создать продукт", description = "Создание нового продукта с указанием типа и категорий")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Продукт успешно создан"),
        @ApiResponse(responseCode = "400", description = "Неверный запрос или тип продукта не найден")
    })
    public ResponseEntity<ru.urfu.cake.shop.product.catalog.dto.response.ApiResponse<ProductDto>> create(@RequestBody @Schema(description = "Данные для создания продукта", required = true) CreateProductDto request) {
        try {
            var result = productService.create(request);
            var response = toDto(result);
            return buildSuccessResponse(response, "Product created successfully");
        } catch (ProductTypeNotFoundException ex) {
            return buildFailResponse(HttpStatus.BAD_REQUEST, "Product type not found");
        }
    }
}