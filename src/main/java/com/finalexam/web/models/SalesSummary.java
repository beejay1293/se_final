package com.finalexam.web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sales_summary")
public class SalesSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "salesman_name", nullable = false)
    private String salesmanName;

    @Column(name = "washing_machine_amount_sold", nullable = false)
    private double washingMachineAmountSold;

    @Column(name = "refrigerator_amount_sold", nullable = false)
    private double refrigeratorAmountSold;

    @Column(name = "music_system_amount_sold", nullable = false)
    private double musicSystemAmountSold;

    public SalesSummary() {}

    public SalesSummary(String salesmanName, double washingMachineAmountSold, double refrigeratorAmountSold, double musicSystemAmountSold) {
        this.salesmanName = salesmanName;
        this.washingMachineAmountSold = washingMachineAmountSold;
        this.refrigeratorAmountSold = refrigeratorAmountSold;
        this.musicSystemAmountSold = musicSystemAmountSold;
    }

}
