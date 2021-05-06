package miu.edu.product.web;


import miu.edu.product.domain.*;
import miu.edu.product.dto.Cart;
import miu.edu.product.dto.CheckOutModel;
import miu.edu.product.dto.RemoveCartModel;
import miu.edu.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.Calendar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class PaymentController {




    /////////////////


//    //Check validity
//    public boolean checkCardValidity(Visa payment){
//        boolean cardNum;
//        Calendar calendar = Calendar.getInstance();
//        String regex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" + "(?<mastercard>5[1-5][0-9]{14}))";
//
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(payment.getCardNumber());
//
//        if(matcher.matches()){
//            if(payment.getExperienceDate().before(calendar.getTime())){
//                return matcher.matches();
//            }
//        }
//        return matcher.matches();
//
//    }


}
