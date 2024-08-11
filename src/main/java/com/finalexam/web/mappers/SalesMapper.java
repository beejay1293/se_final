package com.finalexam.web.mappers;

import com.finalexam.web.dto.SalesDTO;
import com.finalexam.web.models.Sales;
import org.springframework.stereotype.Component;

@Component
public class SalesMapper {

    public static SalesDTO toDTO(Sales sales) {
        SalesDTO dto = new SalesDTO();
        dto.setSalesmanName(sales.getSalesmanName());
        dto.setProductType(sales.getProductType());
        dto.setAmount(sales.getAmount());
        dto.setTransactionDate(sales.getTransactionDate());
        dto.setTransactionCode(sales.getTransactionCode());
        return dto;
    }

    public static Sales toEntity(SalesDTO salesDTO) {
        Sales sales = new Sales();
        sales.setSalesmanName(salesDTO.getSalesmanName());
        sales.setProductType(salesDTO.getProductType());
        sales.setAmount(salesDTO.getAmount());
        sales.setTransactionDate(salesDTO.getTransactionDate());
        return sales;
    }
}
