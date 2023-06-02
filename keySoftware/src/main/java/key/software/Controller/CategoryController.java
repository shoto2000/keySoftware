package key.software.Controller;

import key.software.Model.Category;
import key.software.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<?> addNewCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public  ResponseEntity<?> getCategoryById(@PathVariable("id") int categoryId){
        return categoryService.getCategoryById(categoryId);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCategoryById(@RequestParam("id") int categoryId, @RequestBody Category category){
        return categoryService.updateCategory(categoryId,category);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCategoryById(@RequestParam("id") int categoryId){
        return categoryService.deleteCategory(categoryId);
    }
}
