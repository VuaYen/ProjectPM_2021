package miu.edu.product.dto;

import lombok.Data;
import miu.edu.product.domain.Address;
import miu.edu.product.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private int order_id;
    private int user_id;
    private String billing_address;
    private Address shipping_address;
    private double tax;
    private OrderStatus status;
    private LocalDateTime order_date;
    private List<OrderDetailDTO> order_detail_list;
}
