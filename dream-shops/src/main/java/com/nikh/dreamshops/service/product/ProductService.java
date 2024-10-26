package com.nikh.dreamshops.service.product;

import com.nikh.dreamshops.dto.ImageDto;
import com.nikh.dreamshops.dto.ProductDto;
import com.nikh.dreamshops.exceptions.ProductNotFoundException;
import com.nikh.dreamshops.model.Category;
import com.nikh.dreamshops.model.Image;
import com.nikh.dreamshops.model.Product;
import com.nikh.dreamshops.repository.CategoryRepository;
import com.nikh.dreamshops.repository.ImageRepository;
import com.nikh.dreamshops.repository.ProductRepository;
import com.nikh.dreamshops.request.AddProductRequest;
import com.nikh.dreamshops.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor    // this annotation is for constructor injection. We don't need to define a constructor with the two parameters productRepository, categoryRepository because this annotation takes care of it
public class ProductService implements IProductService{
    private final ProductRepository productRepository;      // always remember everytime we use @RequiredArgsConstructor annotation, the dependency we are using to inject is final
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Override
    public Product addProduct(AddProductRequest request) {
        // check if the category is found in the DB
        // If yes, set it as the new product category
        // If no, then save it as a new category
        // Then set as the new product category.


        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(()-> {
                    Category newCategory = new Category(request.getCategory().getName());   // had to create Category(name) constructor
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category){    //note that we have made this function private. That is the reason we have not put this function declaration in the interface. It is a private function.
        return new Product(     //create constructor in Product
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        ) ;
    }   // this function is in this class but it is not declared in the interface. how does this work? The private function is not accessible outside the class but it can be used inside the other class methods

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()->new ProductNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                        ()-> {throw new ProductNotFoundException("Product not found!");});
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)    // The .map() function is a method on the Optional class.
                .map(existingProduct -> updateExistingProduct(existingProduct, request))      // Since findById(productId) returns Optional<Product>, Java automatically knows/ infers that existingProduct is of type Product.
                .map(productRepository :: save)
                .orElseThrow(()-> new ProductNotFoundException("Product not found!"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products){
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product){
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        productDto.setImages(imageDtos);
        return productDto;
    }
}
