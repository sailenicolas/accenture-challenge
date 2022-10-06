package ar.com.saile.accenturechallenge.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter

public class Post {

    static final String ID = "id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID, nullable = false)
    private Long id;
    @ManyToOne
    private User user;
    private String title;
    @Lob
    @Column(length=512)
    private String body;
}
