package miu.edu.product.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

//@Entity
@Data
//@Table(name = "delivery_addresses")
public class DeliveryAddress extends Address {
    private String receiver;

    private String phone;

    private String email;
}
