package ar.com.saile.accenturechallenge.security;

import ar.com.saile.accenturechallenge.domain.User;
import ar.com.saile.accenturechallenge.dto.AlbumDto;
import ar.com.saile.accenturechallenge.dto.SharingPermissionsDto;
import ar.com.saile.accenturechallenge.enums.PermissionType;
import ar.com.saile.accenturechallenge.exception.RecordNotFound;
import ar.com.saile.accenturechallenge.repository.UserRepository;
import ar.com.saile.accenturechallenge.service.AlbumService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class BasePermissionsEval implements PermissionEvaluator {

    private final UserRepository userService;
    private final AlbumService albumService;
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

        User byUsername = userService.findByUsername( authentication.getName() ).orElseThrow(() -> new RecordNotFound( "NOT FOUND" ) );
            if (targetDomainObject instanceof AlbumDto domainObject)
            {
                if (Objects.equals( domainObject.getUser().getId(), byUsername.getId() ))
                    return true;
                List<SharingPermissionsDto> permissionsDtos = getPermissionsDtos( byUsername, domainObject, String.valueOf( permission ) );
                return permissionsDtos.size() > 0;
            }
        return false;
    }

    private static List<SharingPermissionsDto> getPermissionsDtos(User byUsername, AlbumDto domainObject, String permission) {

        return domainObject.getPermissions().stream().distinct().filter( sharingPermissionsDto -> Objects.equals( sharingPermissionsDto.getUser().getId(), byUsername.getId() ) ).filter( p -> p.getPermissionType().name().equalsIgnoreCase( permission) || p.getPermissionType().name().equalsIgnoreCase( PermissionType.READ_WRITE.name() ) ).toList();
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        User byUsername = userService.findByUsername( authentication.getName() ).orElseThrow(() -> new RecordNotFound( "NOT FOUND" ) );
            if (targetType.equalsIgnoreCase( "album" ) || targetType.equalsIgnoreCase( "shareAlbum" )) {
                AlbumDto albumDto = albumService.getById( (Long) targetId );
                boolean aCase = byUsername.getUsername().equalsIgnoreCase( albumDto.getUser().getUsername() );
                List<SharingPermissionsDto> permissionsDtos = albumDto.getPermissions().stream().
                        filter( p -> {
                            String value = String.valueOf( permission );
                            boolean aCase1 = p.getPermissionType().name().equalsIgnoreCase( PermissionType.READ_WRITE.name() );
                            boolean equalsUser = Objects.equals( p.getUser().getId(), byUsername.getId() );
                            boolean aCase2 = p.getPermissionType().name().equalsIgnoreCase( value );
                            return (aCase1 || aCase2) && equalsUser;
                                }
                        )
                        .toList();
                boolean b = permissionsDtos.size() > 0;
                return (aCase || b);
            } else if (targetType.equalsIgnoreCase( "changePermissions" )) {
                AlbumDto albumDto = albumService.getById( (Long) targetId );
                return byUsername.getUsername().equalsIgnoreCase( albumDto.getUser().getUsername());
            }

        return false;
    }
}
