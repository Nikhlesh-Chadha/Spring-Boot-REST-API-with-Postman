package com.nikh.dreamshops.repository;

import com.nikh.dreamshops.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
    // The methods declared in the interface follow Spring Data JPA's naming conventions to
    // automatically generate queries based on method names. These are dynamic query methods
    // based on field names in the Product entity.
    List<Product> findByCategoryName(String category);      // SELECT * FROM product WHERE category_name = :category

    List<Product> findByBrand(String brand);        // SELECT * FROM product WHERE brand = :brand

    List<Product> findByCategoryNameAndBrand(String category, String brand);        // SELECT * FROM product WHERE category_name = :category AND brand = :brand

    List<Product> findByName(String name);      // SELECT * FROM product WHERE name = :name

    List<Product> findByBrandAndName(String brand, String name);        // SELECT * FROM product WHERE brand = :brand AND name = :name

    Long countByBrandAndName(String brand, String name);        // SELECT COUNT(*) FROM product WHERE brand = :brand AND name = :name
}
