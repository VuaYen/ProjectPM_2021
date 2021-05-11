package miu.edu.product.domain;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //    @NotNull
    private String token;

    //@NotNull
    private CardType brand;

    //@NotNull
    private int last4;

    //    @NotNull
//    @Column(name = "customer_id")
    private String customerId;

    @NotNull
    @Range(min=1,max = 12,message = "Enter correct month")
    @Column(name = "exp_month")
    private int expMonth;

    @NotNull
    @Range(min=2021,message = "Expired Card")
    @Column(name = "exp_year")
    private int expYear;

    //    @NotNull
//    @Column(name = "card_id")
    private String cardId;

    //TODO: Make it not null
    @OneToOne(targetEntity = User.class, cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private User user;
    @NotBlank
    @Size(min = 10, max = 20,message = "Please Enter Correct Card Number")
    private String cardNumber;
    @NotNull
    private String cvs;
    private Date experienceDate;
    @NotNull
    private String nameOnCard;
    private String type;
    private double value;
    @Embedded
    @Valid
    private Address shipping;
}
