package miu.edu.product.service;

import miu.edu.product.domain.*;
import miu.edu.product.service.dto.OrderDTO;
import miu.edu.product.service.dto.OrderDetailDTO;
import miu.edu.product.service.dto.ReportRequestDTO;
import miu.edu.product.service.dto.ScheduledDeliveryDTO;
import miu.edu.product.service.enums.ReportRequestEnum;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Converter {

    public static ReportRequestDTO convert(HttpServletRequest httpServletRequest) {
        ReportRequestDTO reportRequestDTO = new ReportRequestDTO();
        for (ReportRequestEnum e : ReportRequestEnum.values()) {
            reportRequestDTO.getRequestParams().put(e.value(), httpServletRequest.getParameter(e.value()));
        }
        return reportRequestDTO;
    }

    public static Timestamp convert(String dateString) {
        Timestamp ts = null;
        if (dateString != null) {
            String[] patterns = {"MM/dd/yyyy", "MM-dd-yyyy", "yyyy/MM/dd", "yyyy-MM-dd"};
            for (String p : patterns) {
                DateFormat df = new SimpleDateFormat(p);
                Date pd = null;
                try {
                    pd = df.parse(dateString);
                    ts = new Timestamp((pd).getTime());
                    //OK: parsing date string"
                    break;
                } catch (ParseException e) {
                    //error: parsing date string
                }
            }
        }
        return ts;
    }

    public static List<OrderDTO> convert2(List<OnlineOrder> orderList) {
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (OnlineOrder order : orderList) {
            orderDTOList.add(Converter.convert(order));
        }
        return orderDTOList;
    }

    public static List<ScheduledDeliveryDTO> convert(List<ScheduledDelivery> orderList) {
        List<ScheduledDeliveryDTO> orderDTOList = new ArrayList<>();
        for (ScheduledDelivery order : orderList) {
            orderDTOList.add(Converter.convert(order));
        }
        return orderDTOList;
    }

    public static ScheduledDeliveryDTO convert(ScheduledDelivery order) {
        ScheduledDeliveryDTO orderDTO = new ScheduledDeliveryDTO();
        if (order != null) {
            orderDTO.setOrder_id(order.getId());
            orderDTO.setUser_id((int)order.getVendor().getId());
            orderDTO.setShipping_address(order.getAddress());
            orderDTO.setStatus(order.getStatus());
            orderDTO.setDelivery_date(order.getDeliveryDate().toLocalDateTime());
            List<OrderDetail> orderDetailList = order.getDetails();
            List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();
            for (OrderDetail orderDetail : orderDetailList) {
                OrderDetailDTO orderDetailDTO = Converter.convert(orderDetail);
                orderDetailDTO.setOrder_id(order.getId());
                orderDetailDTOList.add(orderDetailDTO);
            }
            orderDTO.setOrder_detail_list(orderDetailDTOList);
        }
        return orderDTO;
    }

    public static OrderDTO convert(OnlineOrder order) {
        OrderDTO orderDTO = new OrderDTO();
        if (order != null) {
            orderDTO.setOrder_id(order.getId());
            orderDTO.setUser_id((int)order.getCustomer().getId());
//            orderDTO.setShipping_address_id(order.getBillingAddress());
            orderDTO.setShipping_address_id(order.getShippingAddress());
            orderDTO.setTax(order.getTax());
            orderDTO.setStatus(order.getStatus());
            orderDTO.setOrder_date(order.getDateCreate().toLocalDateTime());
            List<OrderDetail> OrderDetailList = order.getOrderDetailList();
            List<OrderDetailDTO> OrderDetailDTOList = new ArrayList<>();
            for (OrderDetail OrderDetail : OrderDetailList) {
                OrderDetailDTO OrderDetailDTO = Converter.convert(OrderDetail);
                OrderDetailDTO.setOrder_id(order.getId());
                OrderDetailDTOList.add(OrderDetailDTO);
            }
            orderDTO.setOrder_detail_list(OrderDetailDTOList);
        }
        return orderDTO;
    }

    public static OrderDetailDTO convert(OrderDetail OrderDetail) {
        OrderDetailDTO OrderDetailDTO = new OrderDetailDTO();
        if (OrderDetail != null) {
            OrderDetailDTO.setOrder_item_id(OrderDetail.getId());
            OrderDetailDTO.setQuantity(OrderDetail.getQty());
            OrderDetailDTO.setPrice(OrderDetail.getSellPrice());
            Product product = OrderDetail.getProduct();
            if (product != null) {
                OrderDetailDTO.setProduct_id(product.getProductnumber());
                OrderDetailDTO.setProduct_name(product.getName());
                OrderDetailDTO.setPrice(product.getPrice());
                Category category = product.getCategory();
                if (category != null) {
                    try {
                        OrderDetailDTO.setCategory_id(category.getId());
                        OrderDetailDTO.setCategory_name(category.getName());
                    } catch (Exception e) {
                        // assume could not find the category id
                    }
                }
                Vendor vendor = product.getVendor();
                if (vendor != null) {
                    try {
                        OrderDetailDTO.setVendor_id((int)vendor.getId());
                        OrderDetailDTO.setVendor_name(vendor.getUserName());
                    } catch (Exception e) {
                        // assume could not find the vendor id
                    }
                }
            }
        }
        return OrderDetailDTO;
    }

}
