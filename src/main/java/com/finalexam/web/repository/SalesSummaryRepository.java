package com.finalexam.web.repository;

import com.finalexam.web.models.SalesSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalesSummaryRepository extends JpaRepository<SalesSummary, Long> {
    Optional<SalesSummary> findBySalesmanName(String salesmanName);
}
