package miu.edu.product.repository;

import miu.edu.product.domain.ScheduledDelivery;
import miu.edu.product.domain.OnlineOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface OrderRepository extends JpaRepository<ScheduledDelivery,Integer> {

    @Query(value="select o.* " +
            " from scheduled_deliveries o, scheduled_deliveries_OrderDetail oi, orderdetail i,  vendor v " +
            " where o.id=oi.scheduled_deliveries_id and oi.orderDetailList_id=i.id and o.vendor_id=v.id " +
            "     and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            " group by o.id "
            , nativeQuery=true)
    public List<ScheduledDelivery> findOrdersByVendor(@Param("vendorId") String vendorId);

    @Query(value="select o.* " +
            " from scheduled_deliveries o, scheduled_deliveries_OrderDetail oi, orderdetail i, vendor v " +
            " where o.id=oi.scheduled_deliveries_id and oi.orderDetailList_id=i.id and o.vendor_id=v.id " +
            "     and (o.deliveryDate>=:fromDate) " +
            "     and (o.deliveryDate<=:toDate) " +
            "     and o.status <= 3" +
            " group by o.id "
            , nativeQuery=true)
    public List<ScheduledDelivery> findOrderByReportRequest(@Param("fromDate") Timestamp fromDate,
                                                            @Param("toDate") Timestamp toDate);
    @Query(value="select o.* " +
            " from scheduled_deliveries o, scheduled_deliveries_OrderDetail oi, orderdetail i, vendor v " +
            " where o.id=oi.scheduled_deliveries_id and oi.details_id=i.id and o.vendor_id=v.id " +
            "     and (:fromDate is null or o.deliveryDate>=:fromDate) " +
            "     and (:toDate is null or o.deliveryDate<=:toDate) " +
            "     and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            " group by o.id "
            , nativeQuery=true)
    public List<ScheduledDelivery> findOrderByReportRequest(@Param("fromDate") Timestamp fromDate,
                                                            @Param("toDate") Timestamp toDate,
                                                            @Param("vendorId") String vendorId);

    @Query(value="SELECT count(oi.details_id), sum(i.qty*i.sellPrice) " +
            "     FROM scheduled_deliveries o, scheduled_deliveries_OrderDetail oi, orderdetail i, vendor v " +
            "     WHERE o.id=oi.scheduled_deliveries_id and oi.details_id=i.id and o.vendor_id=v.id " +
            "           and (:fromDate is null or o.deliveryDate>=:fromDate) " +
            "           and (:toDate is null or o.deliveryDate<=:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            "     GROUP BY oi.scheduled_deliveries_id "
            ,nativeQuery=true)
    public List<Object[]> findOrdersByReportRequestWithoutGroupBy(@Param("fromDate") Timestamp fromDate,
                                                                  @Param("toDate") Timestamp toDate, @Param("vendorId") String vendorId);

    @Query(value="SELECT EXTRACT(YEAR FROM o.deliveryDate), count(o.id), sum(i.qty*i.sellPrice) " +
            "     FROM scheduled_deliveries  o, scheduled_deliveries_OrderDetail oi, orderdetail i, vendor v  " +
            "     WHERE o.id=oi.scheduled_deliveries_id and oi.orderDetailList_id=i.id and o.vendor_id=v.id " +
            "           and (:fromDate is null or o.deliveryDate>=:fromDate) " +
            "           and (:toDate is null or o.deliveryDate<=:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3 " +
            "     GROUP BY EXTRACT(YEAR FROM o.deliveryDate)"
            ,nativeQuery=true)
    public List<Object[]> findOrdersByReportRequestWithGroupByYear(@Param("fromDate") Timestamp fromDate,
                                                                   @Param("toDate") Timestamp toDate, @Param("vendorId") String vendorId);

    @Query(value="SELECT DATE_FORMAT(o.deliveryDate, '%Y %b') as month, count(o.id), sum(i.qty*i.sellPrice) " +
            "    FROM scheduled_deliveries  o, scheduled_deliveries_OrderDetail oi, orderdetail i, vendor v " +
            "    WHERE o.id=oi.scheduled_deliveries_id and oi.orderDetailList_id=i.id and o.vendor_id=v.id " +
            "           and (:fromDate is null or o.deliveryDate>=:fromDate) " +
            "           and (:toDate is null or o.deliveryDate<=:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3 " +
            "    GROUP BY month "
            ,nativeQuery=true)
    public List<Object[]> findOrdersByReportRequestWithGroupByYearMonth(@Param("fromDate") Timestamp fromDate,
                                                                        @Param("toDate") Timestamp toDate, @Param("vendorId") String vendorId);

    @Query(value="SELECT DATE_FORMAT(o.deliveryDate, '%Y %b %e') as week, count(o.id), sum(i.qty*i.sellPrice) " +
            "    FROM scheduled_deliveries  o, scheduled_deliveries_OrderDetail oi, orderdetail i, vendor v " +
            "    WHERE o.id=oi.scheduled_deliveries_id and oi.orderDetailList_id=i.id and o.vendor_id=v.id " +
            "           and (:fromDate is null or o.deliveryDate>=:fromDate) " +
            "           and (:toDate is null or o.deliveryDate<=:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3 " +
            "    GROUP BY week "
            ,nativeQuery=true)
    public List<Object[]> findOrdersByReportRequestWithGroupByWeek(@Param("fromDate") Timestamp fromDate,
                                                                   @Param("toDate") Timestamp toDate, @Param("vendorId") String vendorId);

    @Query(value="SELECT EXTRACT(DAY FROM o.deliveryDate), count(o.id), sum(i.qty*i.sellPrice) " +
            "    FROM scheduled_deliveries  o, scheduled_deliveries_OrderDetail oi, orderdetail i, vendor v " +
            "    WHERE o.id=oi.scheduled_deliveries_id and oi.orderDetailList_id=i.id and o.vendor_id=v.id " +
            "           and (:fromDate is null or o.deliveryDate>=:fromDate) " +
            "           and (:toDate is null or o.deliveryDate<=:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3 " +
            "    GROUP BY EXTRACT(DAY FROM o.deliveryDate)",nativeQuery=true)
    public List<Object[]> findOrdersByReportRequestWithGroupByDay(@Param("fromDate") Timestamp fromDate,
                                                                  @Param("toDate") Timestamp toDate, @Param("vendorId") String vendorId);

//    @Query(value="SELECT count(*),a.state FROM onlineorder o WHERE o.shipping_address_id=a.id GROUP BY a.state",nativeQuery=true)
//    public List<Object[]> findOrderBillingState();
//
//    @Query(value="SELECT count(*),a.state FROM onlineorder o WHERE o.shipping_address_id=a.id GROUP BY a.state",nativeQuery=true)
//    public List<Object[]> findOrderShippingState();

    @Query(value="SELECT count(o.id), EXTRACT(YEAR FROM o.dateCreate) as year FROM onlineorder o GROUP BY year",nativeQuery=true)
    public List<Object[]> findOrderYear();

    @Query(value="SELECT count(o.id), DATE_FORMAT(o.dateCreate, '%Y %b') as month FROM onlineorder o GROUP BY month",nativeQuery=true)
    public List<Object[]> findOrderYearMonth();

    @Query(value="SELECT count(distinct o.id), c.name " +
            "     FROM onlineorder o, OnlineOrder_OrderDetail oi, orderdetail i, product p, vendor v, category c " +
            "     WHERE o.id=oi.OnlineOrder_id and oi.orderDetailList_id=i.id and i.productId=p.productnumber and p.vendorId=v.id and p.categoryId=c.id " +
            "     GROUP BY c.id" +
            "     ORDER BY count(distinct o.id) DESC " +
            "     LIMIT 10",nativeQuery=true)
    public List<Object[]> findOrderCategory();

    @Query(value="SELECT count(distinct o.id), v.userName " +
            "     FROM onlineorder o, OnlineOrder_OrderDetail oi, orderdetail i, product p, vendor v " +
            "     WHERE o.id=oi.OnlineOrder_id and oi.orderDetailList_id=i.id and i.productId=p.productnumber and p.vendorId=v.id " +
            "     GROUP BY v.id" +
            "     ORDER BY count(distinct o.id) DESC " +
            "     LIMIT 10",nativeQuery=true)
    public List<Object[]> findOrderVendor();

    @Query(value="SELECT count(distinct o.id), p.name " +
            "     FROM onlineorder o, OnlineOrder_OrderDetail oi, orderdetail i, product p " +
            "     WHERE o.id=oi.OnlineOrder_id and oi.orderDetailList_id=i.id and i.productId=p.productnumber" +
            "     GROUP BY p.productnumber " +
            "     ORDER BY count(distinct o.id) DESC " +
            "     LIMIT 10",nativeQuery=true)
    public List<Object[]> findOrderProduct();

    //for vendor report

    @Query(value="SELECT count(distinct o.id) " +
            "     FROM onlineorder o, OnlineOrder_OrderDetail oi, orderdetail i, product p, vendor v " +
            "     WHERE o.id=oi.OnlineOrder_id and oi.orderDetailList_id=i.id and i.productId=p.productnumber and p.vendorId=v.id and v.id= :vendorId " +
            "     GROUP BY v.id "
            ,nativeQuery=true)
    public Integer findOrderTotalByVendor(Integer vendorId);

    @Query(value="SELECT count(distinct o.id), c.name " +
            "     FROM onlineorder o, OnlineOrder_OrderDetail oi, orderdetail i, product p, vendor v, category c " +
            "     WHERE o.id=oi.OnlineOrder_id and oi.orderDetailList_id=i.id and i.productId=p.productnumber and p.categoryId=c.id and p.vendorId=v.id and v.id= :vendorId " +
            "     GROUP BY c.id " +
            "     ORDER BY count(distinct o.id) DESC " +
            "     LIMIT 10 "
            ,nativeQuery=true)
    public List<Object[]> findOrderByCategory(Integer vendorId);

    @Query(value="SELECT count(distinct o.id), p.name " +
            "     FROM onlineorder o, OnlineOrder_OrderDetail oi, orderdetail i, product p, vendor v " +
            "     WHERE o.id=oi.OnlineOrder_id and oi.orderDetailList_id=i.id and i.productId=p.productnumber and p.vendorId=v.id and v.id=:vendorId " +
            "     GROUP BY p.productnumber " +
            "     ORDER BY count(distinct o.id) DESC " +
            "     LIMIT 10",nativeQuery=true)
    public List<Object[]> findOrderProductByVendor(Integer vendorId);

    @Query(value="SELECT count(distinct o.id), EXTRACT(YEAR FROM o.dateCreate) as year " +
            "     FROM onlineorder o, OnlineOrder_OrderDetail oi, orderdetail i, product p, vendor v " +
            "     WHERE o.id=oi.OnlineOrder_id and oi.orderDetailList_id=i.id and i.productId=p.productnumber and p.vendorId=v.id and v.id=:vendorId " +
            "     GROUP BY year ",nativeQuery=true)
    public List<Object[]> findOrderYearByVendor(Integer vendorId);

    @Query(value="SELECT count(distinct o.id), EXTRACT(YEAR_MONTH FROM o.dateCreate) as month " +
            "     FROM onlineorder o, OnlineOrder_OrderDetail oi, orderdetail i, product p, vendor v " +
            "     WHERE o.id=oi.OnlineOrder_id and oi.orderDetailList_id=i.id and i.productId=p.productnumber and p.vendorId=v.id and v.id=:vendorId " +
            "     GROUP BY month ",nativeQuery=true)
    public List<Object[]> findOrderYearMonthByVendor(Integer vendorId);

    @Query(value="SELECT count(distinct o.id),v.state " +
            "     FROM onlineorder o, addresses a, OnlineOrder_OrderDetail oi, orderdetail i, product p, vendor v " +
            "     WHERE o.id=oi.OnlineOrder_id and oi.orderDetailList_id=i.id and i.productId=p.productnumber and p.vendorId=v.id and v.id=:vendorId " +
            "     GROUP BY v.state ",nativeQuery=true)
    public List<Object[]> findOrderBillingStateByVendor(Integer vendorId);

    @Query(value="SELECT count(distinct o.id),v.state " +
            "     FROM onlineorder o, OnlineOrder_OrderDetail oi, orderdetail i, product p, vendor v " +
            "     WHERE o.id=oi.OnlineOrder_id and oi.orderDetailList_id=i.id and i.productId=p.productnumber and p.vendorId=v.id and v.id=:vendorId " +
            "     GROUP BY v.state",nativeQuery=true)
    public List<Object[]> findOrderShippingStateByVendor(Integer vendorId);
}

