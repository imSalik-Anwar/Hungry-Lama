package com.example.hungrylama.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data // includes @getter, @setter, @toString and @reqArgConstructor
@Table(name = "order_table")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    int Id;

    String orderId; // UUID

    @CreationTimestamp
    Date orderTime;

    // relations ===========================================
    @ManyToOne
    @JoinColumn
    Customer customer;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    Basket basket;

    @ManyToOne
    @JoinColumn
    Restaurant restaurant;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<FoodItem> foodItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn
    DeliverPartner deliverPartner;

    @OneToOne(mappedBy = "order", cascade =  CascadeType.ALL)
    Bill bill;

}
