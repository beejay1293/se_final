package com.finalexam.web.controllers;

import com.finalexam.web.dto.CustomerDTO;
import com.finalexam.web.mappers.CustomerMapper;
import com.finalexam.web.models.Customer;
import com.finalexam.web.service.CustomerService;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class CustomerController {
    private final CustomerService customerService;
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/")
    public String getMainPageData(Model model) {
        CustomerDTO customer = new CustomerDTO();
        customer.setTDate(LocalDate.now());
        List<Customer> customers = customerService.findAllCustomer();
        model.addAttribute("customer", customer);
        model.addAttribute("customers", customers);

        // Create a 2D array representing the seats
        String[][] seats = new String[4][5];
        char[] columns = {'A', 'B', 'C', 'D', 'E'};

        // Initialize the count of remaining seats
        int remainingSeats = 20;

        // Initialize the seats with seat codes (1A, 1B, ..., 4E)
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 5; col++) {
                seats[row][col] = (row + 1) + String.valueOf(columns[col]);

            }
        }

        // Replace the seat code with the customer name if the seat is reserved
        for (Customer cus : customers) {
            String reservedSeat = cus.getSeatNumber();
            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 5; col++) {
                    if (seats[row][col].equals(reservedSeat)) {
                        seats[row][col] = cus.getName();
                        remainingSeats--; // Decrement the count of remaining seats
                    }
                }
            }
        }

        // Add the seat map to the model to be displayed in the view
        model.addAttribute("seats", seats);
        model.addAttribute("remainingSeats", remainingSeats);
        return "main";
    }

    @GetMapping("/customer/edit")
    public String editCustomerForm(@RequestParam("id") long customerId, Model model) {
        Optional<Customer> optionalSale = customerService.findCustomerById(customerId);
        Customer customer = optionalSale.orElseGet(Customer::new); // Provide a new Customer object if not found
        model.addAttribute("customer", customer);
        return "edit";
    }

    @PostMapping("/")
    public String reserveSeat(@Valid @ModelAttribute("customer") CustomerDTO customer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Error {}  exists", result.getAllErrors());
            model.addAttribute("customer", customer);
            return "main";
        }

        // Check if the seat is already reserved
        boolean seatTaken = customerService.isSeatTaken(customer.getSeatNumber());
        if (seatTaken) {
            // Add an error message to the model
            model.addAttribute("seatTakenError", "The seat " + customer.getSeatNumber() + " is already taken.");
            model.addAttribute("customer", customer);
            return "redirect:/";
        }

        Customer newCustomer = CustomerMapper.toEntity(customer);

        Long id = customer.getId();
        if (id != null) {
            newCustomer.setId(id);
        }

        String code = customer.getTransactionCode();
        if (code != null && !code.isEmpty()) {
            customer.setTransactionCode(code);
        }

        customerService.save(newCustomer);
        return "redirect:/";
    }



    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("customerId") long customerId) {
        logger.info("customerID: {}", customerId);
        Optional<Customer> cus = customerService.findCustomerById(customerId);
        if (cus.isEmpty()) {
            return new ResponseEntity<>(new ErrorResponse("Customer not found"), HttpStatus.BAD_REQUEST);
        }

        customerService.deleteCustomer(customerId);
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



}
