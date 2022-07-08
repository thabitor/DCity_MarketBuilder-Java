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

public class Director {

    @Id
    @Column (name = "id")
    private int directorId;

    @Column (name = "firstname")
    private String firstName;

    @Column (name = "lastname")
    private String lastName;

    @Column (name = "salary", nullable = false, precision = 8, scale = 2)
    private BigDecimal salary;

    @OneToOne
    @JoinColumn (name = "market_id")  // fk connecting with market's pk
    private Market market;

}
