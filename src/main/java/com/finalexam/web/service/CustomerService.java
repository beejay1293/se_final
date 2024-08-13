package com.finalexam.web.service;

import com.finalexam.web.models.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    void save(Customer seat);

    List<Customer> findAllCustomer();

    Optional<Customer> findCustomerById(long id);

    void updateCustomer(Customer seat);

    void deleteCustomer(long id);

    boolean isSeatTaken(String seatNumber);
}
