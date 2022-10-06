package ar.com.saile.accenturechallenge.service;

import ar.com.saile.accenturechallenge.domain.Album;
import ar.com.saile.accenturechallenge.domain.SharingPermissions;
import ar.com.saile.accenturechallenge.domain.User;
import ar.com.saile.accenturechallenge.dto.AlbumDto;
import ar.com.saile.accenturechallenge.dto.PageDto;
import ar.com.saile.accenturechallenge.dto.SharingPermissionsDto;
import ar.com.saile.accenturechallenge.dto.UserDto;
import ar.com.saile.accenturechallenge.exception.RecordNotFound;
import ar.com.saile.accenturechallenge.repository.AlbumRepository;
import ar.com.saile.accenturechallenge.repository.SharingPermissionsRepository;
import ar.com.saile.accenturechallenge.repository.UserRepository;
import ar.com.saile.accenturechallenge.utils.PageUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final SharingPermissionsRepository sharingPermissionsRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override
    public PageDto<AlbumDto> getAllAlbums(int page, String order) {

        Page<Album> albums = albumRepository.findAll( PageUtils.getPageable( page, order ) );
        Page<AlbumDto> albumDtoPage = albums.map( n ->
        {
            UserDto userDto = new UserDto();
            User user = n.getUser();
            userDto.setId( user.getId() );
            userDto.setUsername( user.getUsername() );
            userDto.setName( user.getName() );
            userDto.setEmail( user.getEmail() );
            return new AlbumDto( n.getId(), n.getTitle(), userDto, n.getPermissions().stream().map( p -> modelMapper.map( p, SharingPermissionsDto.class ) ).toList());
        } );
        return PageUtils.getPageDto( albumDtoPage );
    }

    @Override
    public AlbumDto createAlbum(AlbumDto.Request album) {

        Album album2 = new Album();
        album2.setTitle( album.getTitle() );
        User found = userRepository.findByUsername( SecurityContextHolder.getContext().getAuthentication().getName() ).orElseThrow( () -> new RecordNotFound( "NOT FOUND1" ) );

        album2.setUser(found);

        Album source = albumRepository.save( album2 );

        List<SharingPermissions> sharingPermissions = album.getPermissions().stream().map( p -> {
            SharingPermissions permissions = new SharingPermissions();
            User user = userRepository.findByEmail( p.getUserEmail() ).orElseThrow( () -> new RecordNotFound( "NOT FOUND2" ) );
            permissions.setUser( user );
            permissions.setPermissionType( p.getPermissionType() );
            permissions.setAlbum( source );
            source.addPermissions( permissions );
            return permissions;
        } ).toList();
        sharingPermissionsRepository.saveAll( sharingPermissions );
        albumRepository.save( source );
        return modelMapper.map( source, AlbumDto.class );
    }

    @Override
    public AlbumDto shareAlbum(Long id, SharingPermissionsDto.Request userEmail) {

        Album album = albumRepository.findById( id ).orElseThrow( () -> new RecordNotFound( "NOT FOUND" ) );
        User user = userRepository.findByEmail( userEmail.getUserEmail() ).orElseThrow( () -> new RecordNotFound( "NOT FOUND" ) );
        SharingPermissions permission = new SharingPermissions( user, album, userEmail.getPermissionType() );
        sharingPermissionsRepository.save( permission );
        album.addPermissions( permission );
        return modelMapper.map( albumRepository.save( album ), AlbumDto.class );
    }

    @Override
    public AlbumDto changePermissions(Long albumId, SharingPermissionsDto.Request userPermissionRequest) {
        Album album = albumRepository.findById( albumId ).orElseThrow( () -> new RecordNotFound( "Album NOT FOUND" ) );
        SharingPermissions found = sharingPermissionsRepository.findByAlbum_IdAndUser_Email( albumId, userPermissionRequest.getUserEmail() ).orElseThrow( () -> new RecordNotFound( "NOT FOUND" ) );
        found.setPermissionType( userPermissionRequest.getPermissionType() );
        sharingPermissionsRepository.save( found );
        return modelMapper.map( album, AlbumDto.class);
    }

    @Override
    public AlbumDto updateAlbum(Long id, AlbumDto album) {
        Album found = albumRepository.findById( id ).orElseThrow( () -> new RecordNotFound( "Album NOT FOUND" ) );
        found.setTitle( album.getTitle() );
        found.setPermissions( album.getPermissions().stream().map( p -> modelMapper.map( p, SharingPermissions.class )).toList() );
        return modelMapper.map( found, AlbumDto.class);
    }

    @Override
    public AlbumDto getById(Long id) {

        Album found = albumRepository.findById( id ).orElseThrow( () -> new RecordNotFound( "Album NOT FOUND" ) );

        AlbumDto albumDto = modelMapper.map( found, AlbumDto.class );
        albumDto.setUser( modelMapper.map( found.getUser(), UserDto.class ) );
        albumDto.getUser().setPassword( null );
        return albumDto;
    }
}
