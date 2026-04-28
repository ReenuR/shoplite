package com.shoplite.productservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "created_by", nullable = false)
    private String productDesc;

    @Column(name = "product_selling_price", nullable = false)
    private BigDecimal productSellingPrice;

    @Column(name = "product_original_price", nullable = false)
    private BigDecimal productOriginalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_category_id")
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_status")
    private ProductStatusCode productStatus;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    private UUID createdBy;

    private boolean isActive;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
