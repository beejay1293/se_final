package com.finalexam.web.repositories;

import com.finalexam.web.models.ProductType;
import com.finalexam.web.models.Sales;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class SalesRepositoryTest {

    @Autowired
    private SalesRepository salesRepository;

    private Sales sales;

    @BeforeEach
    void setUp() {
        // Initialize a Sales object
        sales = new Sales();
        sales.setSalesmanName("John Doe");
        sales.setProductType(ProductType.REFRIGERATOR);
        sales.setAmount(500.0);
        sales.setTransactionDate(LocalDate.now());
        sales.setTransactionCode("ABC123");
    }

    @Test
    void shouldSaveSales() {
        // Save the Sales object
        Sales savedSale = salesRepository.save(sales);

        // Verify the sale was saved
        assertThat(savedSale).isNotNull();
        assertThat(savedSale.getId()).isNotNull();
        assertThat(savedSale.getSalesmanName()).isEqualTo("John Doe");
    }

    @Test
    void shouldFindSaleById() {
        // Save the Sales object
        Sales savedSale = salesRepository.save(sales);

        // Retrieve the sale by ID
        Optional<Sales> foundSale = salesRepository.findById(savedSale.getId());

        // Verify the sale was found
        assertThat(foundSale).isPresent();
        assertThat(foundSale.get().getSalesmanName()).isEqualTo("John Doe");
    }

    @Test
    void shouldFindAllSales() {
        // Save the Sales object
        salesRepository.save(sales);

        // Retrieve all sales
        var salesList = salesRepository.findAll();

        // Verify the list contains at least one sale
        assertThat(salesList).isNotEmpty();
    }

    @Test
    void shouldDeleteSales() {
        // Save the Sales object
        Sales savedSale = salesRepository.save(sales);

        // Delete the sale
        salesRepository.deleteById(savedSale.getId());

        // Verify the sale was deleted
        Optional<Sales> deletedSale = salesRepository.findById(savedSale.getId());
        assertThat(deletedSale).isNotPresent();
    }
}
