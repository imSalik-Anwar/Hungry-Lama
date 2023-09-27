package com.example.hungrylama.repository;

import com.example.hungrylama.model.BasketItem;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketItemRepository extends JpaRepository<BasketItem, Integer> {
}
