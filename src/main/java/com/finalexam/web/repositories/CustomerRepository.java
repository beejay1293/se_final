package com.finalexam.web.repositories;

import com.finalexam.web.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findBySeatNumber(String seatNumber);
}
