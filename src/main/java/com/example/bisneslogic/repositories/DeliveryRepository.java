package com.example.bisneslogic.repositories;

import com.example.bisneslogic.models.Category;
import com.example.bisneslogic.models.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
