package miu.edu.product.dto;

import lombok.Data;

@Data
public class GroupByWeekDTO extends GroupDTO {
    String week;
    public GroupByWeekDTO(Object[] objects){
        if(objects.length>2) {
            this.week = objects[0].toString();
            this.count = Integer.parseInt(objects[1].toString());
            this.sum = Double.parseDouble(objects[2].toString());
        }
    }
}
