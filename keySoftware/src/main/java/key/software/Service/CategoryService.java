package key.software.Service;

import key.software.Model.Category;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    public ResponseEntity<?> getAllCategories();
    public ResponseEntity<?> getCategoryById(int categoryId);
    public ResponseEntity<?> addCategory(Category category);
    public ResponseEntity<?> updateCategory(int categoryId, Category category);
    public ResponseEntity<?> deleteCategory(int categoryId);
}
