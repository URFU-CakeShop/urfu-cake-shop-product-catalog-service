package ru.urfu.cake.shop.product.catalog.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.urfu.cake.shop.product.catalog.service.dto.request.CreateProductCategoryDto;
import ru.urfu.cake.shop.product.catalog.service.dto.response.ApiResponse;
import ru.urfu.cake.shop.product.catalog.service.dto.request.CreateProductDto;
import ru.urfu.cake.shop.product.catalog.service.dto.response.ProductCategoryDto;
import ru.urfu.cake.shop.product.catalog.service.dto.response.ProductDto;
import ru.urfu.cake.shop.product.catalog.service.dto.response.ProductTypeDto;
import ru.urfu.cake.shop.product.catalog.service.exception.ProductCategoryNotFoundException;
import ru.urfu.cake.shop.product.catalog.service.exception.ProductTypeNotFoundException;
import ru.urfu.cake.shop.product.catalog.service.repository.ProductCategoryRepository;
import ru.urfu.cake.shop.product.catalog.service.repository.ProductTypeRepository;
import ru.urfu.cake.shop.product.catalog.service.service.ProductService;

import java.util.UUID;
/**
 * Контроллер продуктов
 */
@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ProductController extends BaseController {
    private final ProductService productService;
    private final ProductTypeRepository productTypeRepository;
    private final ProductCategoryRepository productCategoryRepository;
    /**
     * Создать продукт
     *
     * @param request Запрос
     * @return Результат обработки запроса
     */
    @PostMapping("product")
    public ResponseEntity<ApiResponse<ProductDto>> create(@RequestBody CreateProductDto request) {
        try {
            var result = productService.create(request);
            var response = toDto(result);
            return buildSuccessResponse(response, "Product created successfully");
        } catch (ProductTypeNotFoundException ex) {
            return buildFailResponse(HttpStatus.BAD_REQUEST, "Product type not found");
        }
    }
    /**
     * Получить тип продукта
     *
     * @param typeId Идентификатор типа продукта
     * @return Тип продукта
     */
    @GetMapping("product/type/{id}")
    public ResponseEntity<ApiResponse<ProductTypeDto>> getProductType(@PathVariable UUID typeId) {
        var typeOptional = productTypeRepository.findById(typeId);
        if (typeOptional.isEmpty()) {
            return buildFailResponse(HttpStatus.NOT_FOUND, "Product type not found");
        }
        var type = typeOptional.get();
        var response = toDto(type);
        return buildSuccessResponse(response);
    }
    /**
     * Создать категорию продукта
     *
     * @param request Запрос
     * @return Категория продукта
     */
    @PostMapping("product/category")
    public ResponseEntity<ApiResponse<ProductCategoryDto>> createProductCategory(@RequestBody CreateProductCategoryDto request) {
        var result = productService.create(request);
        var response = toDto(result);
        return buildSuccessResponse(response);
    }
    /**
     * Получить категорию продукта
     *
     * @param categoryId Идентификатор категории продукта
     * @return Категория продукта
     */
    @GetMapping("product/category/{id}")
    public ResponseEntity<ApiResponse<ProductCategoryDto>> getProductCategory(@PathVariable(name = "id") UUID categoryId) {
        var categoryOptional = productCategoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            return buildFailResponse(HttpStatus.NOT_FOUND, "Product category not found");
        }
        var category = categoryOptional.get();
        var response = toDto(category);
        return buildSuccessResponse(response);
    }
    /**
     * Привязать категорию продукта к другой категории
     *
     * @param sourceId Исходная категория продуктов
     * @param targetId Категория продукта которую необходимо привязать
     */
    @PostMapping("product/category/{sourceId}/attach/{targetId}")
    public ResponseEntity<ApiResponse<ProductCategoryDto>> attachProductCategory(
        @PathVariable(name = "sourceId") UUID sourceId,
        @PathVariable(name = "targetId") UUID targetId
    ) {
        try {
            productService.attach(sourceId, targetId);
            return buildSuccessResponse();
        } catch (ProductCategoryNotFoundException ex) {
            return buildFailResponse(HttpStatus.BAD_REQUEST, "Product category not found");
        }
    }
    /**
     * Отвязать родителя указанной категории продукта
     *
     * @param categoryId Категория продуктов
     */
    @PostMapping("product/category/{categoryId}/detach")
    public ResponseEntity<ApiResponse<ProductCategoryDto>> detachProductCategory(
            @PathVariable(name = "categoryId") UUID categoryId
    ) {
        try {
            productService.detach(categoryId);
            return buildSuccessResponse();
        } catch (ProductCategoryNotFoundException ex) {
            return buildFailResponse(HttpStatus.BAD_REQUEST, "Product category not found");
        }
    }
}