package miu.edu.product.repository;

import miu.edu.product.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor,Integer> {

    @Query(value = "select v.* " +
            " from scheduled_deliveries o, scheduled_deliveries_OrderDetail oi, orderdetail i, product p, vendor v " +
            " where o.id=oi.scheduled_deliveries_id and oi.details_id=i.id and i.productId=p.productnumber and p.vendorId=v.id " +
            "     and (:fromDate is null or o.deliveryDate>=:fromDate) " +
            "     and (:toDate is null or o.deliveryDate<=:toDate) " +
            "     and (:vendorId is null or v.id=:vendorId) " +
            " group by v.id " +
            " having 1=1 " +
            " and (:minCost is null or sum(i.qty*i.sellPrice)>:minCost) " +
            " and (:maxCost is null or sum(i.qty*i.sellPrice)<:maxCost) "
            , nativeQuery = true)
    public List<Vendor> findVendorByReportRequest(@Param("fromDate") Timestamp fromDate,
                                                  @Param("toDate") Timestamp toDate,
                                                  @Param("vendorId") String vendorId,
                                                  @Param("minCost") String minCost,
                                                  @Param("maxCost") String maxCost);

    @Query(value = "SELECT count(distinct o.id), sum(i.qty*i.sellPrice) " +
            "     FROM scheduled_deliveries o, scheduled_deliveries_OrderDetail oi, orderdetail i, vendor v " +
            "     WHERE o.id=oi.scheduled_deliveries_id and oi.details_id=i.id and o.vendor_id=v.id " +
            "           and (:fromDate is null or o.deliveryDate>=:fromDate) " +
            "           and (:toDate is null or o.deliveryDate<=:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) " +
            "     GROUP BY v.id "
            , nativeQuery = true)
    public List<Object[]> findVendorByReportRequestWithoutGroupBy(@Param("fromDate") Timestamp fromDate,
                                                                  @Param("toDate") Timestamp toDate, @Param("vendorId") String vendorId);

    @Query(value = "SELECT EXTRACT(YEAR FROM o.deliveryDate), count(distinct v.id), sum(i.qty*i.sellPrice) " +
            "     FROM scheduled_deliveries  o, scheduled_deliveries_OrderDetail oi, orderdetail i, vendor v  " +
            "     WHERE o.id=oi.scheduled_deliveries_id and oi.details_id=i.id and o.vendor_id=v.id " +
            "           and (:fromDate is null or o.deliveryDate>=:fromDate) " +
            "           and (:toDate is null or o.deliveryDate<=:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) " +
            "     GROUP BY EXTRACT(YEAR FROM o.deliveryDate)"
            , nativeQuery = true)
    public List<Object[]> findVendorByReportRequestWithGroupByYear(@Param("fromDate") Timestamp fromDate,
                                                                   @Param("toDate") Timestamp toDate, @Param("vendorId") String vendorId);

    @Query(value = "SELECT DATE_FORMAT(o.deliveryDate, '%Y %b') as month, count(distinct v.id), sum(i.qty*i.sellPrice) " +
            "    FROM scheduled_deliveries  o, scheduled_deliveries_OrderDetail oi, orderdetail i, vendor v " +
            "    WHERE o.id=oi.scheduled_deliveries_id and oi.details_id=i.id and o.vendor_id=v.id " +
            "           and (:fromDate is null or o.deliveryDate>=:fromDate) " +
            "           and (:toDate is null or o.deliveryDate<=:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) " +
            "    GROUP BY month "
            , nativeQuery = true)
    public List<Object[]> findVendorByReportRequestWithGroupByYearMonth(@Param("fromDate") Timestamp fromDate,
                                                                        @Param("toDate") Timestamp toDate, @Param("vendorId") String vendorId);

    @Query(value = "SELECT DATE_FORMAT(o.deliveryDate, '%Y %b %e') as week , count(distinct v.id), sum(i.qty*i.sellPrice) " +
            "    FROM scheduled_deliveries  o, scheduled_deliveries_OrderDetail oi, orderdetail i, vendor v " +
            "    WHERE o.id=oi.scheduled_deliveries_id and oi.details_id=i.id and o.vendor_id=v.id " +
            "           and (:fromDate is null or o.deliveryDate>=:fromDate) " +
            "           and (:toDate is null or o.deliveryDate<=:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) " +
            "    GROUP BY week "
            , nativeQuery = true)
    public List<Object[]> findVendorByReportRequestWithGroupByWeek(@Param("fromDate") Timestamp fromDate,
                                                                   @Param("toDate") Timestamp toDate, @Param("vendorId") String vendorId);

    @Query(value = "SELECT EXTRACT(DAY FROM o.deliveryDate), count(distinct v.id), sum(i.qty*i.sellPrice) " +
            "    FROM scheduled_deliveries  o, scheduled_deliveries_OrderDetail oi, orderdetail i, vendor v " +
            "    WHERE o.id=oi.scheduled_deliveries_id and oi.details_id=i.id and o.vendor_id=v.id " +
            "           and (:fromDate is null or o.deliveryDate>=:fromDate) " +
            "           and (:toDate is null or o.deliveryDate<=:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) " +
            "    GROUP BY EXTRACT(DAY FROM o.deliveryDate)"
            , nativeQuery = true)
    public List<Object[]> findVendorByReportRequestWithGroupByDay(@Param("fromDate") Timestamp fromDate,
                                                                  @Param("toDate") Timestamp toDate, @Param("vendorId") String vendorId);

}
