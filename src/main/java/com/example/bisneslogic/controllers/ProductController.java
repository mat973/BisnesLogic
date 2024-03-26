package com.example.bisneslogic.controllers;

import com.example.bisneslogic.common.ApiResponse;
import com.example.bisneslogic.common.ProductNotFoundException;
import com.example.bisneslogic.dto.ProductDto;
import com.example.bisneslogic.models.Product;
import com.example.bisneslogic.services.CategoryService;
import com.example.bisneslogic.services.ProductService;
import com.example.bisneslogic.util.*;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService, ModelMapper modelMapper) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }


    @GetMapping()
    public List<Product> getProducts(){
        return  productService.findAll().stream()
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
        return converToProductDto(productService.findOne(id));
    }


    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> create(@RequestBody @Valid ProductDto productDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new ProductNotCreatedException(errorMsg.toString());
        }
        if (productService.findByName(productDto.getTitle())){
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "the product already exist"), HttpStatus.NOT_FOUND);
        }
        productService.save(convertToProduct(productDto));


        return new ResponseEntity<>(new ApiResponse(true, "a new product created"), HttpStatus.CREATED);
    }



    @ExceptionHandler
    private ResponseEntity<ProductErrorResponse> handleException(){
        ProductErrorResponse response = new ProductErrorResponse(
                "Person with this id wasn't found!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @PutMapping("/{productId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse>  updateProduct(@PathVariable("productId") Long productId, @RequestBody ProductDto productDto){
        if (!productService.findById(productId)){
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "the Product not exist"), HttpStatus.NOT_FOUND);
        }
        productService.editProduct(productId,productDto);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "product the category"), HttpStatus.OK);
    }



    @ExceptionHandler
    private ResponseEntity<ProductErrorResponse> handleException(ProductNotCreatedException e){
        ProductErrorResponse response = new ProductErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST    );
    }




    private Product convertToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setCategory(categoryService.findOne(productDto.getCategoryId()));
        return product;
    }

    private ProductDto converToProductDto(Product product){
        return modelMapper.map(product, ProductDto.class);
    }
}
