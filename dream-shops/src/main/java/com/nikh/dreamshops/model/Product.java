package com.nikh.dreamshops.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


// It is not a good practice to work with this table directly. Instead, we should make a copy of it
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;

    private BigDecimal price;
    private int inventory;
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)       //whatever happens to the product is going to cascade down to the relationship
    @JoinColumn(name = "category_id")
    private Category category;      // category is a standalone table. When the product is deleted, the category table still remains
    // cascade.ALL means that when the product is deleted, the relationship is also deleted

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)       //when a product is being deleted, all the images associated with the product are deleted along
      private List<Image> images;       //image depends on the product. When the product is deleted, the image is gone

    public Product(String name, String brand, BigDecimal price, int inventory, String description, Category category) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.inventory = inventory;
        this.description = description;
        this.category = category;
    }
}
