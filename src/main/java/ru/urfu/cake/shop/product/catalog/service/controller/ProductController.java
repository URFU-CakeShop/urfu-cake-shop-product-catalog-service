package ru.urfu.cake.shop.product.catalog.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.urfu.cake.shop.product.catalog.service.dto.response.ApiResponse;
import ru.urfu.cake.shop.product.catalog.service.dto.request.CreateProductDto;
import ru.urfu.cake.shop.product.catalog.service.dto.response.ProductDto;
import ru.urfu.cake.shop.product.catalog.service.exception.ProductTypeNotFoundException;
import ru.urfu.cake.shop.product.catalog.service.service.ProductService;
/**
 * Контроллер продуктов
 */
@RestController
@RequestMapping("/api/product")
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
            return ResponseEntity.ok(new ApiResponse<>(true, toDto(result), "Product created successfully"));
        } catch (ProductTypeNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, null, ex.getMessage()));
        }
    }
}