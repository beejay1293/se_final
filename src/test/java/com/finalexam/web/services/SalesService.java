package com.finalexam.web.services;

import com.finalexam.web.models.ProductType;
import com.finalexam.web.models.Sales;
import com.finalexam.web.repositories.SalesRepository;
import com.finalexam.web.service.SalesService;
import com.finalexam.web.service.impl.SalesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class SalesServiceImplTest {

    @Mock
    private SalesRepository salesRepository;

    @InjectMocks
    private SalesServiceImpl salesService;

    private Sales sales;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

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
        // Act
        salesService.save(sales);

        // Assert
        ArgumentCaptor<Sales> salesCaptor = ArgumentCaptor.forClass(Sales.class);
        verify(salesRepository, times(1)).save(salesCaptor.capture());
        Sales capturedSale = salesCaptor.getValue();
        assertThat(capturedSale).isEqualTo(sales);
    }

    @Test
    void shouldFindAllSales() {
        // Arrange
        Sales anotherSale = new Sales();
        anotherSale.setSalesmanName("Jane Doe");
        anotherSale.setProductType(ProductType.WASHING_MACHINE);
        anotherSale.setAmount(700.0);
        anotherSale.setTransactionDate(LocalDate.now());
        anotherSale.setTransactionCode("XYZ789");

        when(salesRepository.findAll()).thenReturn(Arrays.asList(sales, anotherSale));

        // Act
        List<Sales> salesList = salesService.findAllSales();

        // Assert
        assertThat(salesList).hasSize(2);
        assertThat(salesList).contains(sales, anotherSale);
    }

    @Test
    void shouldFindSaleById() {
        // Arrange
        when(salesRepository.findById(anyLong())).thenReturn(Optional.of(sales));

        // Act
        Optional<Sales> foundSale = salesService.findSaleById(1L);

        // Assert
        assertThat(foundSale).isPresent();
        assertThat(foundSale.get()).isEqualTo(sales);
    }

    @Test
    void shouldUpdateSales() {
        // Act
        salesService.updateSale(sales);

        // Assert
        verify(salesRepository, times(1)).save(eq(sales));
    }

    @Test
    void shouldDeleteSales() {
        // Act
        salesService.deleteSale(1L);

        // Assert
        verify(salesRepository, times(1)).deleteById(1L);
    }
}
