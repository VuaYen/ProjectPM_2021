package miu.edu.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miu.edu.product.domain.OnlineOrder;
import miu.edu.product.domain.User;

@Getter
@Setter
@NoArgsConstructor
public class CheckOutModel {
    private OnlineOrder order;
    private User customer;
}
