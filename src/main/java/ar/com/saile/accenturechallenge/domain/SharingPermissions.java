package ar.com.saile.accenturechallenge.domain;

import ar.com.saile.accenturechallenge.enums.PermissionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SharingPermissions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    public SharingPermissions(User user, Album album, PermissionType permissionType) {

        this.user = user;
        this.album = album;
        this.permissionType = permissionType;
    }

    @ManyToOne
    private Album album;
    private PermissionType permissionType;

}
