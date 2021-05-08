package miu.edu.product.service.impl;

import miu.edu.product.domain.User;
import miu.edu.product.repository.UserRepository;
import miu.edu.product.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }


}
