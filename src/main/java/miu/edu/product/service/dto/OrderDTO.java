package miu.edu.product.service.dto;

import lombok.Data;
import miu.edu.product.domain.Address;
import miu.edu.product.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private int order_id;
    private int user_id;
    private Address billing_address_id;
    private Address shipping_address_id;
    private double tax;
    private OrderStatus status;
    private LocalDateTime order_date;
    private List<OrderDetailDTO> order_detail_list;
}
