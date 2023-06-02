package key.software.Service;

import key.software.Exception.AlreadyExistsException;
import key.software.Exception.ResourceNotFoundException;
import key.software.Model.Category;
import key.software.Repository.CategoryRepository;
import key.software.Response.DeleteResponse;
import key.software.Service.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCategories_ReturnsListOfCategories() {
        // Arrange
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Category 1", "Description 1", LocalDateTime.now(), LocalDateTime.now()));
        categories.add(new Category(2, "Category 2", "Description 2", LocalDateTime.now(), LocalDateTime.now()));
        when(categoryRepository.findAll()).thenReturn(categories);

        // Act
        ResponseEntity<?> responseEntity = categoryService.getAllCategories();

        // Assert
        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof List);
        List<Category> responseCategories = (List<Category>) responseEntity.getBody();
        assertEquals(2, responseCategories.size());
    }

    @Test
    void getCategoryById_WithValidCategoryId_ReturnsCategory() {
        // Arrange
        int categoryId = 1;
        Category category = new Category(categoryId, "Category 1", "Description 1", LocalDateTime.now(), LocalDateTime.now());
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // Act
        ResponseEntity<?> responseEntity = categoryService.getCategoryById(categoryId);

        // Assert
        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof Category);
        Category responseCategory = (Category) responseEntity.getBody();
        assertEquals(categoryId, responseCategory.getCategoryId());
    }

    @Test
    void getCategoryById_WithInvalidCategoryId_ThrowsResourceNotFoundException() {
        // Arrange
        int categoryId = 1;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategoryById(categoryId));
    }

    @Test
    void addCategory_WithUniqueCategoryName_ReturnsCreatedCategory() {
        // Arrange
        Category category = new Category(null, "New Category", "New Description", null, null);
        when(categoryRepository.findByCategoryName(category.getCategoryName())).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        // Act
        ResponseEntity<?> responseEntity = categoryService.addCategory(category);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof Category);
        Category createdCategory = (Category) responseEntity.getBody();
        assertEquals(category.getCategoryName(), createdCategory.getCategoryName());
    }

    @Test
    void addCategory_WithDuplicateCategoryName_ThrowsAlreadyExistsException() {
        // Arrange
        Category category = new Category(null, "Duplicate Category", "New Description", null, null);
        when(categoryRepository.findByCategoryName(category.getCategoryName())).thenReturn(Optional.of(category));

        // Act & Assert
        assertThrows(AlreadyExistsException.class, () -> categoryService.addCategory(category));
    }

    @Test
    void updateCategory_WithValidCategoryIdAndUniqueCategoryName_ReturnsUpdatedCategory() {
        // Arrange
        int categoryId = 1;
        Category existingCategory = new Category(categoryId, "Category 1", "Description 1", LocalDateTime.now(), LocalDateTime.now());
        Category updatedCategory = new Category(categoryId, "Updated Category", "Updated Description", null, LocalDateTime.now());
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.findByCategoryName(updatedCategory.getCategoryName())).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);

        // Act
        ResponseEntity<?> responseEntity = categoryService.updateCategory(categoryId, updatedCategory);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof Category);
        Category updatedResult = (Category) responseEntity.getBody();
        assertEquals(updatedCategory.getCategoryName(), updatedResult.getCategoryName());
        assertEquals(updatedCategory.getCategoryDescription(), updatedResult.getCategoryDescription());
    }

    @Test
    void updateCategory_WithValidCategoryIdAndDuplicateCategoryName_ThrowsAlreadyExistsException() {
        // Arrange
        int categoryId = 1;
        Category existingCategory = new Category(categoryId, "Category 1", "Description 1", LocalDateTime.now(), LocalDateTime.now());
        Category updatedCategory = new Category(categoryId, "Duplicate Category", "Updated Description", null, LocalDateTime.now());
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.findByCategoryName(updatedCategory.getCategoryName())).thenReturn(Optional.of(updatedCategory));

        // Act & Assert
        assertThrows(AlreadyExistsException.class, () -> categoryService.updateCategory(categoryId, updatedCategory));
    }

    @Test
    void updateCategory_WithInvalidCategoryId_ThrowsResourceNotFoundException() {
        // Arrange
        int categoryId = 1;
        Category updatedCategory = new Category(categoryId, "Updated Category", "Updated Description", null, LocalDateTime.now());
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> categoryService.updateCategory(categoryId, updatedCategory));
    }

    @Test
    void deleteCategory_WithValidCategoryId_DeletesCategory() {
        // Arrange
        int categoryId = 1;
        Category category = new Category(categoryId, "Category 1", "Description 1", LocalDateTime.now(), LocalDateTime.now());
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // Act
        ResponseEntity<?> responseEntity = categoryService.deleteCategory(categoryId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof DeleteResponse);
        DeleteResponse deleteResponse = (DeleteResponse) responseEntity.getBody();
        assertEquals("Category Deleted Successfully", deleteResponse.getMessage());
        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    void deleteCategory_WithInvalidCategoryId_ThrowsResourceNotFoundException() {
        // Arrange
        int categoryId = 1;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> categoryService.deleteCategory(categoryId));
    }
}
