package ar.com.saile.accenturechallenge.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Comment {

    static final String ID = "id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID, nullable = false)
    private Long id;
    @ManyToOne
    private Post post;
    private String name;
    private String email;
    @Lob
    @Column(length=512)
    private String body;
}
