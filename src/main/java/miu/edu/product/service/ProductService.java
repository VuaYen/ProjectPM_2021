package miu.edu.product.service;


import miu.edu.product.domain.Product;
import miu.edu.product.domain.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
   public List<Product> all();
   public Optional<Product> getById(Integer id);
   public Page<Product> paging(String username , Pageable pageable);
   public Product save(Product product);
   public void  delete(Integer id);
   List<Product> getTopProducts();
   List<Product> getByCategory(Integer categoryid);
   public Product update(Product product);
   public void activeProduct(Integer productnumber);
   List<Product> findAllByStatus(ProductStatus status);

}
