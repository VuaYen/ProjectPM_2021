package miu.edu.product.repository;


import miu.edu.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    //not space between two object
    @Query("SELECT p FROM Product p where p.category.id= :id")
    List<Product> getByCategory(@Param("id") Integer id);

}
