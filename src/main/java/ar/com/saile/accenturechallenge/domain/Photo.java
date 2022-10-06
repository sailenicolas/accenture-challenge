package ar.com.saile.accenturechallenge.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Photo {

    static final String ID = "id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID, nullable = false)
    private Long id;

    private String title;
    private String url;
    private String thumbnailUrl;
    @ManyToOne
    private Album album;
}
