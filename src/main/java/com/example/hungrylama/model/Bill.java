package com.example.hungrylama.model;

import com.example.hungrylama.Enum.Coupon;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data // includes @getter, @setter, @toString and @reqArgConstructor
@Table(name = "bill")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    int Id;

    double orderValue;

    double gst;

    @Enumerated(EnumType.STRING)
    Coupon coupon;

    double discount;

    double billAmount;

    // relations ================================================
    @OneToOne
    @JoinColumn(name = "order_id")
    OrderEntity order;
}
