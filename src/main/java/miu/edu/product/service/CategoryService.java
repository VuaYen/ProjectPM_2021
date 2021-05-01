package miu.edu.product.service;




import miu.edu.product.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    public List<Category> getAll();

    public Optional<Category> getById(Integer id);
}
