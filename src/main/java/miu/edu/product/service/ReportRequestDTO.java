package miu.edu.product.service;

import lombok.Data;

import java.util.HashMap;

@Data
public class ReportRequestDTO {
    HashMap<String,String> request = new HashMap<>();
}
