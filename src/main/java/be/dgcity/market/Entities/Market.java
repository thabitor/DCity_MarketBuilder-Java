package be.dgcity.market.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private int marketId;

    @Column (name = "name")
    private String marketName;

    @Column (name = "street")
    private String marketStreet;

    @Column (name = "city")
    private String marketCity;

    @Column (name = "number")
    private int marketNumber;

    @OneToOne (mappedBy = "market")
    private Director director;

    @OneToMany(mappedBy = "market")
    private Set<Aisle> aisles  = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(
            name = "market_product",
            joinColumns = @JoinColumn(name = "market_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> productsList = new HashSet<>();

    public Market(int marketId, String marketName, String marketStreet, String marketCity, int marketNumber) {
        this.marketId = marketId;
        this.marketName = marketName;
        this.marketStreet = marketStreet;
        this.marketCity = marketCity;
        this.marketNumber = marketNumber;
    }
}
