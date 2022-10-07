package ar.com.saile.accenturechallenge.security;

import ar.com.saile.accenturechallenge.domain.SharingPermissions;
import ar.com.saile.accenturechallenge.domain.User;
import ar.com.saile.accenturechallenge.dto.AlbumDto;
import ar.com.saile.accenturechallenge.dto.SharingPermissionsDto;
import ar.com.saile.accenturechallenge.enums.PermissionType;
import ar.com.saile.accenturechallenge.exception.RecordNotFound;
import ar.com.saile.accenturechallenge.repository.SharingPermissionsRepository;
import ar.com.saile.accenturechallenge.repository.UserRepository;
import ar.com.saile.accenturechallenge.service.AlbumService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@AllArgsConstructor
public class BasePermissionsEval implements PermissionEvaluator {

    private final UserRepository userService;
    private final AlbumService albumService;
    private final SharingPermissionsRepository permissionsRepository;
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

                Optional<SharingPermissions> byAlbum_idAndUser_id = permissionsRepository.findByAlbum_IdAndUser_Id( albumDto.getId(), byUsername.getId() );
                boolean b = byAlbum_idAndUser_id.isPresent();
                if (b && !(byAlbum_idAndUser_id.get().getPermissionType().name().equalsIgnoreCase( String.valueOf( permission ) )||byAlbum_idAndUser_id.get().getPermissionType().name().equalsIgnoreCase( PermissionType.READ_WRITE.name() ))){
                    return false;
                }
                return (aCase || b);
            } else if (targetType.equalsIgnoreCase( "changePermissions" )) {
                AlbumDto albumDto = albumService.getById( (Long) targetId );
                return byUsername.getUsername().equalsIgnoreCase( albumDto.getUser().getUsername());
            }

        return false;
    }
}
