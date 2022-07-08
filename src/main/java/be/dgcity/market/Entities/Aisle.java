package be.dgcity.market.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Aisle {

    @Id
    @Column (name = "id")
    private int aisleId;

    @Column (name = "type")
    private String aisleType;

    @ManyToOne
    @Column (name = "market_id")  // fk that connects with market pk
    private Market market;


}
