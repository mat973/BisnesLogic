package com.example.bisneslogic.repositories;


import com.example.bisneslogic.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
