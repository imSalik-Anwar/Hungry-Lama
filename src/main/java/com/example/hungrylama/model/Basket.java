package com.example.hungrylama.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.CodePointLength;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data // includes @getter, @setter, @toString and @reqArgConstructor
@Table(name = "basket")
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basket_id")
    int Id;

    // relations ==========================================
    @OneToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    @OneToOne
    @JoinColumn(name = "order_id")
    OrderEntity order;

    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL)
    List<FoodItem> foodItems = new ArrayList<>();

}
