package ru.urfu.cake.shop.product.catalog.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.urfu.cake.shop.product.catalog.dto.request.CreateProductVariantDto;
import ru.urfu.cake.shop.product.catalog.entity.Product;
import ru.urfu.cake.shop.product.catalog.entity.ProductVariant;
import ru.urfu.cake.shop.product.catalog.exception.ProductNotFoundException;
import ru.urfu.cake.shop.product.catalog.exception.ProductVariantNotFoundException;
import ru.urfu.cake.shop.product.catalog.repository.ProductRepository;
import ru.urfu.cake.shop.product.catalog.repository.ProductVariantRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductVariantServiceImpl unit tests")
class ProductVariantServiceImplTest {
    @Mock
    private ProductVariantRepository productVariantRepository;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductVariantServiceImpl productVariantService;
    @Captor
    private ArgumentCaptor<ProductVariant> productVariantCaptor;

    @Test
    @DisplayName("create: should create variant when product exists")
    void create_shouldCreateVariant_whenProductExists() {
        var productId = UUID.randomUUID();
        var product = new Product();
        product.setId(productId);

        var request = new CreateProductVariantDto();
        request.setProductId(productId);
        request.setSku("SKU-001");
        request.setPrice(new BigDecimal("99.99"));
        request.setCurrency("RUB");
        request.setWeight(0.5);
        request.setActive(true);

        var savedVariant = new ProductVariant();
        savedVariant.setId(UUID.randomUUID());

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productVariantRepository.save(any())).thenReturn(savedVariant);

        var result = productVariantService.create(request);

        assertSame(savedVariant, result);
        verify(productRepository).findById(productId);
        verify(productVariantRepository).save(productVariantCaptor.capture());

        var captured = productVariantCaptor.getValue();
        assertSame(product, captured.getProduct());
        assertEquals("SKU-001", captured.getSku());
        assertEquals(new BigDecimal("99.99"), captured.getPrice());
        assertEquals("RUB", captured.getCurrency());
        assertEquals(0.5, captured.getWeight());
        assertTrue(captured.isActive());
        assertNotNull(captured.getCreatedAt());
        assertNotNull(captured.getUpdatedAt());
        assertEquals(1, captured.getVersion());
    }
    @Test
    @DisplayName("create: should throw when product not found")
    void create_shouldThrow_whenProductNotFound() {
        var productId = UUID.randomUUID();
        var request = new CreateProductVariantDto();
        request.setProductId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        var exception = assertThrows(ProductNotFoundException.class,
                () -> productVariantService.create(request));
        assertTrue(exception.getMessage().contains(productId.toString()));

        verify(productRepository).findById(productId);
        verify(productVariantRepository, never()).save(any());
    }
    @Test
    @DisplayName("getById: should return variant when found")
    void getById_shouldReturnVariant_whenFound() {
        var variantId = UUID.randomUUID();
        var variant = new ProductVariant();
        variant.setId(variantId);

        when(productVariantRepository.findById(variantId)).thenReturn(Optional.of(variant));

        var result = productVariantService.getById(variantId);

        assertSame(variant, result);
        verify(productVariantRepository).findById(variantId);
    }
    @Test
    @DisplayName("getById: should throw when not found")
    void getById_shouldThrow_whenNotFound() {
        var variantId = UUID.randomUUID();

        when(productVariantRepository.findById(variantId)).thenReturn(Optional.empty());

        var exception = assertThrows(ProductVariantNotFoundException.class,
                () -> productVariantService.getById(variantId));
        assertTrue(exception.getMessage().contains(variantId.toString()));

        verify(productVariantRepository).findById(variantId);
    }
    @Test
    @DisplayName("getByProductId: should return variants when product exists")
    void getByProductId_shouldReturnVariants_whenProductExists() {
        var productId = UUID.randomUUID();
        var product = new Product();
        product.setId(productId);

        var variant1 = new ProductVariant();
        variant1.setId(UUID.randomUUID());
        var variant2 = new ProductVariant();
        variant2.setId(UUID.randomUUID());
        var variants = List.of(variant1, variant2);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productVariantRepository.findByProductId(productId)).thenReturn(variants);

        var result = productVariantService.getByProductId(productId);

        assertSame(variants, result);
        assertEquals(2, result.size());
        verify(productRepository).findById(productId);
        verify(productVariantRepository).findByProductId(productId);
    }
    @Test
    @DisplayName("getByProductId: should throw when product not found")
    void getByProductId_shouldThrow_whenProductNotFound() {
        var productId = UUID.randomUUID();

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        var exception = assertThrows(ProductNotFoundException.class,
                () -> productVariantService.getByProductId(productId));
        assertTrue(exception.getMessage().contains(productId.toString()));

        verify(productRepository).findById(productId);
        verify(productVariantRepository, never()).findByProductId(any());
    }
}
