//package com.finalexam.web.repositories;
//
//import com.finalexam.web.models.Sales;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@ActiveProfiles("test")
//class SeatsRepositoryTest {
//
//    @Autowired
//    private SeatsRepository seatsRepository;
//
//    private Sales sales;
//
//    @BeforeEach
//    void setUp() {
//        // Initialize a Sales object
//        sales = new Sales();
//        sales.setSalesmanName("John Doe");
//        sales.setProductType(ProductType.REFRIGERATOR);
//        sales.setAmount(500.0);
//        sales.setTransactionDate(LocalDate.now());
//        sales.setTransactionCode("ABC123");
//    }
//
//    @Test
//    void shouldSaveSales() {
//        // Save the Sales object
//        Sales savedSale = seatsRepository.save(sales);
//
//        // Verify the sale was saved
//        assertThat(savedSale).isNotNull();
//        assertThat(savedSale.getId()).isNotNull();
//        assertThat(savedSale.getSalesmanName()).isEqualTo("John Doe");
//    }
//
//    @Test
//    void shouldFindSaleById() {
//        // Save the Sales object
//        Sales savedSale = seatsRepository.save(sales);
//
//        // Retrieve the sale by ID
//        Optional<Sales> foundSale = seatsRepository.findById(savedSale.getId());
//
//        // Verify the sale was found
//        assertThat(foundSale).isPresent();
//        assertThat(foundSale.get().getSalesmanName()).isEqualTo("John Doe");
//    }
//
//    @Test
//    void shouldFindAllSales() {
//        // Save the Sales object
//        seatsRepository.save(sales);
//
//        // Retrieve all sales
//        var salesList = seatsRepository.findAll();
//
//        // Verify the list contains at least one sale
//        assertThat(salesList).isNotEmpty();
//    }
//
//    @Test
//    void shouldDeleteSales() {
//        // Save the Sales object
//        Sales savedSale = seatsRepository.save(sales);
//
//        // Delete the sale
//        seatsRepository.deleteById(savedSale.getId());
//
//        // Verify the sale was deleted
//        Optional<Sales> deletedSale = seatsRepository.findById(savedSale.getId());
//        assertThat(deletedSale).isNotPresent();
//    }
//}
