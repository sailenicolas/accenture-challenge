package ar.com.saile.accenturechallenge.repository;

import ar.com.saile.accenturechallenge.domain.SharingPermissions;
import ar.com.saile.accenturechallenge.enums.PermissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SharingPermissionsRepository extends JpaRepository<SharingPermissions, Long> {


    Optional<SharingPermissions> findByAlbum_IdAndUser_Email(Long album, String userId);
    Optional<SharingPermissions> findByAlbum_IdAndUser_IdAndPermissionType(Long album, Long userId, PermissionType permissionType);
    List<SharingPermissions> findAllByAlbum_IdAndPermissionType(Long album, PermissionType userId);

    Optional<SharingPermissions> findByAlbum_IdAndUser_Id(Long id, Long userId);
}
