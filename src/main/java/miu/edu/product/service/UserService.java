package miu.edu.product.service;

import miu.edu.product.domain.User;
import miu.edu.product.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    public User save(User user){
        return userRepository.save(user);
    }

    @Override
    public boolean createUser(User user) {
//        Optional<User> existUser = userRepository.findById(user.getId());
//        if(existUser.isPresent()){
//            return false;
//        }
//
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String password = user.getPassword();
//        String encryptedPassword = encoder.encode(password);
//        user.setPassword(encryptedPassword);
//
//        T createdUser = (T)userRepository.save(user);
//
//        return (createdUser != null);
        return false;
    }

    @Override
    public <T extends User> T saveProfile(T user) {
        return null;
    }

    @Override
    public List<User> getNewVendorUser() {

        return null;
    }

    @Override
    public void approveNewUser(boolean status, String username) {
        userRepository.approveNewUserAccount(status,username);
    }

    @Override
    public <T extends User> T getByUsername(String username) {
//        Optional<T> opt = userRepository.findById(username);
//        if (opt.isPresent()) {
//            return opt.get();
//        }
        return null;
    }
}
