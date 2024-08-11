package com.finalexam.web.service.impl;

import com.finalexam.web.models.SalesSummary;
import com.finalexam.web.repository.SalesSummaryRepository;
import com.finalexam.web.service.SalesSummaryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalesSummaryServiceImpl  implements SalesSummaryService {
    private final SalesSummaryRepository salesSummaryRepository;

    public SalesSummaryServiceImpl(SalesSummaryRepository salesSummaryRepository) {
        this.salesSummaryRepository = salesSummaryRepository;
    }

    @Override
    public void save(SalesSummary sale) {
        salesSummaryRepository.save(sale);
    }

    @Override
    public List<SalesSummary> findAll() {
        return salesSummaryRepository.findAll();
    }

    @Override
    public Optional<SalesSummary> findBySalesmanName(String name) {
        return salesSummaryRepository.findBySalesmanName(name);
    }

    @Override
    public void update(SalesSummary sale) {
        salesSummaryRepository.save(sale);
    }

    @Override
    public void delete(long id) {
        salesSummaryRepository.deleteById(id);
    }
}
