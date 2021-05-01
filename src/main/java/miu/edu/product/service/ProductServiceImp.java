package miu.edu.product.service;


import miu.edu.product.domain.Product;
import miu.edu.product.domain.ProductStatus;
import miu.edu.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class ProductServiceImp implements ProductService {
 @Autowired
    private ProductRepository productRepository;


@Autowired
    private CategoryService categoryService;


    @Override
    public List<Product> all() {
        return toList(productRepository.findAll());
    }

    @Override
    public Optional<Product> getById(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    public Page<Product> paging(String username , Pageable pageable) {
//        Seller seller=new Seller();
//        seller.setUsername(username);
//        return productPagingRepository.findProductsBySeller(seller,pageable);
        return null;
    }

    @Override
    public Product save(Product product) {


        product.setStatus(ProductStatus.New);

        product.setCreatedDate(new Date());

        return productRepository.save(product);
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Product> getTopProducts() {
        return toList(productRepository.findAll());
//        return toList(productRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate")));//getTopProducts());
    }



    @Override
    public List<Product> getByCategory(Integer categoryid) {
        return toList(productRepository.getByCategory( categoryid));
    }


    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void activeProduct(Integer productnumber) {
        Optional<Product> product = getById(productnumber);
        Product product1= new Product();
        if (product.isPresent()){
            product1 =product.get();
            product1.setStatus(ProductStatus.Active);
            productRepository.save(product1);
        }else {
            System.out.println("There no product on this productnumber");
        }

    }

    public static <T> List<T> toList(final Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}
