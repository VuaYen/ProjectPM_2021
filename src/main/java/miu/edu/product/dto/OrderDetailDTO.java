package miu.edu.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailDTO {
    private  String productname;
    private  String quantity;
    private  String price;
    private  String total;
}
