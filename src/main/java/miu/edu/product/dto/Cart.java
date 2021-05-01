package miu.edu.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miu.edu.product.domain.OnlineOrder;


import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
public class Cart {
    private int total;
    private HashMap<String, OnlineOrder> orderList = new HashMap<>();
}
