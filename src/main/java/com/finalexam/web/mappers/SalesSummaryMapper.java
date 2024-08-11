package com.finalexam.web.mappers;

import com.finalexam.web.dto.SalesSummaryDTO;
import com.finalexam.web.models.SalesSummary;
import org.springframework.stereotype.Component;

@Component
public class SalesSummaryMapper {

    public SalesSummaryDTO toDTO(SalesSummary salesSummary) {
        return new SalesSummaryDTO(
                salesSummary.getId(),
                salesSummary.getSalesmanName(),
                salesSummary.getWashingMachineAmountSold(),
                salesSummary.getRefrigeratorAmountSold(),
                salesSummary.getMusicSystemAmountSold()
        );
    }

    public SalesSummary toEntity(SalesSummaryDTO salesSummaryDTO) {
        return new SalesSummary(
                salesSummaryDTO.getSalesmanName(),
                salesSummaryDTO.getWashingMachineAmountSold(),
                salesSummaryDTO.getRefrigeratorAmountSold(),
                salesSummaryDTO.getMusicSystemAmountSold()
        );
    }
}
