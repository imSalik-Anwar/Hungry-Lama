package com.example.hungrylama.repository;

import com.example.hungrylama.Enum.FoodCategory;
import com.example.hungrylama.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Integer> {
    List<FoodItem> findByCategory(FoodCategory category);


    Optional<FoodItem> findByDishName(String dishName);
}
