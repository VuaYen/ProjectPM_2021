package miu.edu.product.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miu.edu.product.sender.Subject;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OnlineOrder extends Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Date dateDelivered;

    @NotNull
    @Future
    private Date dateShipping;

    private Double tax;
    private Date dateCreate;
    private Double shippingFee;
    private Double total;
    private String orderno;
//    @Embedded
    private String shippingAddress;
    private OrderStatus status;


    public String getsStatus() {
        return status.name();
    }

    @Transient
    private String sStatus;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId")
    private List<OrderDetail> orderDetailList;
    @OneToOne
    private User customer;




}
