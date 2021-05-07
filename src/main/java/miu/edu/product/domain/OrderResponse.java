package miu.edu.product.domain;

import lombok.Data;

@Data
public class OrderResponse {
    int id;
    String name;
    double total;

    public OrderResponse(OnlineOrder order) {
        id = order.getId();
        name = order.getCustomer().getUserName();
        total = order.getOrderDetailList().stream().map(e -> e.getSellPrice() * e.getQty()).reduce(0.0, Double::sum);
        total += (total * 0.07);
    }
}
