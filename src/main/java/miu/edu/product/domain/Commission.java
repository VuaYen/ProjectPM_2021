package miu.edu.product.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "commissions")
public class Commission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    double sale;

    double commission;

    double commissionRate;

    Timestamp earnedDate;

    @OneToOne(targetEntity = ScheduledDelivery.class, cascade = CascadeType.ALL)
    ScheduledDelivery delivery;

    @OneToOne(targetEntity = Vendor.class, cascade = CascadeType.ALL)
    Vendor vendor;
}
