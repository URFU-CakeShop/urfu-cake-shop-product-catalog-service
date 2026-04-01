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

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
/**
 * Контроллер типов продуктов
 */
@RestController
@RequestMapping("/product/type")
@RequiredArgsConstructor
public class ProductTypeController extends BaseController {
    private final ProductTypeRepository productTypeRepository;
    /**
     * Создать тип продукта
     *
     * @param request Запрос
     * @return Результат обработки запроса
     */
    @PostMapping
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
    public ResponseEntity<ApiResponse<ProductTypeDto>> getProductType(@PathVariable(name = "id") UUID typeId) {
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
