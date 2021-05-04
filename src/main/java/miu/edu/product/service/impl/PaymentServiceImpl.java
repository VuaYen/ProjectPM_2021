package miu.edu.product.service.impl;

import miu.edu.product.domain.Payment;
import miu.edu.product.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Override
    public Payment save(Payment payment) {

        return null;
    }

    @Override
    public List<Payment> findAll() {
        return null;
    }

    @Override
    public List<Payment> getAllByUserName(String username) {
        return null;
    }

    @Override
    public Payment findById(Long id) {
        return null;
    }

    @Override
    public Payment updatePayment(Payment payment) {
        return null;
    }
}
