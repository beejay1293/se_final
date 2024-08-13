//package com.finalexam.web.controllers;
//
//import com.finalexam.web.dto.SeatsDTO;
//import com.finalexam.web.models.Sales;
//import com.finalexam.web.models.SalesSummary;
//import com.finalexam.web.service.SeatsService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class SeatsControllerTest {
//
//    @Mock
//    private SeatsService seatsService;
//
//    @Mock
//    private SeatsSummaryService seatsSummaryService;
//
//    @Mock
//    private BindingResult bindingResult;
//
//    @Mock
//    private Model model;
//
//    @InjectMocks
//    private SeatsController seatsController;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testGetCreateSaleForm() {
//        String view = seatsController.getCreateSaleForm(model);
//        assertEquals("sales-form", view);
//        verify(model, times(1)).addAttribute(eq("sale"), any(SeatsDTO.class));
//    }
//
//    @Test
//    void testListSales() {
//        List<Sales> salesList = new ArrayList<>();
//        when(seatsService.findAllSales()).thenReturn(salesList);
//
//        String view = seatsController.listSales(model);
//        assertEquals("sales", view);
//        verify(model, times(1)).addAttribute("sales", salesList);
//    }
//
//    @Test
//    void testCreateSalesWithValidData() {
//        SeatsDTO seatsDTO = new SeatsDTO();
//        seatsDTO.setAmount(1000.0);
//        seatsDTO.setProductType(ProductType.REFRIGERATOR);
//        seatsDTO.setSalesmanName("John Doe");
//
//        when(bindingResult.hasErrors()).thenReturn(false);
//
//        String view = seatsController.createSales(seatsDTO, bindingResult, model);
//        assertEquals("redirect:/sales", view);
//        verify(seatsService, times(1)).save(any(Sales.class));
//        verify(seatsSummaryService, times(1)).save(any(SalesSummary.class));
//    }
//
//    @Test
//    void testCreateSalesWithInvalidData() {
//        SeatsDTO seatsDTO = new SeatsDTO();
//        when(bindingResult.hasErrors()).thenReturn(true);
//
//        String view = seatsController.createSales(seatsDTO, bindingResult, model);
//        assertEquals("sales-form", view);
//        verify(seatsService, never()).save(any(Sales.class));
//        verify(seatsSummaryService, never()).save(any(SalesSummary.class));
//    }
//
//    @Test
//    void testEditClubForm() {
//        Sales sales = new Sales();
//        when(seatsService.findSaleById(anyLong())).thenReturn(Optional.of(sales));
//
//        String view = seatsController.editClubForm(1L, model);
//        assertEquals("sales-form", view);
//        verify(model, times(1)).addAttribute("sale", sales);
//    }
//
//    @Test
//    void testDeleteSale() {
//        Sales sale = new Sales();
//        sale.setAmount(100.0);
//        sale.setProductType(ProductType.REFRIGERATOR);
//        when(seatsService.findSaleById(anyLong())).thenReturn(Optional.of(sale));
//
//        ResponseEntity<?> response = seatsController.deleteSale(1L);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        verify(seatsService, times(1)).deleteSale(anyLong());
//    }
//
//    @Test
//    void testDeleteSaleNotFound() {
//        when(seatsService.findSaleById(anyLong())).thenReturn(Optional.empty());
//
//        ResponseEntity<?> response = seatsController.deleteSale(1L);
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        verify(seatsService, never()).deleteSale(anyLong());
//    }
//
//    @Test
//    void testGetCompoundInterest() {
//        Sales sale = new Sales();
//        sale.setAmount(1000.0);
//        sale.setProductType(ProductType.REFRIGERATOR);
//
//        when(seatsService.findSaleById(anyLong())).thenReturn(Optional.of(sale));
//
//        String view = seatsController.getCompoundInterest(1L, model);
//        assertEquals("compound-interest", view);
//        verify(model, times(1)).addAttribute(eq("compoundInterestResults"), any());
//    }
//
//    @Test
//    void testGetCompoundInterestSaleNotFound() {
//        when(seatsService.findSaleById(anyLong())).thenReturn(Optional.empty());
//
//        String view = seatsController.getCompoundInterest(1L, model);
//        assertEquals("compound-interest", view);
//        verify(model, times(1)).addAttribute("error", "Sale not found");
//    }
//}
