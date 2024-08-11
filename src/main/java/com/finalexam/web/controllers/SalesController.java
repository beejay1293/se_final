package com.finalexam.web.controllers;

import com.finalexam.web.dto.SalesDTO;
import com.finalexam.web.mappers.SalesMapper;
import com.finalexam.web.models.ProductType;
import com.finalexam.web.models.Sales;
import com.finalexam.web.models.SalesSummary;
import com.finalexam.web.service.SalesService;
import com.finalexam.web.service.SalesSummaryService;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class SalesController {
    private final SalesService salesService;
    private final SalesSummaryService salesSummaryService;
    private static final Logger logger = LoggerFactory.getLogger(SalesController.class);


    public SalesController(SalesService salesService, SalesSummaryService salesSummaryService) {
        this.salesService = salesService;
        this.salesSummaryService = salesSummaryService;
    }

    @GetMapping("/")
    public String getCreateSaleForm(Model model) {
        SalesDTO sale = new SalesDTO();
        sale.setTransactionDate(LocalDate.now());
        model.addAttribute("sale", sale);
        model.addAttribute("refrigerator", ProductType.REFRIGERATOR);
        model.addAttribute("music_system", ProductType.MUSIC_SYSTEM);
        model.addAttribute("washing_machine", ProductType.WASHING_MACHINE);
        return "sales-form";
    }

    @GetMapping("/sales")
    public String listSales(Model model) {
        List<Sales> sales = salesService.findAllSales();
        model.addAttribute("sales", sales);
        return "sales";
    }

    @PostMapping("/sales")
    public String createSales(@Valid @ModelAttribute("sale") SalesDTO sales, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Error {}  exists", result.getAllErrors());
            model.addAttribute("sale", sales);
            return "sales-form";
        }

        Sales sale = SalesMapper.toEntity(sales);

        Long id = sales.getId();
        if (id != null) {
            sale.setId(sales.getId());
            Optional<Sales> s = salesService.findSaleById(id);
            if (s.isPresent()) {
                double deduction = -s.get().getAmount();
                createOrUpdateSalesSummary(s.get().getSalesmanName(), s.get().getProductType(), deduction);
            }
        }

        String code = sales.getTransactionCode();
        if (code != null && !code.isEmpty()) {
            sale.setTransactionCode(code);
        }
        salesService.save(sale);
        createOrUpdateSalesSummary(sale.getSalesmanName(), sale.getProductType(), sales.getAmount());
        return "redirect:/sales";
    }

    public void createOrUpdateSalesSummary(String name, ProductType product, double amount) {
        Optional<SalesSummary> optionalSale = salesSummaryService.findBySalesmanName(name);
        SalesSummary ss = optionalSale.orElseGet(SalesSummary::new);

        // Set the Salesman Name if it's a new SalesSummary
        if (ss.getSalesmanName() == null) {
            ss.setSalesmanName(name);
        }

        // Update the relevant product amount based on ProductType
        switch (product) {
            case WASHING_MACHINE:
                ss.setWashingMachineAmountSold(ss.getWashingMachineAmountSold() + amount);
                break;
            case REFRIGERATOR:
                ss.setRefrigeratorAmountSold(ss.getRefrigeratorAmountSold() + amount);
                break;
            case MUSIC_SYSTEM:
                ss.setMusicSystemAmountSold(ss.getMusicSystemAmountSold() + amount);
                break;
            default:
                throw new IllegalArgumentException("Invalid product type: " + product);
        }
        logger.info("Summary =  {}", ss);

        salesSummaryService.save(ss);
    }

    @GetMapping("/sales/edit")
    public String editClubForm(@RequestParam("id") long saleId, Model model) {
        Optional<Sales> optionalSale = salesService.findSaleById(saleId);
        Sales sale = optionalSale.orElseGet(Sales::new); // Provide a new Sales object if not found
        model.addAttribute("sale", sale);
        model.addAttribute("refrigerator", ProductType.REFRIGERATOR);
        model.addAttribute("music_system", ProductType.MUSIC_SYSTEM);
        model.addAttribute("washing_machine", ProductType.WASHING_MACHINE);
        return "sales-form";
    }

    @DeleteMapping("/sales/{saleId}")
    public ResponseEntity<?> deleteSale(@PathVariable("saleId") long saleId) {
        logger.info("SaleID: {}", saleId);

        if (salesService.findSaleById(saleId).isEmpty()) {
            return new ResponseEntity<>(new ErrorResponse("Sale not found"), HttpStatus.BAD_REQUEST);
        }

        salesService.deleteSale(saleId);
        return new ResponseEntity<>(new SuccessResponse("Request submitted successfully"), HttpStatus.OK);
    }

    @Setter
    @Getter
    public static class ErrorResponse {
        private String message;
        public ErrorResponse(String message) {
            this.message = message;
        }
    }

    @Setter
    @Getter
    public static class SuccessResponse {
        private String message;
        private Boolean success = true;

        public SuccessResponse(String message) {
            this.message = message;
        }
    }

    @GetMapping("/sales-summary")
    public String listSalesSummary(Model model) {
        List<SalesSummary> salesSummary = salesSummaryService.findAll();
        model.addAttribute("salesSummary", salesSummary);
        return "sales-summary";
    }

    @GetMapping("/sales/compound-interest")
    public String getCompoundInterest(@RequestParam("id") long saleId, Model model) {
        Optional<Sales> sale = salesService.findSaleById(saleId);

        if (sale.isPresent()) {
            Sales sales = sale.get();
            double startingAmount = sales.getAmount();
            double interestRate;

            // Determine the interest rate based on the product type
            if (sales.getProductType() == ProductType.REFRIGERATOR) {
                interestRate = 0.15;
            } else {
                interestRate = 0.10;
            }

            // Create a list to store compound interest calculations for 5 years
            List<CompoundInterestResult> compoundInterestResults = new ArrayList<>();

            // Calculate compound interest for 5 years
            for (int year = 1; year <= 5; year++) {
                double interest = startingAmount * interestRate;
                double endingBalance = startingAmount + interest;

                compoundInterestResults.add(new CompoundInterestResult(year, startingAmount, interest, endingBalance));

                // Use the ending balance of the current year as the starting amount for the next year
                startingAmount = endingBalance;
            }

            model.addAttribute("compoundInterestResults", compoundInterestResults);
        } else {
            model.addAttribute("error", "Sale not found");
        }
        return "compound-interest";
    }

    // Data structure to hold compound interest calculation results
    @Getter
    @Setter
    public static class CompoundInterestResult {
        // Getters and Setters
        private int year;
        private double startingAmount;
        private double interest;
        private double endingBalance;

        public CompoundInterestResult(int year, double startingAmount, double interest, double endingBalance) {
            this.year = year;
            this.startingAmount = startingAmount;
            this.interest = interest;
            this.endingBalance = endingBalance;
        }

        public String getStartingAmount() {
            return String.format("%.2f", startingAmount);
        }

        public String getInterest() {
            return String.format("%.2f", interest);
        }

        public String getEndingBalance() {
            return String.format("%.2f", endingBalance);
        }
    }
}
