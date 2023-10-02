package com.example.hungrylama.repository;

import com.example.hungrylama.model.DeliveryPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner, Integer> {
    Optional<DeliveryPartner> findByContactNumber(String contact);
}
