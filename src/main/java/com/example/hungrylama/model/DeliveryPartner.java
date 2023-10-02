package com.example.hungrylama.model;

import com.example.hungrylama.Enum.Gender;
import jakarta.persistence.*;
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
@Table(name = "delivery_partner")
public class DeliveryPartner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_partner_id")
    int Id;

    @Column(nullable = false)
    String name;

    @Column(unique = true, nullable = false)
    String contactNumber;

    @Enumerated(EnumType.STRING)
    Gender gender;

    // relations ============================================
    @OneToMany(mappedBy = "deliveryPartner", cascade = CascadeType.ALL)
    List<OrderEntity> orders = new ArrayList<>();
}
