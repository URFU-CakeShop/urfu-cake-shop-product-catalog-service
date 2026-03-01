package ru.urfu.cake.shop.product.catalog.service.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
/**
 * Категория продуктов
 */
@Table(name = "category")
@Entity
@Data
public class ProductCategory {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ProductCategory parent;
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("name ASC")
    private List<ProductCategory> subcategories = new ArrayList<>();
}
