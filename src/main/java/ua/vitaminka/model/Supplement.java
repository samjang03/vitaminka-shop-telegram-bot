package ua.vitaminka.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "supplement")
public class Supplement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "manufacturer")
    private String manufacturer;
    @Column(name = "country_of_origin")
    private String countryOfOrigin;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "photo_url")
    private String photoUrl;
}
