package miu.edu.product.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/")
@RestController
@CrossOrigin
public class MailController {
    @Autowired
    MailService mailService;

    @RequestMapping(value = "/mail", method = RequestMethod.POST)
    public Object sendEmailMessage() {
        return mailService.sendMail("yen637@gmail.com");
    }

}
