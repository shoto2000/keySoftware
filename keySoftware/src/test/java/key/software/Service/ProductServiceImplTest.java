package key.software.Service;

import key.software.Exception.ResourceNotFoundException;
import key.software.Model.Category;
import key.software.Model.Product;
import key.software.Repository.CategoryRepository;
import key.software.Repository.ProductRepository;
import key.software.Response.DeleteResponse;
import key.software.Service.ProductServiceImpl;
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

class ProductServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProduct_ReturnsListOfProducts() {
        // Arrange
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Product 1", 10.0, "Description 1", "Brand 1", "Image 1", 10, 1.0, 2.0, 3.0, "Color 1", false,LocalDateTime.now(), LocalDateTime.now(), null));
        products.add(new Product(2, "Product 2", 20.0, "Description 2", "Brand 2", "Image 2", 20, 2.0, 3.0, 4.0, "Color 2", false,LocalDateTime.now(), LocalDateTime.now(), null));
        when(productRepository.findAll()).thenReturn(products);

        // Act
        ResponseEntity<?> responseEntity = productService.getAllProduct();

        // Assert
        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof List);
        List<Product> responseProducts = (List<Product>) responseEntity.getBody();
        assertEquals(2, responseProducts.size());
    }

    @Test
    void getProductById_WithValidProductId_ReturnsProduct() {
        // Arrange
        int productId = 1;
        Product product = new Product(productId, "Product 1", 10.0, "Description 1", "Brand 1", "Image 1", 10, 1.0, 2.0, 3.0, "Color 1", false, LocalDateTime.now(), LocalDateTime.now(), null);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        ResponseEntity<?> responseEntity = productService.getProductById(productId);

        // Assert
        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof Product);
        Product responseProduct = (Product) responseEntity.getBody();
        assertEquals(productId, responseProduct.getProductId());
    }

    @Test
    void getProductById_WithInvalidProductId_ThrowsResourceNotFoundException() {
        // Arrange
        int productId = 1;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(productId));
    }

    @Test
    void getAllProductByCategoryId_WithValidCategoryId_ReturnsListOfProducts() {
        // Arrange
        int categoryId = 1;
        Category category = new Category(categoryId, "Category 1", "Description 1", LocalDateTime.now(), LocalDateTime.now());
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Product 1", 10.0, "Description 1", "Brand 1", "Image 1", 10, 1.0, 2.0, 3.0, "Color 1",false, LocalDateTime.now(), LocalDateTime.now(), null));
        products.add(new Product(2, "Product 2", 20.0, "Description 2", "Brand 2", "Image 2", 20, 2.0, 3.0, 4.0, "Color 2",false, LocalDateTime.now(), LocalDateTime.now(), null));
        category.setProducts(products);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // Act
        ResponseEntity<?> responseEntity = productService.getAllProductByCategoryId(categoryId);

        // Assert
        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof List);
        List<Product> responseProducts = (List<Product>) responseEntity.getBody();
        assertEquals(2, responseProducts.size());
    }

    @Test
    void getAllProductByCategoryId_WithInvalidCategoryId_ThrowsResourceNotFoundException() {
        // Arrange
        int categoryId = 1;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.getAllProductByCategoryId(categoryId));
    }



    @Test
    void saveProductInCategory_WithInvalidCategoryId_ThrowsResourceNotFoundException() {
        int categoryId = 1;
        Product product = new Product(null, "Product 1", 10.0, "Description 1", "Brand 1", "Image 1", 10, 1.0, 2.0, 3.0, "Color 1", false,null, null, null);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.saveProductInCategory(product, categoryId));
    }

    @Test
    void updateProduct_WithValidProductId_ReturnsUpdatedProduct() {
        int productId = 1;
        Product existingProduct = new Product(productId, "Product 1", 10.0, "Description 1", "Brand 1", "Image 1", 10, 1.0, 2.0, 3.0, "Color 1",false, LocalDateTime.now(), LocalDateTime.now(), null);
        Product updatedProduct = new Product(productId, "Updated Product", 20.0, "Updated Description", "Updated Brand", "Updated Image", 20, 2.0, 3.0, 4.0, "Updated Color",false, LocalDateTime.now(), LocalDateTime.now(), null);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        ResponseEntity<?> responseEntity = productService.updateProduct(productId, updatedProduct);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof Product);
        Product responseProduct = (Product) responseEntity.getBody();
        assertEquals(productId, responseProduct.getProductId());
        assertEquals(updatedProduct.getProductName(), responseProduct.getProductName());
        assertEquals(updatedProduct.getProductPrice(), responseProduct.getProductPrice());
    }

    @Test
    void updateProduct_WithInvalidProductId_ThrowsResourceNotFoundException() {
        int productId = 1;
        Product updatedProduct = new Product(productId, "Updated Product", 20.0, "Updated Description", "Updated Brand", "Updated Image", 20, 2.0, 3.0, 4.0, "Updated Color",false, LocalDateTime.now(), LocalDateTime.now(), null);

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(productId, updatedProduct));
    }

}
