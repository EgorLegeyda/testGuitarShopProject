package by.legeyda.catalogueservice.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.StringJoiner;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "catalogue", name = "t_guitar")
public class Guitar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Column(name = "c_manufacturer")
    String manufacturer;

    @Column(name = "c_model")
    String model;

    @Column(name = "c_price")
    Integer price;

    @Column(name = "c_description")
    String description;

}
