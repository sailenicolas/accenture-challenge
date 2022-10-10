package ar.com.saile.accenturechallenge.security;

import ar.com.saile.accenturechallenge.domain.SharingPermissions;
import ar.com.saile.accenturechallenge.domain.User;
import ar.com.saile.accenturechallenge.dto.AlbumDto;
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
        String value = String.valueOf( permission );
        PermissionType permissionReceived = value.equalsIgnoreCase( "READ_WRITE")
                ? PermissionType.READ_WRITE : value.equalsIgnoreCase( "WRITE")
                ? PermissionType.WRITE : PermissionType.READ ;
        User byUsername = userService.findByUsername( authentication.getName() ).orElseThrow(() -> new RecordNotFound( "NOT FOUND" ) );
            if (targetDomainObject instanceof AlbumDto domainObject)
            {
                if (Objects.equals( domainObject.getUser().getId(), byUsername.getId() ))
                    return true;
                Optional<SharingPermissions> permissions = permissionsRepository.gettingAlbumById(
                        domainObject.getId(),
                        byUsername.getId(),
                        permissionReceived,
                        PermissionType.READ_WRITE );
                return permissions.isPresent();
            }
        return false;
    }


    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {

        String value = String.valueOf( permission );
        PermissionType permissionReceived = value.equalsIgnoreCase( "READ_WRITE")
                ? PermissionType.READ_WRITE : value.equalsIgnoreCase( "WRITE")
                ? PermissionType.WRITE : PermissionType.READ ;
        User byUsername = userService.findByUsername( authentication.getName() ).orElseThrow(() -> new RecordNotFound( "NOT FOUND" ) );
            if (targetType.equalsIgnoreCase( "album" ) || targetType.equalsIgnoreCase( "shareAlbum" )) {
                AlbumDto albumDto = albumService.getById( (Long) targetId );
                if(byUsername.getUsername().equalsIgnoreCase( albumDto.getUser().getUsername() ))
                    return true;

                Optional<SharingPermissions> sharingPermissions = permissionsRepository.findDistinctByUser_IdIsAndAlbum_IdIsAndPermissionTypeIsOrPermissionTypeIs(
                        (Long) targetId,
                        byUsername.getId(),
                        permissionReceived,
                        PermissionType.READ_WRITE );
                return sharingPermissions.isPresent();
            } else if (targetType.equalsIgnoreCase( "changePermissions" )) {
                AlbumDto albumDto = albumService.getById( (Long) targetId );
                return byUsername.getUsername().equalsIgnoreCase( albumDto.getUser().getUsername());
            }

        return false;
    }
}
