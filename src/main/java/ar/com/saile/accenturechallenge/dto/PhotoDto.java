package ar.com.saile.accenturechallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * A DTO for the {@link ar.com.saile.accenturechallenge.domain.Photo} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class PhotoDto implements Serializable {

    private Long id;

    private String title;

    private String url;

    private String thumbnailUrl;

    private AlbumDto album;

    public void setAlbumId(long album) {

        this.album = new AlbumDto();
        this.album.setId(album);
    }
}