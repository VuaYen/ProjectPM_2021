package miu.edu.product.service;


import miu.edu.product.domain.OnlineOrder;
import miu.edu.product.domain.OrderStatus;
import miu.edu.product.exception.OrderCreateException;

import java.util.List;

public interface OrderService {

    public void placeOrder(OnlineOrder order) throws OrderCreateException;

    public List<OnlineOrder> getAll();

    public OnlineOrder getOrder(Integer id);

    public OnlineOrder getOrder(String orderNo);

    public OnlineOrder getOrderWithDetails(String orderNo);

    public OnlineOrder getOrderWithDetails(Integer id);

    public void updateStatus(OrderStatus status, Integer id);

}
