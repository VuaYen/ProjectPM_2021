package miu.edu.product.dto.pdf;

import lombok.Data;

@Data
public class OrderVendorDTO {
    private int total;
    private String name;
    public OrderVendorDTO(Object[] objects){
        if(objects.length>1){
            this.total = Integer.parseInt(objects[0].toString());
            this.name = objects[1].toString();
        }
    }
}
