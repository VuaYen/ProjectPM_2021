package miu.edu.product.repository;

import miu.edu.product.domain.ScheduledDelivery;
import miu.edu.product.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduledDeliveryRepository extends JpaRepository<ScheduledDelivery,Integer> {
    List<ScheduledDelivery> findAllByVendor(Vendor vendor);
}
