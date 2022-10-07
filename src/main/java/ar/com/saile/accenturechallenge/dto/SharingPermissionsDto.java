package ar.com.saile.accenturechallenge.dto;

import ar.com.saile.accenturechallenge.enums.PermissionType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * A DTO for the {@link ar.com.saile.accenturechallenge.domain.SharingPermissions} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class SharingPermissionsDto implements Serializable {

    private Long id;
    @JsonIgnore
    private UserDto user;
    private AlbumDto album;
    private PermissionType permissionType;

    public String getUsername() {

        return user.getUsername();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static final  class Request {
        @NotBlank
        private String userEmail;
        private PermissionType permissionType;
    }
}