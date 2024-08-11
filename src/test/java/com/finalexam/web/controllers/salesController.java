package com.finalexam.web.controllers;

import com.finalexam.web.dto.SalesDTO;
import com.finalexam.web.mappers.SalesMapper;
import com.finalexam.web.models.ProductType;
import com.finalexam.web.models.Sales;
import com.finalexam.web.models.SalesSummary;
import com.finalexam.web.service.SalesService;
import com.finalexam.web.service.SalesSummaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SalesControllerTest {

    @Mock
    private SalesService salesService;

    @Mock
    private SalesSummaryService salesSummaryService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    @InjectMocks
    private SalesController salesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCreateSaleForm() {
        String view = salesController.getCreateSaleForm(model);
        assertEquals("sales-form", view);
        verify(model, times(1)).addAttribute(eq("sale"), any(SalesDTO.class));
    }

    @Test
    void testListSales() {
        List<Sales> salesList = new ArrayList<>();
        when(salesService.findAllSales()).thenReturn(salesList);

        String view = salesController.listSales(model);
        assertEquals("sales", view);
        verify(model, times(1)).addAttribute("sales", salesList);
    }

    @Test
    void testCreateSalesWithValidData() {
        SalesDTO salesDTO = new SalesDTO();
        salesDTO.setAmount(1000.0);
        salesDTO.setProductType(ProductType.REFRIGERATOR);
        salesDTO.setSalesmanName("John Doe");

        when(bindingResult.hasErrors()).thenReturn(false);

        String view = salesController.createSales(salesDTO, bindingResult, model);
        assertEquals("redirect:/sales", view);
        verify(salesService, times(1)).save(any(Sales.class));
        verify(salesSummaryService, times(1)).save(any(SalesSummary.class));
    }

    @Test
    void testCreateSalesWithInvalidData() {
        SalesDTO salesDTO = new SalesDTO();
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = salesController.createSales(salesDTO, bindingResult, model);
        assertEquals("sales-form", view);
        verify(salesService, never()).save(any(Sales.class));
        verify(salesSummaryService, never()).save(any(SalesSummary.class));
    }

    @Test
    void testEditClubForm() {
        Sales sales = new Sales();
        when(salesService.findSaleById(anyLong())).thenReturn(Optional.of(sales));

        String view = salesController.editClubForm(1L, model);
        assertEquals("sales-form", view);
        verify(model, times(1)).addAttribute("sale", sales);
    }

    @Test
    void testDeleteSale() {
        Sales sale = new Sales();
        sale.setAmount(100.0);
        sale.setProductType(ProductType.REFRIGERATOR);
        when(salesService.findSaleById(anyLong())).thenReturn(Optional.of(sale));

        ResponseEntity<?> response = salesController.deleteSale(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(salesService, times(1)).deleteSale(anyLong());
    }

    @Test
    void testDeleteSaleNotFound() {
        when(salesService.findSaleById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<?> response = salesController.deleteSale(1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(salesService, never()).deleteSale(anyLong());
    }

    @Test
    void testGetCompoundInterest() {
        Sales sale = new Sales();
        sale.setAmount(1000.0);
        sale.setProductType(ProductType.REFRIGERATOR);

        when(salesService.findSaleById(anyLong())).thenReturn(Optional.of(sale));

        String view = salesController.getCompoundInterest(1L, model);
        assertEquals("compound-interest", view);
        verify(model, times(1)).addAttribute(eq("compoundInterestResults"), any());
    }

    @Test
    void testGetCompoundInterestSaleNotFound() {
        when(salesService.findSaleById(anyLong())).thenReturn(Optional.empty());

        String view = salesController.getCompoundInterest(1L, model);
        assertEquals("compound-interest", view);
        verify(model, times(1)).addAttribute("error", "Sale not found");
    }
}
