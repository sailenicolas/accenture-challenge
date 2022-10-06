package ar.com.saile.accenturechallenge.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Role {

    static final String ID = "id";

    static final String ROLES_PRIVILEGES = "roles_privileges";

    static final String ROLE_ID = "role_id";

    static final String PRIVILEGE_ID = "privilege_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID, nullable = false)
    private Long id;
    private String name;
}
