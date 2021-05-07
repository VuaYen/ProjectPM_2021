package miu.edu.product.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity()
@Data
@Table(name = "transactions")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue(value = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(targetEntity = Card.class, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Card card;

    @Column(columnDefinition = "DOUBLE DEFAULT 0.00")
    private double amount;

    private Timestamp createdAt;
}
