package com.example.hungrylama.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

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

    @Column(name = "unique_order_id")
    String orderId; // UUID

    @CreationTimestamp
    Date orderTime;

    // relations ===========================================
    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    Basket basket;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    Restaurant restaurant;


    @ManyToOne
    @JoinColumn(name = "delivery_partner_id")
    DeliveryPartner deliveryPartner;

    @OneToOne(mappedBy = "order", cascade =  CascadeType.ALL)
    Bill bill;

}
