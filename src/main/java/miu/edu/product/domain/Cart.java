package miu.edu.product.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Data
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // help us identify a guest user
    // numeric value of the current time
    // generate something random
    @NotNull
    private String sessionId;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = User.class, fetch = FetchType.LAZY)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = CartItem.class, orphanRemoval = true)
    private Set<CartItem> cartItems;
}
