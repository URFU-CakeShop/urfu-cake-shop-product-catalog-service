package ru.urfu.cake.shop.product.catalog.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.urfu.cake.shop.product.catalog.entity.ProductCategory;
import ru.urfu.cake.shop.product.catalog.exception.ProductCategoryNotFoundException;
import ru.urfu.cake.shop.product.catalog.repository.ProductCategoryRepository;
import ru.urfu.cake.shop.product.catalog.repository.ProductRepository;
import ru.urfu.cake.shop.product.catalog.repository.ProductTypeRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductServiceImpl unit tests")
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductTypeRepository productTypeRepository;
    @Mock
    private ProductCategoryRepository productCategoryRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    @DisplayName("attach: should set target category parent to source category")
    void attach_shouldSetParent_whenBothCategoriesExist() {
        var sourceId = UUID.randomUUID();
        var targetId = UUID.randomUUID();
        var sourceCategory = new ProductCategory();
        sourceCategory.setId(sourceId);
        var targetCategory = new ProductCategory();
        targetCategory.setId(targetId);

        when(productCategoryRepository.findById(sourceId)).thenReturn(Optional.of(sourceCategory));
        when(productCategoryRepository.findById(targetId)).thenReturn(Optional.of(targetCategory));

        productService.attach(sourceId, targetId);

        assertSame(sourceCategory, targetCategory.getParent());
        verify(productCategoryRepository).save(targetCategory);
        verify(productCategoryRepository).findById(sourceId);
        verify(productCategoryRepository).findById(targetId);
        verifyNoMoreInteractions(productCategoryRepository);
    }
    @Test
    @DisplayName("attach: should throw when source category not found")
    void attach_shouldThrow_whenSourceCategoryNotFound() {
        var sourceId = UUID.randomUUID();
        var targetId = UUID.randomUUID();

        when(productCategoryRepository.findById(sourceId)).thenReturn(Optional.empty());

        var exception = assertThrows(ProductCategoryNotFoundException.class,
                () -> productService.attach(sourceId, targetId));
        assertTrue(exception.getMessage().contains(sourceId.toString()));

        verify(productCategoryRepository).findById(sourceId);
        verify(productCategoryRepository, never()).findById(targetId);
        verify(productCategoryRepository, never()).save(any());
    }
    @Test
    @DisplayName("attach: should throw when target category not found")
    void attach_shouldThrow_whenTargetCategoryNotFound() {
        var sourceId = UUID.randomUUID();
        var targetId = UUID.randomUUID();
        var sourceCategory = new ProductCategory();
        sourceCategory.setId(sourceId);

        when(productCategoryRepository.findById(sourceId)).thenReturn(Optional.of(sourceCategory));
        when(productCategoryRepository.findById(targetId)).thenReturn(Optional.empty());

        var exception = assertThrows(ProductCategoryNotFoundException.class,
                () -> productService.attach(sourceId, targetId));
        assertTrue(exception.getMessage().contains(targetId.toString()));

        verify(productCategoryRepository).findById(sourceId);
        verify(productCategoryRepository).findById(targetId);
        verify(productCategoryRepository, never()).save(any());
    }
    @Test
    @DisplayName("detach: should set category parent to null")
    void detach_shouldSetParentToNull_whenCategoryExists() {
        var categoryId = UUID.randomUUID();
        var parentCategory = new ProductCategory();
        parentCategory.setId(UUID.randomUUID());
        var category = new ProductCategory();
        category.setId(categoryId);
        category.setParent(parentCategory);

        when(productCategoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        productService.detach(categoryId);

        assertNull(category.getParent());
        verify(productCategoryRepository).save(category);
        verify(productCategoryRepository).findById(categoryId);
        verifyNoMoreInteractions(productCategoryRepository);
    }
    @Test
    @DisplayName("detach: should throw when category not found")
    void detach_shouldThrow_whenCategoryNotFound() {
        var categoryId = UUID.randomUUID();

        when(productCategoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        var exception = assertThrows(ProductCategoryNotFoundException.class,
                () -> productService.detach(categoryId));
        assertTrue(exception.getMessage().contains(categoryId.toString()));

        verify(productCategoryRepository).findById(categoryId);
        verify(productCategoryRepository, never()).save(any());
    }
}
