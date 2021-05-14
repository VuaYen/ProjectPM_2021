package miu.edu.product.service;


import miu.edu.product.domain.User;

import java.util.List;

public interface IUserService {
    public List<User> all();
    public  boolean createUser(User user);
    public User saveProfile(User user);

    public List<User> getNewVendorUser();

    public void approveNewUser(boolean status, String username);

    public <T extends User> T getByUsername(String username);
    public User save(User user);
    public User saveRole(User user);
}
