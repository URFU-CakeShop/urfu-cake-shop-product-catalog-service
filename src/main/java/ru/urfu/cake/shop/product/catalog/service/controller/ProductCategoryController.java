package ru.urfu.cake.shop.product.catalog.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.urfu.cake.shop.product.catalog.service.dto.request.CreateProductCategoryDto;
import ru.urfu.cake.shop.product.catalog.service.dto.response.ApiResponse;
import ru.urfu.cake.shop.product.catalog.service.dto.response.ProductCategoryDto;
import ru.urfu.cake.shop.product.catalog.service.exception.ProductCategoryNotFoundException;
import ru.urfu.cake.shop.product.catalog.service.repository.ProductCategoryRepository;
import ru.urfu.cake.shop.product.catalog.service.service.ProductService;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
/**
 * Контроллер категорий
 */
@RestController
@RequestMapping("/product/category")
@RequiredArgsConstructor
public class ProductCategoryController extends BaseController {
    private final ProductService productService;
    private final ProductCategoryRepository productCategoryRepository;
    /**
     * Создать категорию продукта
     *
     * @param request Запрос
     * @return Категория продукта
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ProductCategoryDto>> createProductCategory(@RequestBody CreateProductCategoryDto request) {
        var result = productService.create(request);
        var response = toDto(result);
        return buildSuccessResponse(response);
    }
    /**
     * Получить все категории продуктов
     *
     * @return Категория продукта
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductCategoryDto>>> getProductCategories() {
        var categoryList = productCategoryRepository.findAll();
        var categoryListDto = new LinkedList<ProductCategoryDto>();
        for (var category : categoryList) {
            categoryListDto.add(toDto(category));
        }
        return buildSuccessResponse(categoryListDto);
    }
    /**
     * Получить категорию продукта
     *
     * @param categoryId Идентификатор категории продукта
     * @return Категория продукта
     */
    @GetMapping("{id}")
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
    @PostMapping("{sourceId}/attach/{targetId}")
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
    @PostMapping("{categoryId}/detach")
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
