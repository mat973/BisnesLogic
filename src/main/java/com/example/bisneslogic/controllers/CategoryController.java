package com.example.bisneslogic.controllers;


import com.example.bisneslogic.common.ApiResponse;
import com.example.bisneslogic.dto.CategoryDto;
import com.example.bisneslogic.dto.ProductDto;
import com.example.bisneslogic.dto.UserDto;
import com.example.bisneslogic.models.Category;
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

import static org.aspectj.bridge.MessageUtil.print;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    private final ModelMapper modelMapper;


    @Autowired
    public CategoryController(CategoryService productService, ModelMapper modelMapper) {
        this.categoryService = productService;
        this.modelMapper = modelMapper;
    }


    @GetMapping()
    public List<CategoryDto> getProducts(){
        return  categoryService.findAll().stream().map(this::converToCategoryDto)
                .collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    public CategoryDto getCategory(@PathVariable("id") Long id){
        return converToCategoryDto(categoryService.findOne(id));
    }


    @PostMapping("/update/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse>  updateCategory(@PathVariable("categoryId") Long categoryId, @RequestBody Category category){
        if (!categoryService.findById(categoryId)){
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "the category not exist"), HttpStatus.NOT_FOUND);
        }
        categoryService.editCategory(categoryId, category);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "updated the category"), HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> create(@RequestBody @Valid CategoryDto categoryDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new CategoryNotCreatedException(errorMsg.toString());
        }
        if (categoryService.findByName(categoryDto.getTitle())){
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "the category already exist"), HttpStatus.NOT_FOUND);
        }
        categoryService.save(convertToCategory(categoryDto));


        return new ResponseEntity<>(new ApiResponse(true, "a new category created"), HttpStatus.CREATED);
    }




    @ExceptionHandler
    private ResponseEntity<CategoryErrorResponse> handleException(CategoryNotFoundException e){
        CategoryErrorResponse response = new CategoryErrorResponse(
                "Category with this id wasn't found!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    private ResponseEntity<CategoryErrorResponse> handleException(CategoryNotCreatedException e){
        CategoryErrorResponse response = new CategoryErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST    );
    }






    private Category convertToCategory(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }

    private CategoryDto converToCategoryDto(Category category){
        return modelMapper.map(category, CategoryDto.class);
    }
}



