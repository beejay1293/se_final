package com.finalexam.web.service;

import com.finalexam.web.models.SalesSummary;

import java.util.List;
import java.util.Optional;

public interface SalesSummaryService {
    void save(SalesSummary sale);

    List<SalesSummary> findAll();

    Optional<SalesSummary> findBySalesmanName(String salesSummaryName);

    void update(SalesSummary sale);

    void delete(long id);
}
