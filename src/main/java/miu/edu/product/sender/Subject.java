package miu.edu.product.sender;

import miu.edu.product.domain.OnlineOrder;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private List<ISender> list= new ArrayList<>();
    public void addSender(ISender sender){
        list.add(sender);

    }
    public void notify(OnlineOrder onlineOrder){
        for(ISender sender:list){
            sender.send(onlineOrder);
        }

    }
}
