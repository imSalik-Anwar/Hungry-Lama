package com.example.hungrylama.model;

import com.example.hungrylama.Enum.RestaurantCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data // includes @getter, @setter, @toString and @reqArgConstructor
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    int Id;

    @Size(min = 1, max = 40)
    @Column(unique = true, nullable = false)
    String name;

    @Column(unique = true, nullable = false)
    @Size(min = 10, max = 10)
    String contactNumber;

    String location;

    int zipCode;

    @Enumerated(EnumType.STRING)
    RestaurantCategory restaurantCategory;

    boolean open;

    // restaurant =====================================
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    List<FoodItem> menu = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    List<OrderEntity> orders = new ArrayList<>();

}
