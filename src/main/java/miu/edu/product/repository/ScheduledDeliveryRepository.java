package miu.edu.product.repository;

import miu.edu.product.domain.OrderStatus;
import miu.edu.product.domain.ScheduledDelivery;
import miu.edu.product.domain.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduledDeliveryRepository extends JpaRepository<ScheduledDelivery,Integer> {
    List<ScheduledDelivery> findAllByVendor(Vendor vendor);
    Page<ScheduledDelivery> findAllByUserIdAndStatusIn(int userId, List<OrderStatus> statusList, Pageable paging);

    Page<ScheduledDelivery> findAllByVendorIdAndStatusIn(int vendorId, List<OrderStatus> statusList, Pageable paging);

    Page<ScheduledDelivery> findAllByStatusIn(List<OrderStatus> statusList, Pageable paging);

}
