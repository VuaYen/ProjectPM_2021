package miu.edu.product.service;

import miu.edu.product.domain.OnlineOrder;
import miu.edu.product.domain.OrderDetail;
import miu.edu.product.repository.OnlineOrderRepository;
import miu.edu.product.repository.OrderDetailRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OrderDetailImp implements OrderDetailService {

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    OnlineOrderRepository orderRepository;

    @Override
    public List<OrderDetail> getDetailByOrderId(Integer id) {

        return orderDetailRepository.getDetailsByOrderId(id);
    }

    @Override
    public String printReceipt(String format, Integer orderId) throws FileNotFoundException, JRException {

        String path ="";
        List<OrderDetail> orderDetails = orderDetailRepository.getDetailsByOrderId(orderId);
        OnlineOrder order = orderRepository.findById(orderId).get();

        //Load jrxml file and compile it
        File file = ResourceUtils.getFile("classpath:receipt.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(orderDetails);
        Map<String,Object> map=new HashedMap();
        map.put("orderNumber",order.getOrderno()); //set value to Parameter
        map.put("orderDate",order.getDateCreate().toString());
        map.put("deliveryDate",order.getDateDelivered()==null? "N/A": order.getDateDelivered().toString());
        map.put("tax",order.getTax().toString());
        map.put("shippingfee",order.getShippingFee().toString());
        map.put("total",order.getTotal().toString());
        JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,map, dataSource);
        if(format.equalsIgnoreCase("pdf")){
            byte[] pdf= JasperExportManager.exportReportToPdf(jasperPrint);
//            path= BlobAzure.Upload(pdf,"pdf");
        }
        else throw  new Error("don't support "+ format);

        return path;

    }
}
