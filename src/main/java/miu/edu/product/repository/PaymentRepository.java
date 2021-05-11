package miu.edu.product.repository;
import miu.edu.product.domain.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends CrudRepository<Card, Long> {
    public List<Card> findAll();
    public Card findById(long id);
    public void delete(Card visa);
    public Card save(Card visa);
}

