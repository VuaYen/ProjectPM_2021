package miu.edu.product.repository;

import miu.edu.product.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query(value = " select count(distinct o.user_id) " +
            " from onlineorder o, OnlineOrder_OrderDetail oi, orderdetail i, product p, vendor v " +
            " where o.id=oi.order_id and oi.details_id=i.id and i.productId=p.productnumber and  p.vendorId=v.id and (:vendorId is null or v.id=:vendorId) group by v.id ", nativeQuery = true)
    public Long countUserByVendorId(@Param("vendorId") String vendorId);

    @Query("select  p from User p where p.status = true ")
    public List<User> getNewSellerAccount();


    @Modifying
    @Query("update User u set u.status=:status where u.userName=:username")
    public void approveNewUserAccount(@Param("status") boolean status, @Param("username") String username);

    Optional<User> findByUserName(String username);
    // Optional<User> findByEmail(String email);


}

