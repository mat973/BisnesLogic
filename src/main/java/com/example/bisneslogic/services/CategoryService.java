package com.example.bisneslogic.services;


import com.example.bisneslogic.models.Category;
import com.example.bisneslogic.models.Product;
import com.example.bisneslogic.repositories.CategoryRepository;
import com.example.bisneslogic.util.CategoryNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;


    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findOne(Long id) {
        Optional<Category> foundCategory = categoryRepository.findById(id);
        return foundCategory.orElseThrow(CategoryNotFoundException::new);
    }


    @Transactional
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Transactional
    public void editCategory(Long categoryId, Category updateCategory) {
        Category category = categoryRepository.getById(categoryId);
        category.setTitle(updateCategory.getTitle());

    }


    public boolean findById(Long categoryId) {
        return categoryRepository.findById(categoryId).isPresent();
    }

    public boolean findByName(String title) {
        return categoryRepository.findByTitle(title).isPresent();
    }
}
