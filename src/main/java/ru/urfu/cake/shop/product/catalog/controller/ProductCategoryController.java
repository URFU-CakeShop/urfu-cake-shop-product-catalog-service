package ru.urfu.cake.shop.product.catalog.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.urfu.cake.shop.product.catalog.dto.request.CreateProductCategoryDto;
import ru.urfu.cake.shop.product.catalog.dto.response.ProductCategoryDto;
import ru.urfu.cake.shop.product.catalog.exception.ProductCategoryNotFoundException;
import ru.urfu.cake.shop.product.catalog.repository.ProductCategoryRepository;
import ru.urfu.cake.shop.product.catalog.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
/**
 * Контроллер категорий
 */
@RestController
@RequestMapping("/product/category")
@RequiredArgsConstructor
@Tag(name = "Product Category", description = "Управление категориями продуктов")
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
    @Operation(summary = "Создать категорию продукта", description = "Создание новой категории продукта")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Категория успешно создана"),
        @ApiResponse(responseCode = "400", description = "Неверный запрос")
    })
    public ResponseEntity<ru.urfu.cake.shop.product.catalog.dto.response.ApiResponse<ProductCategoryDto>> createProductCategory(@RequestBody @Schema(description = "Данные для создания категории продукта", required = true) CreateProductCategoryDto request) {
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
    @Operation(summary = "Получить все категории продуктов", description = "Возвращает список всех категорий продуктов")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Список категорий успешно получен")
    })
    public ResponseEntity<ru.urfu.cake.shop.product.catalog.dto.response.ApiResponse<List<ProductCategoryDto>>> getProductCategories() {
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
    @Operation(summary = "Получить категорию продукта по ID", description = "Возвращает категорию продукта по указанному идентификатору")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Категория успешно получена"),
        @ApiResponse(responseCode = "404", description = "Категория не найдена")
    })
    public ResponseEntity<ru.urfu.cake.shop.product.catalog.dto.response.ApiResponse<ProductCategoryDto>> getProductCategory(
            @PathVariable(name = "id")
            @Parameter(description = "Идентификатор категории продукта", required = true)
            UUID categoryId) {
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
    @Operation(summary = "Привязать категорию продукта к другой категории", description = "Устанавливает родительскую связь между двумя категориями")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Категории успешно привязаны"),
        @ApiResponse(responseCode = "400", description = "Категория не найдена")
    })
    public ResponseEntity<ru.urfu.cake.shop.product.catalog.dto.response.ApiResponse<ProductCategoryDto>> attachProductCategory(
            @PathVariable(name = "sourceId")
            @Parameter(description = "Идентификатор исходной категории", required = true)
            UUID sourceId,
            @PathVariable(name = "targetId")
            @Parameter(description = "Идентификатор целевой категории", required = true)
            UUID targetId
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
    @Operation(summary = "Отвязать родительскую категорию", description = "Удаляет родительскую связь у указанной категории")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Категория успешно отвязана"),
        @ApiResponse(responseCode = "400", description = "Категория не найдена")
    })
    public ResponseEntity<ru.urfu.cake.shop.product.catalog.dto.response.ApiResponse<ProductCategoryDto>> detachProductCategory(
            @PathVariable(name = "categoryId")
            @Parameter(description = "Идентификатор категории продукта", required = true)
            UUID categoryId
    ) {
        try {
            productService.detach(categoryId);
            return buildSuccessResponse();
        } catch (ProductCategoryNotFoundException ex) {
            return buildFailResponse(HttpStatus.BAD_REQUEST, "Product category not found");
        }
    }
}
