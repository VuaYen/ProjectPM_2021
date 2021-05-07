package miu.edu.product.repository;

import miu.edu.product.domain.OrderDetail;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends CrudRepository<OrderDetail, Integer> {
    @Query(value="SELECT * FROM ORDERDETAIL  Where orderId= ?1",  nativeQuery = true )
    public List<OrderDetail> getDetailsByOrderId(Integer id);

    @Modifying
    @Query(value="DELETE FROM ORDERDETAIL  Where productId= ?1",  nativeQuery = true )
    public void deleteByProductId(Integer id);
}
