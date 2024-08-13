package com.finalexam.web.mappers;

import com.finalexam.web.dto.CustomerDTO;
import com.finalexam.web.models.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public static CustomerDTO toDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setName(customer.getName());
        dto.setTDate(customer.getTDate());
        dto.setSeatNumber(customer.getSeatNumber());
        return dto;
    }

    public static Customer toEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setSeatNumber(customerDTO.getSeatNumber());
        customer.setTDate(customerDTO.getTDate());
        return customer;
    }
}
