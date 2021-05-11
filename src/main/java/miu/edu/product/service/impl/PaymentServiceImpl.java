package miu.edu.product.service.impl;

import miu.edu.product.domain.Card;
import miu.edu.product.domain.Payment;
import miu.edu.product.repository.PaymentRepository;
import miu.edu.product.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @org.springframework.transaction.annotation.Transactional
    public Card save(Card payment){
        return paymentRepository.save(payment);
    }
    @org.springframework.transaction.annotation.Transactional
    public List<Card> getAllPayment(){
        return paymentRepository.findAll();
    }

    @org.springframework.transaction.annotation.Transactional
    public Card findById(long id) {
        return paymentRepository.findById(id);
    }
    @org.springframework.transaction.annotation.Transactional
    public void delete(Card Payment) {
        paymentRepository.delete(Payment);
    }
}
