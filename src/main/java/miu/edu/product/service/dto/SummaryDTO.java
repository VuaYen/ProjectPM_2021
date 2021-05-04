package miu.edu.product.service.dto;

import lombok.Data;

@Data
public class SummaryDTO {
    private Long vendorTotal;
    private Long employeeTotal;
    private Long userTotal;
    private Long categoryTotal;
    private Long productTotal;
    private Long orderTotal;
    private Long transactionTotal;
    private Long deliveryTotal;
    private Double costTotal;
}
