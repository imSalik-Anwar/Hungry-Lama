package com.example.hungrylama.model;

import com.example.hungrylama.Enum.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    int Id;

    @Size(min = 2, message = "{validation.name.size.too_short}")
    @Size(max = 40, message = "{validation.name.size.too_long}")
    String name;

    @Email
    @Column(unique = true)
    String email;

    @Enumerated(EnumType.STRING)
    Gender gender;

    @Size(min = 6, message = "{validation.password.size.too_short}")
    @Size(max = 12, message = "{validation.password.size.too_long}")
    @Column(nullable = false)
    String password;

    int zipCode;

    String city;

    int numberOfOrders = 0;

    // relations ====================================================
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    Basket basket;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    List<OrderEntity> orders = new ArrayList<>();
}
