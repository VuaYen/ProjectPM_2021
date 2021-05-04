package miu.edu.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miu.edu.product.domain.OnlineOrder;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveCartModel {
    private Integer cartItemTotal;
    private Integer orderItemTotal;
    private Integer productId;
    private OnlineOrder order;
}
