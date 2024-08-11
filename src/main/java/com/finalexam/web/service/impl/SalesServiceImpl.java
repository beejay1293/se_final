package com.finalexam.web.service.impl;

import com.finalexam.web.models.Sales;
import com.finalexam.web.repository.SalesRepository;
import com.finalexam.web.service.SalesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalesServiceImpl implements SalesService {
    private final SalesRepository salesRepository;

    public SalesServiceImpl(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    @Override
    public void save(Sales sale) {
        salesRepository.save(sale);
    }

    @Override
    public List<Sales> findAllSales() {
        return salesRepository.findAll();
    }

    @Override
    public Optional<Sales> findSaleById(long id) {
        return salesRepository.findById(id);
    }

    @Override
    public void updateSale(Sales sale) {
        salesRepository.save(sale);
    }

    @Override
    public void deleteSale(long id) {
        salesRepository.deleteById(id);
    }
}
