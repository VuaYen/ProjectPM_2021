package miu.edu.product.repository;

import miu.edu.product.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @Query(value = " select count(distinct c.id) " +
            "from category c, product p, vendor v " +
            "where c.id = p.categoryId and p.vendorId=v.id and (:vendorId is null or v.id=:vendorId) ", nativeQuery = true)
    public Long countCategoriesByVendorId(@Param("vendorId") String vendorId);

    @Query(value="select c.* " +
            " from scheduled_deliveries o, scheduled_deliveries_orderdetail od, orderdetail i, product p, vendor v, category c " +
            " where o.id=od.scheduled_deliveries_id and od.detials_id=i.id and i.productId=p.productnumber and p.vendorId=v.id and p.categoryId=c.id " +
            "     and (:fromDate is null or o.deliveredDate>:fromDate) " +
            "     and (:toDate is null or o.deliveredDate<:toDate) " +
            "     and (:vendorId is null or v.id=:vendorId) and" +
            " group by c.id and o.status <= 3" +
            " having 1=1 " +
            " and (:minCost is null or sum(i.qty*i.sellPrice)>:minCost) " +
            " and (:maxCost is null or sum(i.qty*i.sellPrice)<:maxCost) "
            , nativeQuery=true)
    public List<Category> findCategoryByReportRequest(@Param("fromDate") Timestamp fromDate,
                                                      @Param("toDate") Timestamp toDate,
                                                      @Param("vendorId") String vendorId,
                                                      @Param("minCost") String minCost,
                                                      @Param("maxCost") String maxCost);

    @Query(value="SELECT count(distinct o.id), sum(i.qty*i.sellPrice) " +
            "     FROM scheduled_deliveries o, scheduled_deliveries_OrderDetail oi, orderdetail i, product p, vendor v, category c " +
            "     WHERE o.id=oi.scheduled_deliveries_id and oi.scheduled_deliveries_id=i.id and i.productId=p.productnumber and o.vendor_id=v.id and p.categoryId=c.id  and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            "           and (:fromDate is null or o.deliveryDate>=:fromDate) " +
            "           and (:toDate is null or o.deliveryDate<=:toDate) " +
            "     GROUP BY c.id "
            ,nativeQuery=true)
    public List<Object[]> findCategoryByReportRequestWithoutGroupBy(@Param("fromDate") Timestamp fromDate,
                                                                    @Param("toDate") Timestamp toDate, @Param("vendorId") String vendorId);

    @Query(value="SELECT EXTRACT(YEAR FROM o.deliveryDate), count(distinct c.id), sum(i.qty*i.sellPrice) " +
            "     FROM scheduled_deliveries  o, scheduled_deliveries_OrderDetail oi, orderdetail i, product p, vendor v, category c   " +
            "     WHERE o.id=oi.scheduled_deliveries_id and oi.orderDetailList_id=i.id and i.productId=p.productnumber and o.vendor_id=v.id and p.categoryId=c.id  " +
            "           and (:fromDate is null or o.deliveryDate>=:fromDate) " +
            "           and (:toDate is null or o.deliveryDate<=:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            "     GROUP BY EXTRACT(YEAR FROM o.deliveryDate)"
            ,nativeQuery=true)
    public List<Object[]> findCategoryByReportRequestWithGroupByYear(@Param("fromDate") Timestamp fromDate,
                                                                     @Param("toDate") Timestamp toDate, @Param("vendorId") String vendorId);

    @Query(value="SELECT DATE_FORMAT(o.deliveryDate, '%Y %b') as month, count(distinct c.id), sum(i.qty*i.sellPrice) " +
            "    FROM scheduled_deliveries  o, scheduled_deliveries_OrderDetail oi, orderdetail i, product p, vendor v, category c  " +
            "    WHERE o.id=oi.scheduled_deliveries_id and oi.orderDetailList_id=i.id and i.productId=p.productnumber and o.vendor_id=v.id and p.categoryId=c.id  " +
            "           and (:fromDate is null or o.deliveryDate>=:fromDate) " +
            "           and (:toDate is null or o.deliveryDate<=:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            "    GROUP BY month "
            ,nativeQuery=true)
    public List<Object[]> findCategoryByReportRequestWithGroupByYearMonth(@Param("fromDate") Timestamp fromDate,
                                                                          @Param("toDate") Timestamp toDate, @Param("vendorId") String vendorId);

    @Query(value="SELECT DATE_FORMAT(o.deliveryDate, '%Y %b %e') as week, count(distinct c.id), sum(i.qty*i.sellPrice) " +
            "    FROM scheduled_deliveries  o, scheduled_deliveries_OrderDetail oi, orderdetail i, product p, vendor v, category c  " +
            "    WHERE o.id=oi.scheduled_deliveries_id and oi.orderDetailList_id=i.id and i.productId=p.productnumber and p.vendorId=v.id and p.categoryId=c.id  " +
            "           and (:fromDate is null or o.deliveryDate>=:fromDate) " +
            "           and (:toDate is null or o.deliveryDate<=:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            "    GROUP BY week "
            ,nativeQuery=true)
    public List<Object[]> findCategoryByReportRequestWithGroupByWeek(@Param("fromDate") Timestamp fromDate,
                                                                     @Param("toDate") Timestamp toDate, @Param("vendorId") String vendorId);

    @Query(value="SELECT EXTRACT(DAY FROM o.deliveryDate), count(distinct c.id), sum(i.qty*i.sellPrice) " +
            "    FROM scheduled_deliveries  o, scheduled_deliveries_OrderDetail oi, orderdetail i, product p, vendor v, category c  " +
            "    WHERE o.id=oi.scheduled_deliveries_id and oi.orderDetailList_id=i.id and i.productId=p.productnumber and p.vendorId=v.id and p.categoryId=c.id  " +
            "           and (:fromDate is null or o.deliveryDate>=:fromDate) " +
            "           and (:toDate is null or o.deliveryDate<=:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            "    GROUP BY EXTRACT(DAY FROM o.deliveryDate)"
            ,nativeQuery=true)
    public List<Object[]> findCategoryByReportRequestWithGroupByDay(@Param("fromDate") Timestamp fromDate,
                                                                    @Param("toDate") Timestamp toDate, @Param("vendorId") String vendorId);

}
