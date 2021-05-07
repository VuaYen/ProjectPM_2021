package miu.edu.product.domain;
import lombok.Data;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String token;

    @NotNull
    private CardType brand;

    @NotNull
    private int last4;

    @NotNull
    @Column(name = "customer_id")
    private String customerId;

    @NotNull
    @Column(name = "exp_month")
    private int expMonth;

    @NotNull
    @Column(name = "exp_year")
    private int expYear;

    @NotNull
    @Column(name = "card_id")
    private String cardId;

    //TODO: Make it not null
    @OneToOne(targetEntity = User.class, cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private User user;
}
