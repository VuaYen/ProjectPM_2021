package miu.edu.product.repository;

import miu.edu.product.domain.OnlineOrder;
import miu.edu.product.domain.OrderStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface OnlineOrderRepository extends CrudRepository<OnlineOrder, Integer> {
    @Modifying
    @Query("update OnlineOrder s set s.status=:status where s.id=:id")
    public void updateStatus(@Param("status") OrderStatus status, @Param("id") Integer id);

    @Modifying
    @Query("update OnlineOrder s set s.dateDelivered=:date where s.id=:id")
    public void updateDateDelivered(@Param("date") Date date, @Param("id") Integer id);
}
