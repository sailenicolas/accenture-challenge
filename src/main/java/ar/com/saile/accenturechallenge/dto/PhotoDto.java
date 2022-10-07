package ar.com.saile.accenturechallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static final class Request{
        private Long id;

        @NotBlank
        private String title;

        @NotBlank
        private String url;

        @NotBlank
        private String thumbnailUrl;

        @NotNull
        private Long albumId;

    }

}