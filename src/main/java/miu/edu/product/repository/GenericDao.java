package miu.edu.product.repository;

import java.util.List;

public interface GenericDao<T> {

    T save(T t);

    void deleteById(Long id);

    T findById(Long id);

    T update(T t);   
    
    List<T> findAll();

    
}
