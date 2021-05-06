package miu.edu.product.repository;


import miu.edu.product.domain.Product;
import miu.edu.product.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    //not space between two object
    @Query("SELECT p FROM Product p where p.category.id= :id")
    List<Product> getByCategory(@Param("id") Integer id);

    List<Product> findAllByVendor(Vendor vendor);

    @Query(value = "select p.* " +
            " from scheduled_deliveries o, scheduled_deliveries_OrderDetail oi, orderdetail i, product p, vendor v " +
            " where o.id=oi.scheduled_deliveries_id and oi.details_id=i.id and i.productId=p.productnumber and p.vendorId=v.id " +
            "           and (:fromDate is null or o.deliveryDate>=:fromDate) " +
            "           and (:toDate is null or o.deliveryDate<=:toDate) " +
            "     and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            " group by p.productnumber " +
            " having 1=1 " +
            " and (:minCost is null or sum(i.qty*i.sellPrice)>:minCost) " +
            " and (:maxCost is null or sum(i.qty*i.sellPrice)<:maxCost) "
            , nativeQuery = true)
    public List<Product> findProductByReportRequest(@Param("fromDate") Timestamp fromDate,
                                                    @Param("toDate") Timestamp toDate,
                                                    @Param("vendorId") String vendorId,
                                                    @Param("minCost") String minCost,
                                                    @Param("maxCost") String maxCost);

    @Query(value = "SELECT count(distinct o.id), sum(i.qty*i.sellPrice) " +
            "     FROM scheduled_deliveries o, scheduled_deliveries_OrderDetail oi, orderdetail i, product p, vendor v " +
            "     WHERE o.id=oi.scheduled_deliveries_id and oi.details_id=i.id and i.productId=p.productnumber and p.vendorId=v.id " +
            "           and (:fromDate is null or o.deliveryDate>=:fromDate) " +
            "           and (:toDate is null or o.deliveryDate<=:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            "     GROUP BY p.productnumber "
            , nativeQuery = true)
    public List<Object[]> findProductByReportRequestWithoutGroupBy(@Param("fromDate") Timestamp fromDate,
                                                                   @Param("toDate") Timestamp toDate, @Param("vendorId") String vendorId);

    @Query(value = "SELECT EXTRACT(YEAR FROM o.deliveryDate), count(distinct p.productnumber), sum(i.qty*i.sellPrice) " +
            "     FROM scheduled_deliveries o, scheduled_deliveries_OrderDetail oi, orderdetail i, product p, vendor v  " +
            "     WHERE o.id=oi.scheduled_deliveries_id and oi.details_id=i.id and i.productId=p.productnumber and p.vendorId=v.id " +
            "           and (:fromDate is null or o.deliveryDate>=:fromDate) " +
            "           and (:toDate is null or o.deliveryDate<=:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            "     GROUP BY EXTRACT(YEAR FROM o.deliveryDate)"
            , nativeQuery = true)
    public List<Object[]> findProductByReportRequestWithGroupByYear(@Param("fromDate") Timestamp fromDate,
                                                                    @Param("toDate") Timestamp toDate, @Param("vendorId") String vendorId);

    @Query(value = "SELECT DATE_FORMAT(o.deliveryDate, '%Y %b') as month, count(distinct p.productnumber), sum(i.qty*i.sellPrice) " +
            "    FROM scheduled_deliveries o, scheduled_deliveries_OrderDetail oi, orderdetail i, product p, vendor v " +
            "    WHERE o.id=oi.scheduled_deliveries_id and oi.details_id=i.id and i.productId=p.productnumber and p.vendorId=v.id " +
            "           and (:fromDate is null or o.deliveryDate>=:fromDate) " +
            "           and (:toDate is null or o.deliveryDate<=:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            "    GROUP BY month "
            , nativeQuery = true)
    public List<Object[]> findProductByReportRequestWithGroupByYearMonth(@Param("fromDate") Timestamp fromDate,
                                                                         @Param("toDate") Timestamp toDate, @Param("vendorId") String vendorId);

    @Query(value = "SELECT DATE_FORMAT(o.deliveryDate, '%Y %b %e') as week , count(distinct p.productnumber), sum(i.qty*i.sellPrice) " +
            "    FROM scheduled_deliveries o, scheduled_deliveries_OrderDetail oi, orderdetail i, product p, vendor v " +
            "    WHERE o.id=oi.scheduled_deliveries_id and oi.details_id=i.id and i.productId=p.productnumber and p.vendorId=v.id " +
            "           and (:fromDate is null or o.deliveryDate>=:fromDate) " +
            "           and (:toDate is null or o.deliveryDate<=:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            "    GROUP BY week "
            , nativeQuery = true)
    public List<Object[]> findProductByReportRequestWithGroupByWeek(@Param("fromDate") Timestamp fromDate,
                                                                    @Param("toDate") Timestamp toDate, @Param("vendorId") String vendorId);

    @Query(value = "SELECT EXTRACT(DAY FROM o.deliveryDate), count(distinct p.productnumber), sum(i.qty*i.sellPrice) " +
            "    FROM scheduled_deliveries o, scheduled_deliveries_OrderDetail oi, orderdetail i, product p, vendor v " +
            "    WHERE o.id=oi.scheduled_deliveries_id and oi.details_id=i.id and i.productId=p.productnumber and p.vendorId=v.id " +
            "           and (:fromDate is null or o.deliveryDate>=:fromDate) " +
            "           and (:toDate is null or o.deliveryDate<=:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            "    GROUP BY EXTRACT(DAY FROM o.deliveryDate)"
            , nativeQuery = true)
    public List<Object[]> findProductByReportRequestWithGroupByDay(@Param("fromDate") Timestamp fromDate,
                                                                   @Param("toDate") Timestamp toDate, @Param("vendorId") String vendorId);

}
