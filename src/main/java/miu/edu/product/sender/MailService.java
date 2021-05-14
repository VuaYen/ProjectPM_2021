package miu.edu.product.sender;

import org.springframework.web.bind.annotation.RequestBody;

public interface MailService {
    Object sendMailA();
    Object sendMailMulti();
    Object sendMailHTML();
    Object sendMailCC();
    Object sendMail(@RequestBody String to);
    Object sendMail(@RequestBody String to,String content);
}
