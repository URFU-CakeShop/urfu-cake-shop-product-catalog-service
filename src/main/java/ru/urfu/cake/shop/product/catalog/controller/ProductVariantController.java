package ru.urfu.cake.shop.product.catalog.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.urfu.cake.shop.product.catalog.dto.request.CreateProductVariantDto;
import ru.urfu.cake.shop.product.catalog.dto.response.ProductVariantDto;
import ru.urfu.cake.shop.product.catalog.exception.ProductNotFoundException;
import ru.urfu.cake.shop.product.catalog.exception.ProductVariantNotFoundException;
import ru.urfu.cake.shop.product.catalog.ProductVariantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import java.util.UUID;
/**
 * Контроллер разновидностей продуктов
 */
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Tag(name = "Product Variant", description = "Управление вариантами продуктов")
public class ProductVariantController extends BaseController {
    private final ProductVariantService productVariantService;
    /**
     * Создать разновидность продукта
     *
     * @param request Запрос
     * @return Результат обработки запроса
     */
    @PostMapping("/variant")
    @Operation(summary = "Создать вариант продукта", description = "Создание нового варианта продукта")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Вариант продукта успешно создан"),
        @ApiResponse(responseCode = "400", description = "Неверный запрос или продукт не найден")
    })
    public ResponseEntity<ru.urfu.cake.shop.product.catalog.dto.response.ApiResponse<ProductVariantDto>> create(@RequestBody @Schema(description = "Данные для создания варианта продукта", required = true) CreateProductVariantDto request) {
        try {
            var result = productVariantService.create(request);
            var response = toDto(result);
            return buildSuccessResponse(response, "Product variant created successfully");
        } catch (ProductNotFoundException ex) {
            return buildFailResponse(HttpStatus.BAD_REQUEST, "Product not found");
        }
    }
    /**
     * Получить разновидность продукта по идентификатору
     *
     * @param id Идентификатор
     * @return Результат обработки запроса
     */
    @GetMapping("/variant/{id}")
    @Operation(summary = "Получить вариант продукта по ID", description = "Возвращает вариант продукта по указанному идентификатору")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Вариант продукта успешно получен"),
        @ApiResponse(responseCode = "404", description = "Вариант продукта не найден")
    })
    public ResponseEntity<ru.urfu.cake.shop.product.catalog.dto.response.ApiResponse<ProductVariantDto>> getById(
            @PathVariable
            @Parameter(description = "Идентификатор варианта продукта", required = true)
            UUID id
    ) {
        try {
            var result = productVariantService.getById(id);
            var response = toDto(result);
            return buildSuccessResponse(response);
        } catch (ProductVariantNotFoundException ex) {
            return buildFailResponse(HttpStatus.NOT_FOUND, "Product variant not found");
        }
    }
    /**
     * Получить все разновидности продукта по идентификатору продукта
     *
     * @param productId Идентификатор продукта
     * @return Результат обработки запроса
     */
    @GetMapping("/{productId}/variant")
    @Operation(summary = "Получить варианты продукта по ID продукта", description = "Возвращает все варианты указанного продукта")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Список вариантов продукта успешно получен"),
        @ApiResponse(responseCode = "400", description = "Продукт не найден")
    })
    public ResponseEntity<ru.urfu.cake.shop.product.catalog.dto.response.ApiResponse<List<ProductVariantDto>>> getByProductId(
        @PathVariable UUID productId
    ) {
        try {
            var result = productVariantService.getByProductId(productId);
            var response = result.stream().map(BaseController::toDto).toList();
            return buildSuccessResponse(response);
        } catch (ProductNotFoundException ex) {
            return buildFailResponse(HttpStatus.BAD_REQUEST, "Product not found");
        }
    }
}
