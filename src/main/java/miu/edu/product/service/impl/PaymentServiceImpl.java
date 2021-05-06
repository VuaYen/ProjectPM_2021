package miu.edu.product.service.impl;

import miu.edu.product.domain.Visa;
import miu.edu.product.repository.PaymentRepository;
import miu.edu.product.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Transactional
    public Visa save(Visa payment){
        return paymentRepository.save(payment);
    }
    @Transactional
    public List<Visa> getAllPayment(){
        return paymentRepository.findAll();
    }

    @Transactional
    public Visa findById(long id) {
        return paymentRepository.findById(id);
    }
    @Transactional
    public void delete(Visa Payment) {
        paymentRepository.delete(Payment);
    }
}
