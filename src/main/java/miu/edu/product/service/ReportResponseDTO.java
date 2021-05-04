package miu.edu.product.service;

import lombok.Data;

import java.util.HashMap;

@Data
public class ReportResponseDTO {
    HashMap<String,Object> response = new HashMap<>();
}
