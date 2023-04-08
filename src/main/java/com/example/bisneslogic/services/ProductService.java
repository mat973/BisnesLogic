package com.example.bisneslogic.services;

import com.example.bisneslogic.common.ProductNotFoundException;
import com.example.bisneslogic.dto.ProductDto;
import com.example.bisneslogic.models.Product;
import com.example.bisneslogic.repositories.CategoryRepository;
import com.example.bisneslogic.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;


    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product findOne(Long id) throws ProductNotFoundException {
        Optional<Product> foundProduct =  productRepository.findById(id);
        return foundProduct.orElseThrow(()->new ProductNotFoundException("Product not found"));
    }

    @Transactional
    public void save(Product product){productRepository.save(product);}


    @Transactional
    public void editProduct(Long productId, ProductDto updatedProduct) {
        Product product = productRepository.getById(productId);
        product.setTitle(updatedProduct.getTitle());
        product.setPrice(updatedProduct.getPrice());
        product.setCategory(categoryRepository.getById(updatedProduct.getCategoryId()));
    }

    public boolean findByName(String title) {
        return productRepository.findByTitle(title).isPresent();
    }

    public Optional<Product> findByTitle(String title){
        return productRepository.findByTitle(title);
    }

    public boolean findById(Long productId) {
        return productRepository.findById(productId).isPresent();
    }
}
