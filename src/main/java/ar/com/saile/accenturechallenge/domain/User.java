package ar.com.saile.accenturechallenge.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = User.ACC)
@NoArgsConstructor
public class User {
    static final String ID = "id";

    static final String ACC = "userAcc";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID, nullable = false)
    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;
    @OneToOne
    private Address address;
    private String phone;
    private String website;
    @ManyToOne
    private Company company;
    @ManyToMany
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Album> albums;
}
