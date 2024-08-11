package com.finalexam.web.dto;

import com.finalexam.web.models.ProductType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Random;

@Data
@Builder
@NoArgsConstructor
public class SalesSummaryDTO {
    private Long id;

    private String salesmanName;

    private double washingMachineAmountSold;

    private double refrigeratorAmountSold;

    private double musicSystemAmountSold;

    public SalesSummaryDTO(Long id, String salesmanName, double washingMachineAmountSold, double refrigeratorAmountSold, double musicSystemAmountSold) {
        this.id = id;
        this.salesmanName = salesmanName;
        this.washingMachineAmountSold = washingMachineAmountSold;
        this.refrigeratorAmountSold = refrigeratorAmountSold;
        this.musicSystemAmountSold = musicSystemAmountSold;
    }
}
