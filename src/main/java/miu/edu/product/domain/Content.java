package miu.edu.product.domain;

import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "Content")
public class Content implements Serializable {

    @Id
    @NotNull
    private String slug;

    @NotBlank
    private String name;
    private String cont;

    public Content() {
    }


    public Content(String slug, String name, String cont){
        this.slug = slug;
        this.name=name;
        this.cont =cont;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }
}
