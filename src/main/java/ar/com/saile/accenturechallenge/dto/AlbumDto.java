package ar.com.saile.accenturechallenge.dto;

import ar.com.saile.accenturechallenge.domain.Album;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the {@link Album} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class AlbumDto implements Serializable {

    public AlbumDto(Long id, String title) {

        this.id = id;
        this.title = title;
    }


    private Long id;

    private String title;

    private UserDto user;

    private List<SharingPermissionsDto> permissions = new ArrayList<>();

    public void setUserId(long userId) {
        this.user = new UserDto();
        this.user.setId( userId );
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static final class Request{
        private String title;
        private List<SharingPermissionsDto.Request> permissions;
    }
}