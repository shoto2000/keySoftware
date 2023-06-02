package key.software.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer productId;
    private String productName;
    private Double productPrice;
    private String productDescription;
    private String productBrand;
    private String productImageUrl;
    private Integer productAvailableQuantity;
    private Double productLengthInCm;
    private Double productBreadthInCm;
    private Double productHeightInCm;
    private String productColor;
    private LocalDateTime productCreatedAt;
    private LocalDateTime productUpdatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;

}
