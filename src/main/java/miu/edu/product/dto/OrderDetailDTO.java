package miu.edu.product.dto;

import lombok.Data;

@Data
public class OrderDetailDTO {
    private int order_item_id;
    private int order_id;
    private int product_id;
    private String product_name;
    private int category_id;
    private String category_name;
    private Integer vendor_id;
    private String vendor_name;
    private int quantity;
    private double price;
}
