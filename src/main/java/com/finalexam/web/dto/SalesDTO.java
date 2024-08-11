package com.finalexam.web.dto;

import com.finalexam.web.models.ProductType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Random;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesDTO {
    private Long id;

    @NotEmpty(message = "Salesman name cannot be blank")
    private String salesmanName;

    @NotNull(message = "Product type cannot be null")
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Min(value = 0, message = "Amount must be a positive number")
    private Double amount;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate transactionDate;
    private String transactionCode;
}