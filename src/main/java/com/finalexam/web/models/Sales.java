package com.finalexam.web.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name ="sales")
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Salesman name cannot be blank")
    @Column(name = "salesman_name", nullable = false)
    private String salesmanName;

    @NotNull(message = "Product type cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", nullable = false)
    private ProductType productType;

    @NotNull(message = "Amount cannot be null")
    @Min(value = 0, message = "Amount must be a positive number")
    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "transaction_date", nullable = false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate transactionDate;

    @Column(name = "transaction_code", nullable = false, unique = true)
    private String transactionCode;

    // Constructors, getters, and setters

    public Sales() {
        this.transactionDate = LocalDate.now();
        this.transactionCode = generateTransactionCode();
    }

    private String generateTransactionCode() {
        Random random = new Random();
        int code = 100 + random.nextInt(900); // 3-digit random code
        return String.valueOf(code);
    }
}
