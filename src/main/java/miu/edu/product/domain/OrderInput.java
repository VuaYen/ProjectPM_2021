package miu.edu.product.domain;

import lombok.Data;

import java.util.List;

@Data
public class OrderInput {
    private int id;
    private String sessionId;
    private Address shippingAddress;
    private Address billingAddress;

    private String receiverName;
    private String receiverEmail;
    private String receiverPhone;

    List<ChargeModel> charges;
}
