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
/**
 * Контроллер продуктов
 */
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController extends BaseController {
    private final ProductService productService;
    /**
     * Создать продукт
     *
     * @param request Запрос
     * @return Результат обработки запроса
     */
    @PostMapping
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