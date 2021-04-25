package miu.edu.product.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Master extends IPayment {

    private String cardNumber;
    private String cvs;
    private Date experienceDate;
    private String nameOnCard;
    private String type;
    private double value;
    @Override
    public void pay() {

    }
}
