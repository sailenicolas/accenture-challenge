package ar.com.saile.accenturechallenge.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Geo {

    static final String ID = "id";

    @Id
    @Column(name = ID, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lat;
    private String lng;

}
