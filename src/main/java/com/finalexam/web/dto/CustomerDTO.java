package com.finalexam.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private Long id;

    @NotEmpty(message = "Customer name cannot be blank")
    private String name;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate tDate;

    private String seatNumber;
    private String transactionCode;
}