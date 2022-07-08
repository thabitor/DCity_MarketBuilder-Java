package be.dgcity.market.Entities;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Product {

    @Id
    @Column (name = "id", nullable = false, length = 5)
    private String productId;

    @Column (name = "name")
    private String productName;

    @Column (name = "brand")
    private String productBrand;

    @Column (name = "price", precision = 6, scale = 2)
    private BigDecimal price;

    @ManyToMany(mappedBy = "productsList")
    private List<Market> marketsList;

    // BIG HINT: 'ToMany' always means that the variable concerned is a list or a set or a collection as the annotation indicates;
    // the opposite is true when we see 'ToOne' which means our variable is singular!

}
