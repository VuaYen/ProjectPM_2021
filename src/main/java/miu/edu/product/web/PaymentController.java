package miu.edu.product.web;


import miu.edu.product.domain.Visa;
import miu.edu.product.service.PaymentService;
import miu.edu.product.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    PaymentService paymentService;
    TransactionService transactionService;


    //Check validity
    public boolean checkCardValidity(Visa payment){
        boolean cardNum;
        Calendar calendar = Calendar.getInstance();
        String regex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" + "(?<mastercard>5[1-5][0-9]{14}))";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(payment.getCardNumber());

        if(matcher.matches()){
            if(payment.getExperienceDate().before(calendar.getTime())){
                return matcher.matches();
            }
        }
        return matcher.matches();

    }


}
