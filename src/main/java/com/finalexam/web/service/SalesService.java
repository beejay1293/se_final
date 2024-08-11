package com.finalexam.web.service;

import com.finalexam.web.dto.SalesDTO;
import com.finalexam.web.models.Sales;

import java.util.List;
import java.util.Optional;

public interface SalesService {
    void save(Sales sale);

    List<Sales> findAllSales();

    Optional<Sales> findSaleById(long id);

    void updateSale(Sales sale);

    void deleteSale(long id);
}
