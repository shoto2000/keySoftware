package key.software.Service;

import key.software.Model.Product;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    public ResponseEntity<?> getAllProduct();
    public ResponseEntity<?> getProductById(int productId);
    public ResponseEntity<?> getAllProductByCategoryId(int categoryId);
    public ResponseEntity<?> saveProductInCategory(Product product, int categoryId);
    public ResponseEntity<?> updateProduct(int productId, Product product);
    public ResponseEntity<?> deleteProduct(int productId);
    public ResponseEntity<?> undoProduct(int productId);
    public ResponseEntity<?> getAllDeletedProducts();
}
