package com.online.store.entity;

import com.online.store.enums.Category;
import com.online.store.enums.Label;
import com.online.store.enums.SubCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

import static jakarta.persistence.EnumType.ORDINAL;

@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(nullable = false, unique = true, name = "product_id")
    private Long productId;

    @Column(name = "name")
    private String productName;

    @Column(name = "description")
    private String productDescription;

    @Column(name = "price_with_tax")
    private Integer fullPrice;

    @Column(name = "price_without_tax")
    private Integer priceWithoutTax;

    private String image;

    @Enumerated(ORDINAL)
    private Category category;

    @Enumerated(ORDINAL)
    @Column(name = "sub_category")
    private SubCategory subCategory;

    @Enumerated(ORDINAL)
    private Label label;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(nullable = false, name = "updated_at")
    private Date updatedAt;

    @Column(updatable = false, name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;
}
