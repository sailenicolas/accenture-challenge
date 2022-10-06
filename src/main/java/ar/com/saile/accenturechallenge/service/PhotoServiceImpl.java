package ar.com.saile.accenturechallenge.service;

import ar.com.saile.accenturechallenge.domain.Album;
import ar.com.saile.accenturechallenge.domain.Photo;
import ar.com.saile.accenturechallenge.dto.AlbumDto;
import ar.com.saile.accenturechallenge.dto.PhotoDto;
import ar.com.saile.accenturechallenge.exception.RecordNotFound;
import ar.com.saile.accenturechallenge.repository.PhotoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;
    @Override
    public PhotoDto getById(Long id) {

        Photo found = photoRepository.findById( id ).orElseThrow( () -> new RecordNotFound( "Not found" ) );
        Album foundAlbum = found.getAlbum();
        AlbumDto albumDto = new AlbumDto(foundAlbum.getId(), foundAlbum.getTitle());
        return new PhotoDto(found.getId(), found.getTitle(), found.getUrl(), found.getThumbnailUrl(), albumDto );
    }

    @Override
    public PhotoDto create(PhotoDto photoDto) {

        return null;
    }

    @Override
    public PhotoDto update(Long id, PhotoDto photoDto) {

        return null;
    }
}
