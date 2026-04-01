package ru.urfu.cake.shop.product.catalog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.urfu.cake.shop.product.catalog.dto.response.ApiResponse;
import ru.urfu.cake.shop.product.catalog.dto.request.CreateProductDto;
import ru.urfu.cake.shop.product.catalog.dto.response.ProductDto;
import ru.urfu.cake.shop.product.catalog.exception.ProductTypeNotFoundException;
import ru.urfu.cake.shop.product.catalog.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Продукт успешно создан", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Неверный запрос или тип продукта не найден")
    })
    public ResponseEntity<ApiResponse<ProductDto>> create(@RequestBody CreateProductDto request) {
        try {
            var result = productService.create(request);
            var response = toDto(result);
            return buildSuccessResponse(response, "Product created successfully");
        } catch (ProductTypeNotFoundException ex) {
            return buildFailResponse(HttpStatus.BAD_REQUEST, "Product type not found");
        }
    }
}