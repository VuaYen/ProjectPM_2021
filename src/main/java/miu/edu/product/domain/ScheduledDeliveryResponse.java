package miu.edu.product.domain;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ScheduledDeliveryResponse {

    int id;
    OrderStatus status;
    private Address address;
    private Timestamp deliveryDate;
    private Timestamp deliveredDate;
    private List<OrderDetailResponse> details;
    private VendorResponse vendor;
    private UserResponse user;

    public ScheduledDeliveryResponse() {

    }

    public ScheduledDeliveryResponse(ScheduledDelivery scheduledDelivery) {
        id = scheduledDelivery.getId();
        status = scheduledDelivery.getStatus();
        address = scheduledDelivery.getAddress();
        deliveryDate = scheduledDelivery.getDeliveryDate();
        deliveredDate = scheduledDelivery.getDeliveredDate();
        if (scheduledDelivery.getDetails() != null && scheduledDelivery.getDetails().size() > 0) {
            details = scheduledDelivery.getDetails().stream().map(OrderDetailResponse::new).collect(Collectors.toList());
        }

        if (scheduledDelivery.getVendor() != null) {
            vendor = new VendorResponse((int)scheduledDelivery.getVendor().getId(), scheduledDelivery.getVendor().getUserName());
        }

        if (scheduledDelivery.getUser() != null) {
            user = new UserResponse((int)scheduledDelivery.getUser().getId(), scheduledDelivery.getUser().getUserName());
        }
    }

    @Data
    public class OrderDetailResponse {
        int id;
        String name;
        double rate;
        int quantity;
        String image;

        public OrderDetailResponse() {

        }

        public OrderDetailResponse(OrderDetail detail) {
            id = detail.getId();
            rate = detail.getSellPrice();
            quantity = detail.getQty();
            if (detail.getProduct() != null) {
                name = detail.getProduct().getName();
                if (detail.getProduct().getPhoto() != null && detail.getProduct().getPhoto().length() > 0) {
                    String photo = detail.getProduct().getPhoto();
                }
            }
        }
    }

    @Data
    public class VendorResponse {
        int id;
        String name;

        public VendorResponse() {

        }

        public VendorResponse(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    @Data
    public class UserResponse {
        int id;
        String name;

        public UserResponse() {

        }

        public UserResponse(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
