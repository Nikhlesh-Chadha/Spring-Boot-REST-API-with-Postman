package com.nikh.dreamshops.request;

import com.nikh.dreamshops.model.Category;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;

@Data       //This annotation is used in place of Getter and Setter annotations. This not good to use in an entity class but we can use it here because this is not an entity class and does not have entry to a database
public class AddProductRequest {
    private Long id;
    private String name;
    private String brand;

    private BigDecimal price;
    private int inventory;
    private String description;

    private Category category;

}
