package key.software.Service;

import key.software.Exception.ResourceNotFoundException;
import key.software.Model.Category;
import key.software.Model.Product;
import key.software.Repository.CategoryRepository;
import key.software.Repository.ProductRepository;
import key.software.Response.DeleteResponse;
import key.software.Response.UndoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResponseEntity<?> getAllProduct() {
        List<Product> products = productRepository.findAll().stream().filter(a-> !a.getProductDeleted()).collect(Collectors.toList());
        return new ResponseEntity<>(products, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<?> getProductById(int productId) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if(productOptional.isPresent() && !productOptional.get().getProductDeleted()){
            return new ResponseEntity<>(productOptional.get(),HttpStatus.FOUND);
        }
        throw new ResourceNotFoundException("Product not Found");
    }

    @Override
    public ResponseEntity<?> getAllProductByCategoryId(int categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        if(categoryOptional.isPresent()){
            List<Product> products = categoryOptional.get().getProducts().stream().filter(p->!p.getProductDeleted()).collect(Collectors.toList());
            return new ResponseEntity<>(products,HttpStatus.FOUND);
        }
        throw new ResourceNotFoundException("Category id is not present");
    }

    public ResponseEntity<?> saveProductInCategory(Product product, int categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category not found"));

        product.setCategory(category);

        LocalDateTime now = LocalDateTime.now();
        product.setProductCreatedAt(now);
        product.setProductUpdatedAt(now);
        product.setProductDeleted(false);
        Product savedProduct = productRepository.save(product);

        List<Product> categoryProducts = category.getProducts();
        categoryProducts.add(savedProduct);
        category.setProducts(categoryProducts);
        category.setUpdatedTime(now);
        categoryRepository.save(category);

        return new ResponseEntity<>(savedProduct,HttpStatus.CREATED);
    }
    @Override
    public ResponseEntity<?> updateProduct(int productId, Product product) {
        Optional<Product> existingProduct = productRepository.findById(productId);
        if (existingProduct.isPresent()) {
            product.setProductId(productId);
            product.setProductCreatedAt(existingProduct.get().getProductCreatedAt());
            product.setProductUpdatedAt(LocalDateTime.now());
            return new ResponseEntity<>(productRepository.save(product),HttpStatus.OK);
        }
        throw new ResourceNotFoundException("Product Not Present");
    }

    @Override
    public ResponseEntity<?> deleteProduct(int productId) {
        Optional<Product> existingProduct = productRepository.findById(productId);
        if (existingProduct.isPresent() && !existingProduct.get().getProductDeleted()) {
            existingProduct.get().setProductDeleted(true);
            productRepository.save(existingProduct.get());
            return new ResponseEntity<>(new DeleteResponse("Product"),HttpStatus.OK);
        }
        throw new ResourceNotFoundException("Product Not Present");
    }

    @Override
    public ResponseEntity<?> undoProduct(int productId) {
        Optional<Product> existingProduct = productRepository.findById(productId);
        if (existingProduct.isPresent() && existingProduct.get().getProductDeleted()) {
            existingProduct.get().setProductDeleted(false);
            productRepository.save(existingProduct.get());
            return new ResponseEntity<>(new UndoResponse("Product"),HttpStatus.OK);
        }
        throw new ResourceNotFoundException("Product Not Present");
    }

    @Override
    public ResponseEntity<?> getAllDeletedProducts() {
        List<Product> products = productRepository.findAll().stream().filter(Product::getProductDeleted).collect(Collectors.toList());
        return new ResponseEntity<>(products, HttpStatus.FOUND);
    }

}
