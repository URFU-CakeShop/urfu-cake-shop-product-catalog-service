package ru.urfu.cake.shop.product.catalog.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.urfu.cake.shop.product.catalog.service.dto.request.CreateProductVariantDto;
import ru.urfu.cake.shop.product.catalog.service.dto.response.ApiResponse;
import ru.urfu.cake.shop.product.catalog.service.dto.response.ProductVariantDto;
import ru.urfu.cake.shop.product.catalog.service.exception.ProductNotFoundException;
import ru.urfu.cake.shop.product.catalog.service.exception.ProductVariantNotFoundException;
import ru.urfu.cake.shop.product.catalog.service.service.ProductVariantService;
import java.util.List;
import java.util.UUID;
/**
 * Контроллер разновидностей продуктов
 */
@RestController
@RequestMapping("/product/variant")
@RequiredArgsConstructor
public class ProductVariantController extends BaseController {
    private final ProductVariantService productVariantService;
    /**
     * Создать разновидность продукта
     *
     * @param request Запрос
     * @return Результат обработки запроса
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ProductVariantDto>> create(@RequestBody CreateProductVariantDto request) {
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
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductVariantDto>> getById(@PathVariable UUID id) {
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
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductVariantDto>>> getByProductId(@RequestParam UUID productId) {
        try {
            var result = productVariantService.getByProductId(productId);
            var response = result.stream().map(BaseController::toDto).toList();
            return buildSuccessResponse(response);
        } catch (ProductNotFoundException ex) {
            return buildFailResponse(HttpStatus.BAD_REQUEST, "Product not found");
        }
    }
}
