package com.example.bisneslogic.repositories;

import com.example.bisneslogic.models.Product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {



    Optional<Product> findByTitle(String title);
}
