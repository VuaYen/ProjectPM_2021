package miu.edu.product.service;


import miu.edu.product.domain.Visa;

import java.util.List;

public interface PaymentService {

    public Visa save(Visa payment);
    public List<Visa> getAllPayment();

    public Visa findById(long id);

    public void delete(Visa Payment);
}
