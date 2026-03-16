package ru.urfu.cake.shop.product.catalog.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.urfu.cake.shop.product.catalog.service.dto.request.CreateProductVariantDto;
import ru.urfu.cake.shop.product.catalog.service.entity.Product;
import ru.urfu.cake.shop.product.catalog.service.entity.ProductVariant;
import ru.urfu.cake.shop.product.catalog.service.exception.ProductNotFoundException;
import ru.urfu.cake.shop.product.catalog.service.exception.ProductVariantNotFoundException;
import ru.urfu.cake.shop.product.catalog.service.repository.ProductRepository;
import ru.urfu.cake.shop.product.catalog.service.repository.ProductVariantRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
/**
 * Сервис разновидностей продуктов
 */
@Service
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {
    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;
    /**
     * Стартовая версия новой разновидности продукта
     */
    public static final int StartVersion = 1;
    /**
     * Создать разновидность продукта
     *
     * @param request Запрос
     * @return Разновидность продукта
     */
    @Override
    public ProductVariant create(CreateProductVariantDto request) {
        var productOptional = productRepository.findById(request.getProductId());
        if (productOptional.isEmpty()) {
            throw new ProductNotFoundException(request.getProductId());
        }
        var product = productOptional.get();
        var productVariant = new ProductVariant();
        productVariant.setProduct(product);
        productVariant.setSku(request.getSku());
        productVariant.setPrice(request.getPrice());
        productVariant.setCurrency(request.getCurrency());
        productVariant.setWeight(request.getWeight());
        productVariant.setActive(request.isActive());
        productVariant.setCreatedAt(LocalDateTime.now());
        productVariant.setUpdatedAt(LocalDateTime.now());
        productVariant.setVersion(StartVersion);
        return productVariantRepository.save(productVariant);
    }
    /**
     * Получить разновидность продукта по идентификатору
     *
     * @param id Идентификатор
     * @return Разновидность продукта
     */
    @Override
    public ProductVariant getById(UUID id) {
        var result = productVariantRepository.findById(id);
        if (result.isEmpty()) {
            throw new ProductVariantNotFoundException(id);
        }
        return result.get();
    }
    /**
     * Получить все разновидности продукта по идентификатору продукта
     *
     * @param productId Идентификатор продукта
     * @return Список разновидностей продукта
     */
    @Override
    public List<ProductVariant> getByProductId(UUID productId) {
        var productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new ProductNotFoundException(productId);
        }
        // Пока нет кастомного метода в репозитории, фильтруем в сервисе
        var allVariants = productVariantRepository.findAll();
        return allVariants.stream()
                .filter(variant -> variant.getProduct().getId().equals(productId))
                .toList();
    }
}