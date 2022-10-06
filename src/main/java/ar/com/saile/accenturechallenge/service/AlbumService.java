package ar.com.saile.accenturechallenge.service;

import ar.com.saile.accenturechallenge.dto.AlbumDto;
import ar.com.saile.accenturechallenge.dto.PageDto;
import ar.com.saile.accenturechallenge.dto.SharingPermissionsDto;

public interface AlbumService {

    PageDto<AlbumDto> getAllAlbums(int page, String order);

    AlbumDto createAlbum(AlbumDto.Request album);

    AlbumDto shareAlbum(Long id, SharingPermissionsDto.Request userEmail);

    AlbumDto changePermissions(Long albumId, SharingPermissionsDto.Request userPermissionRequest);

    AlbumDto updateAlbum(Long id, AlbumDto album);

    AlbumDto getById(Long id);
}
