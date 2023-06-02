package key.software.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer categoryId;
    private String categoryName;
    private String categoryDescription;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Product> products;
}
