package miu.edu.product.domain;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Role {// extends BaseModel{
    @Id
//    @GeneratedValue
    private Long id;
    private int lv;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
