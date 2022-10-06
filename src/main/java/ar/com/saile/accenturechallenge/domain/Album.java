package ar.com.saile.accenturechallenge.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter

public class Album {
    static final String ID = "id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID, nullable = false)
    private Long id;
    private String title;
    @ManyToOne
    private User user;
    @ManyToMany
    private List<SharingPermissions> permissions = new ArrayList<>();

    public void addPermissions(SharingPermissions permission) {
        permissions.add( permission );
    }
}
