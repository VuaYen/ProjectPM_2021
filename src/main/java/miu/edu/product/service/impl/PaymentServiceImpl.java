//package miu.edu.product.service.impl;
//
//import miu.edu.product.domain.Payment;
//import miu.edu.product.repository.PaymentRepository;
//import miu.edu.product.service.PaymentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.List;
//
//@Service
//public class PaymentServiceImpl implements PaymentService {
//    @Autowired
//    PaymentRepository paymentRepository;
//
//    @Transactional
//    public Payment save(Payment payment){
//        return paymentRepository.save(payment);
//    }
//    @Transactional
//    public List<Payment> getAllPayment(){
//        return paymentRepository.findAll();
//    }
//
//    @Transactional
//    public Payment findById(long id) {
//        return paymentRepository.findById(id);
//    }
//    @Transactional
//    public void delete(Payment Payment) {
//        paymentRepository.delete(Payment);
//    }
//}
