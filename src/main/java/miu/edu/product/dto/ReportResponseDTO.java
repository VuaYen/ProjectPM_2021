package miu.edu.product.dto;

import lombok.Data;

import java.util.HashMap;

@Data
public class ReportResponseDTO {
    HashMap<String,Object> responseData = new HashMap<>();
}
