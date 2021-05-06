package miu.edu.product.service;

import miu.edu.product.domain.OrderDetail;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.util.List;

public interface OrderDetailService {
    public List<OrderDetail> getDetailByOrderId(Integer id);
    public String printReceipt(String format, Integer orderId) throws FileNotFoundException, JRException;
}
