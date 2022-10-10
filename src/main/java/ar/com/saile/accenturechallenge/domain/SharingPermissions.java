package ar.com.saile.accenturechallenge.domain;

import ar.com.saile.accenturechallenge.enums.PermissionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "SharingPermissions.findDistinctByUser_IdIsAndAlbum_IdIsAndPermissionTypeIsOrPermissionTypeIs", query = "select distinct s from SharingPermissions s where  s.album.id = :id and s.user.id = :userid and (s.permissionType = :perms2 or s.permissionType = :perms)")
})
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

    @Enumerated
    private PermissionType permissionType;

    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder( "SharingPermissions{" );
        sb.append( "id=" ).append( id );
        sb.append( ", user=" ).append( user );
        sb.append( ", album=" ).append( album );
        sb.append( ", permissionType=" ).append( permissionType );
        sb.append( '}' );
        return sb.toString();
    }
}
