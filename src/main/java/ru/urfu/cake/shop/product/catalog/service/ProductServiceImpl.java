package ru.urfu.cake.shop.product.catalog;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.urfu.cake.shop.product.catalog.dto.request.CreateProductCategoryDto;
import ru.urfu.cake.shop.product.catalog.dto.request.CreateProductDto;
import ru.urfu.cake.shop.product.catalog.entity.Product;
import ru.urfu.cake.shop.product.catalog.entity.ProductCategory;
import ru.urfu.cake.shop.product.catalog.exception.ProductCategoryNotFoundException;
import ru.urfu.cake.shop.product.catalog.exception.ProductTypeNotFoundException;
import ru.urfu.cake.shop.product.catalog.repository.ProductCategoryRepository;
import ru.urfu.cake.shop.product.catalog.repository.ProductRepository;
import ru.urfu.cake.shop.product.catalog.repository.ProductTypeRepository;
/**
 * Сервис продуктов
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
    private final ProductCategoryRepository productCategoryRepository;
    /**
     * Стартовая версия нового продукта
     */
    public static final int StartVersion = 1;
    /**
     * Создать продукт
     *
     * @param request Запрос
     * @return Продукт
     */
    @Override
    public Product create(CreateProductDto request) {
        var productTypeOptional = productTypeRepository.findById(request.getTypeId());
        if (productTypeOptional.isEmpty()) {
            throw new ProductTypeNotFoundException(request.getTypeId());
        }
        var productType = productTypeOptional.get();
        var productCategoryList = new LinkedList<ProductCategory>();
        for (var productCategoryId : request.getCategoryIdList()) {
            var productCategory = getCategoryById(productCategoryId);
            productCategoryList.add(productCategory);
        }
        var product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setType(productType);
        product.setCategory(productCategoryList);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        product.setVersion(StartVersion);
        return productRepository.save(product);
    }
    /**
     * Создать категорию продуктов
     *
     * @param request Запрос
     * @return Категория продуктов
     */
    @Override
    public ProductCategory create(CreateProductCategoryDto request) {
        var category = new ProductCategory();
        category.setName(request.getName());
        return productCategoryRepository.save(category);
    }
    /**
     * Привязать категорию продуктов к другой категории
     *
     * @param sourceCategoryId Исходная категория продуктов
     * @param targetCategoryId Категория продуктов для привязки
     */
    @Override
    public void attach(UUID sourceCategoryId, UUID targetCategoryId) {
        var sourceCategory = getCategoryById(sourceCategoryId);
        var targetCategory = getCategoryById(targetCategoryId);
        targetCategory.setParent(sourceCategory);
        productCategoryRepository.save(targetCategory);
    }
    /**
     * Отвязать родителя указанной категории продукта
     *
     * @param categoryId Идентификатор категории продуктов
     */
    @Override
    public void detach(UUID categoryId) {
        var category = getCategoryById(categoryId);
        category.setParent(null);
        productCategoryRepository.save(category);
    }
    /**
     * Получить категорию по идентификатору
     *
     * @param id Идентификатор
     * @return Категория
     */
    private ProductCategory getCategoryById(UUID id) {
        var result = productCategoryRepository.findById(id);
        if (result.isEmpty()) {
            throw new ProductCategoryNotFoundException(id);
        }
        return result.get();
    }
}
