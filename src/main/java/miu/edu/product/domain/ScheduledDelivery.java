package miu.edu.product.domain;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@Table(name = "scheduled_deliveries")
public class ScheduledDelivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private OrderStatus status;

    @Embedded
    private Address address;

    //expected delivery date
    private Timestamp deliveryDate;

    //actual delivered date
    private Timestamp deliveredDate;

    @OneToMany(cascade = CascadeType.DETACH, targetEntity = OrderDetail.class, fetch = FetchType.EAGER)
    private List<OrderDetail> details;

    @OneToOne(cascade = CascadeType.DETACH, targetEntity = Vendor.class, fetch = FetchType.EAGER)
    private Vendor vendor;

    @OneToOne(cascade = CascadeType.DETACH, targetEntity = User.class, fetch = FetchType.EAGER)
    private User user;

}
