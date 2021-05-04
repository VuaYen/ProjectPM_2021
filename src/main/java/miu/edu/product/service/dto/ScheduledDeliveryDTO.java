package miu.edu.product.service.dto;

import lombok.Data;
import miu.edu.product.domain.Address;
import miu.edu.product.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ScheduledDeliveryDTO {
    private int order_id;
    private int user_id;
    private Address shipping_address;
    private double tax;
    private OrderStatus status;
    private LocalDateTime delivery_date;
    private List<OrderDetailDTO> order_detail_list;
}
