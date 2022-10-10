package ar.com.saile.accenturechallenge.service;

import ar.com.saile.accenturechallenge.domain.Album;
import ar.com.saile.accenturechallenge.domain.Photo;
import ar.com.saile.accenturechallenge.dto.AlbumDto;
import ar.com.saile.accenturechallenge.dto.PhotoDto;
import ar.com.saile.accenturechallenge.exception.RecordNotFound;
import ar.com.saile.accenturechallenge.exception.UserNotAuthorized;
import ar.com.saile.accenturechallenge.repository.AlbumRepository;
import ar.com.saile.accenturechallenge.repository.PhotoRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;
    private final UserService userService;
    private final AlbumRepository albumRepository;
    private final ModelMapper modelMapper;
    @Override
    public PhotoDto getById(Long id) {

        Photo found = photoRepository.findById( id ).orElseThrow( () -> new RecordNotFound( "Not found" ) );
        Album foundAlbum = found.getAlbum();
        AlbumDto albumDto = new AlbumDto(foundAlbum.getId(), foundAlbum.getTitle());
        return new PhotoDto(found.getId(), found.getTitle(), found.getUrl(), found.getThumbnailUrl(), albumDto );
    }

    @Override
    public PhotoDto create(PhotoDto.Request photoDto) {
        Album album = albumRepository.findById( photoDto.getAlbumId() ).orElseThrow( () -> new RecordNotFound( "NOT FOUND Entity" ) );
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!Objects.equals( album.getUser().getId(), userService.getUserByUsername( username ).getId() ))
            throw new UserNotAuthorized("NOT AUTH");

        Photo found = new Photo();
        found.setUrl( photoDto.getUrl() );
        found.setTitle( photoDto.getTitle() );
        found.setThumbnailUrl( photoDto.getThumbnailUrl() );
        found.setAlbum( album );
        return modelMapper.map(photoRepository.save( found ), PhotoDto.class);
    }

    @Override
    public PhotoDto update(Long id, PhotoDto.Request photoDto) {
        Photo found = photoRepository.findById( id ).orElseThrow( () -> new RecordNotFound( "NOT FOUND Entity" ) );
        found.setUrl( photoDto.getUrl() );
        found.setTitle( photoDto.getTitle() );
        found.setThumbnailUrl( photoDto.getThumbnailUrl() );
        Album album = new Album();
        album.setId( photoDto.getId() );
        found.setAlbum( album );
        return modelMapper.map(found, PhotoDto.class);
    }
}
