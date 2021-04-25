package miu.edu.product.sender;

import miu.edu.product.domain.OnlineOrder;

public interface ISender {
    public void send(OnlineOrder onlineOrder);
}
