package key.software.Service;

import key.software.Exception.AlreadyExistsException;
import key.software.Exception.ResourceNotFoundException;
import key.software.Model.Category;
import key.software.Repository.CategoryRepository;
import key.software.Response.DeleteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<?> getAllCategories() {
        return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<?> getCategoryById(int categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        if(categoryOptional.isPresent()) return new ResponseEntity<>(categoryOptional.get(),HttpStatus.FOUND);
        throw new ResourceNotFoundException("Category not found with provided id");
    }

    @Override
    public ResponseEntity<?> addCategory(Category category) {
        Optional<Category> categoryOptional = categoryRepository.findByCategoryName(category.getCategoryName());

        if(!categoryOptional.isPresent()){
            category.setCreatedTime(LocalDateTime.now());
            category.setUpdatedTime(LocalDateTime.now());
            return new ResponseEntity<>(categoryRepository.save(category),HttpStatus.CREATED);
        }
        throw new AlreadyExistsException("Category with same name already present");
    }

    @Override
    public ResponseEntity<?> updateCategory(int categoryId, Category category) {
        Optional<Category> existingCategory = categoryRepository.findById(categoryId);
        if (existingCategory.isPresent()) {
            if(categoryRepository.findByCategoryName(category.getCategoryName()).isPresent()){
                throw new AlreadyExistsException("Category with similar name already present");
            }
            category.setCategoryId(categoryId);
            category.setUpdatedTime(LocalDateTime.now());
            return new ResponseEntity<>(categoryRepository.save(category),HttpStatus.OK);
        }
        throw new ResourceNotFoundException("category not Found");
    }

    @Override
    public ResponseEntity<?> deleteCategory(int categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            categoryRepository.delete(category.get());
            return new ResponseEntity<>(new DeleteResponse("Category"),HttpStatus.OK);
        }
        throw new ResourceNotFoundException("Category not Found");
    }
}
