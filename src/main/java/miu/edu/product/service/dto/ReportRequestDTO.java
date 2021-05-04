package miu.edu.product.service.dto;

import lombok.Data;

import java.util.HashMap;

@Data
public class ReportRequestDTO {
    HashMap<String,String> requestParams = new HashMap<>();
}
