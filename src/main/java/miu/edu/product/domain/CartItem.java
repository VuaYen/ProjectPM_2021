package miu.edu.product.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Data
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.DETACH, targetEntity = Product.class)
    private Product product;

    @Column(columnDefinition = "int default 1")
    @NotNull
    private int quantity;

    @Column(columnDefinition = "int default 0.00")
    @NotNull
    private double rate;
}
