package ar.com.saile.accenturechallenge.repository;

import ar.com.saile.accenturechallenge.domain.SharingPermissions;
import ar.com.saile.accenturechallenge.enums.PermissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SharingPermissionsRepository extends JpaRepository<SharingPermissions, Long> {


    Optional<SharingPermissions> findByAlbum_IdAndUser_Email(Long album, String userId);

    List<SharingPermissions> findAllByAlbum_IdAndPermissionType(Long album, PermissionType userId);

    @Query("from SharingPermissions u where u.album.id=:id and u.user.id=:userid and (u.permissionType = :perm or u.permissionType=:perm2 )")
    Optional<SharingPermissions> gettingAlbumById(@Param( "id" ) Long id, @Param( "userid" ) Long userid, @Param( "perm" ) PermissionType perm, @Param( "perm2" ) PermissionType perm2);

    Optional<SharingPermissions> findDistinctByUser_IdIsAndAlbum_IdIsAndPermissionTypeIsOrPermissionTypeIs(@Param( "id" ) Long id, @Param( "userid" ) Long userid, @Param( "perms2" ) PermissionType perm, @Param( "perms" ) PermissionType perm2);
}
