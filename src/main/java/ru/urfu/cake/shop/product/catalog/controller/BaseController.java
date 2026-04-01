package ru.urfu.cake.shop.product.catalog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.urfu.cake.shop.product.catalog.dto.response.ApiResponse;
import ru.urfu.cake.shop.product.catalog.dto.response.ProductCategoryDto;
import ru.urfu.cake.shop.product.catalog.dto.response.ProductDto;
import ru.urfu.cake.shop.product.catalog.dto.response.ProductTypeDto;
import ru.urfu.cake.shop.product.catalog.dto.response.ProductVariantDto;
import ru.urfu.cake.shop.product.catalog.entity.Product;
import ru.urfu.cake.shop.product.catalog.entity.ProductCategory;
import ru.urfu.cake.shop.product.catalog.entity.ProductType;
import ru.urfu.cake.shop.product.catalog.entity.ProductVariant;

import java.util.LinkedList;
import java.util.UUID;
/**
 * Базовый класс для построения контроллеров
 */
public abstract class BaseController {
    /**
     * Построить ответ с успешным статус-кодом
     *
     * @return Ответ
     * @param <T> Типизация тела ответа
     */
    protected static <T> ResponseEntity<ApiResponse<T>> buildSuccessResponse() {
        return buildResponse(HttpStatus.OK, true, null, null);
    }
    /**
     * Построить ответ с успешным статус-кодом
     *
     * @param data Данные ответа
     * @return Ответ
     * @param <T> Типизация тела ответа
     */
    protected static <T> ResponseEntity<ApiResponse<T>> buildSuccessResponse(T data) {
        return buildResponse(HttpStatus.OK, true, data, null);
    }
    /**
     * Построить ответ с успешным статус-кодом
     *
     * @param data Данные ответа
     * @param message Сообщение
     * @return Ответ
     * @param <T> Типизация тела ответа
     */
    protected static <T> ResponseEntity<ApiResponse<T>> buildSuccessResponse(T data, String message) {
        return buildResponse(HttpStatus.OK, true, data, message);
    }
    /**
     * Построить ответ с неуспешным статус-кодом
     *
     * @param status Http-статус код
     * @param message Сообщение
     * @return Ответ
     * @param <T> Типизация тела ответа
     */
    protected static <T> ResponseEntity<ApiResponse<T>> buildFailResponse(HttpStatus status, String message) {
        return buildResponse(status, false, null, message);
    }
    /**
     * Построить ответ
     *
     * @param status Http-статус код
     * @param success Признак успешного запроса
     * @param data Данные ответа
     * @param message Сообщение
     * @return Ответ
     * @param <T> Типизация тела ответа
     */
    protected static <T> ResponseEntity<ApiResponse<T>> buildResponse(HttpStatus status, boolean success, T data, String message) {
        var response = new ApiResponse<>(success, data, message);
        return ResponseEntity
                .status(status)
                .body(response);
    }
    /**
     * Привести продукт к DTO
     *
     * @param product Продукт
     * @return DTO
     */
    protected static ProductDto toDto(Product product) {
        return new ProductDto(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getType().getId(),
            product.getCategory().stream().map(ProductCategory::getId).toList()
        );
    }
    /**
     * Привести тип продукта к DTO
     *
     * @param productType Тип продукта
     * @return DTO
     */
    protected static ProductTypeDto toDto(ProductType productType) {
        return new ProductTypeDto(
            productType.getId(),
            productType.getName()
        );
    }
    /**
     * Привести категория продукта к DTO
     *
     * @param productCategory Категория продукта
     * @return DTO
     */
    protected static ProductCategoryDto toDto(ProductCategory productCategory) {
        var subcategories = new LinkedList<UUID>();
        for (var subcategoryId : productCategory.getSubcategories()) {
            subcategories.add(subcategoryId.getId());
        }
        var parent = productCategory.getParent();
        UUID parentId = null;
        if (parent != null) {
            parentId = parent.getId();
        }
        return new ProductCategoryDto(productCategory.getId(), productCategory.getName(), parentId, subcategories);
    }
    /**
     * Привести разновидность продукта к DTO
     *
     * @param productVariant Разновидность продукта
     * @return DTO
     */
    protected static ProductVariantDto toDto(ProductVariant productVariant) {
        return new ProductVariantDto(
            productVariant.getId(),
            productVariant.getProduct().getId(),
            productVariant.getSku(),
            productVariant.getPrice(),
            productVariant.getCurrency(),
            productVariant.getWeight(),
            productVariant.isActive(),
            productVariant.getCreatedAt(),
            productVariant.getUpdatedAt(),
            productVariant.getVersion()
        );
    }
}
