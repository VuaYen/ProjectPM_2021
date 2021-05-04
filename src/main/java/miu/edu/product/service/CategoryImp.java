package miu.edu.product.service;

import miu.edu.product.domain.Category;
import miu.edu.product.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryImp  implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return (List<Category>) categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getById(Integer id) {

        return categoryRepository.findById(id);

    }
}
