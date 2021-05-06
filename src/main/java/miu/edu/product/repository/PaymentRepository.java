package miu.edu.product.repository;
import miu.edu.product.domain.Visa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;
import java.util.List;

@Repository
public interface PaymentRepository extends CrudRepository<Visa, Long> {
    public List<Visa> findAll();
    public Visa findById(long id);
    public void delete(Visa visa);
    public Visa save(Visa visa);
}

