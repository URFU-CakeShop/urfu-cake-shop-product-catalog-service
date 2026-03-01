package ru.urfu.cake.shop.product.catalog.service.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.urfu.cake.shop.product.catalog.service.dto.request.CreateProductDto;
import ru.urfu.cake.shop.product.catalog.service.entity.Product;
import ru.urfu.cake.shop.product.catalog.service.exception.ProductTypeNotFoundException;
import ru.urfu.cake.shop.product.catalog.service.repository.ProductRepository;
import ru.urfu.cake.shop.product.catalog.service.repository.ProductTypeRepository;
/**
 * Сервис продуктов
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
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
        var product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setType(productType);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        product.setVersion(StartVersion);
        product = productRepository.save(product);
        return product;
    }
}
