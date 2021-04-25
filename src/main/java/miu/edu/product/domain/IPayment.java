package miu.edu.product.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public  abstract class IPayment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    public abstract void pay();
}