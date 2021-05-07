package miu.edu.product.service;
import miu.edu.product.domain.Payment;
import java.util.List;

public interface PaymentService {

    Payment save(Payment payment);

    List<Payment> findAll();

    List<Payment> getAllByUserName(String username);

    Payment findById(Long id);

    Payment updatePayment(Payment payment);
}
