package key.software.Controller;

import key.software.Model.Product;
import key.software.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<?> addProductInCategory(@RequestBody Product product, @RequestParam("id") int categoryId){
        return productService.saveProductInCategory(product,categoryId);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllProducts(){
        return productService.getAllProduct();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") int productId){
        return productService.getProductById(productId);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getProductByCategoryId(@PathVariable("id") int categoryId){
        return productService.getAllProductByCategoryId(categoryId);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestParam("id") int productId, @RequestBody Product product){
        return productService.updateProduct(productId,product);
    }

    @PutMapping("/delete")
    public ResponseEntity<?> deleteProduct(@RequestParam("id") int productId){
        return productService.deleteProduct(productId);
    }

    @PutMapping("/undo")
    public ResponseEntity<?> undoProduct(@RequestParam("id") int productId){
        return productService.undoProduct(productId);
    }

    @GetMapping("/deleted/")
    public ResponseEntity<?> getAllDeletedProduct(){
        return productService.getAllDeletedProducts();
    }
}
