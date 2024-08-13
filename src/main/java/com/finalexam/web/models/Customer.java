package com.finalexam.web.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Random;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name ="customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Salesman name cannot be blank")
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "seatno", nullable = false, unique = true)
    private String seatNumber;

    @Column(name = "tdate", nullable = false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate tDate = LocalDate.now();

    @Column(name = "transaction_code", nullable = false, unique = true)
    private String transactionCode;

    public Customer() {
        this.transactionCode = generateTransactionCode();
    }

    private String generateTransactionCode() {
        Random random = new Random();
        int code = 100 + random.nextInt(900); // 3-digit random code
        return String.valueOf(code);
    }

}
