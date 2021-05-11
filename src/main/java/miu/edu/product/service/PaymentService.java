package miu.edu.product.service;
import miu.edu.product.domain.Card;
import miu.edu.product.domain.Payment;
import java.util.List;

public interface PaymentService {
    public Card save(Card payment);
    public List<Card> getAllPayment();

    public Card findById(long id);

    public void delete(Card Payment);
}
