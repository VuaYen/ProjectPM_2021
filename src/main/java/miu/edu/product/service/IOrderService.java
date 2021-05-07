package miu.edu.product.service;

import miu.edu.product.domain.*;

public interface IOrderService {
    OrderResponse checkoutOrder(OrderInput order) throws Exception;

    CartItemResponse deleteCartItem(int cartItemId, String sessionId) throws Exception;

    PagedResponse<ScheduledDeliveryResponse> getUserOrders(int userId, int page, int itemsPerPage, boolean b) throws Exception;

    PagedResponse<ScheduledDeliveryResponse> getVendorOrders(int vendorId, int page, int itemsPerPage, boolean b) throws Exception;

    PagedResponse<ScheduledDeliveryResponse> getActiveOrders(int page, int itemsPerPage, boolean b) throws Exception;

    ScheduledDeliveryResponse updateOrderStatus(int deliveryId, int status) throws Exception;
}
