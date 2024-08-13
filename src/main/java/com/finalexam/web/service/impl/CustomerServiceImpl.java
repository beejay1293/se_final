package com.finalexam.web.service.impl;

import com.finalexam.web.models.Customer;
import com.finalexam.web.repositories.CustomerRepository;
import com.finalexam.web.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public List<Customer> findAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findCustomerById(long id) {
        return customerRepository.findById(id);
    }

    @Override
    public void updateCustomer(Customer seat) {
            customerRepository.save(seat);
    }

    @Override
    public void deleteCustomer(long id) {
        customerRepository.deleteById(id);
    }

    public boolean isSeatTaken(String seatNumber) {
        return customerRepository.findBySeatNumber(seatNumber).isPresent();
    }
}
