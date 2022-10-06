package ar.com.saile.accenturechallenge.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Address {

    static final String ID = "id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID, nullable = false)
    private Long id;

    private String street;
    private String suite;
    private String city;
    private String zipcode;
    @ManyToOne
    private Geo geo;

}
