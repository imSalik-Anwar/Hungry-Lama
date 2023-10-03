package com.example.hungrylama.repository;

import com.example.hungrylama.model.DeliveryPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner, Integer> {
    Optional<DeliveryPartner> findByContactNumber(String contact);


    String query = "Select d From DeliveryPartner d Order By RAND() LIMIT 1";
    @Query(value = query)
    DeliveryPartner findRandom();

    Optional<DeliveryPartner> findByContactNumberOrEmail(String contact, String email);
}
