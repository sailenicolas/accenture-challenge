package ar.com.saile.accenturechallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * A DTO for the {@link ar.com.saile.accenturechallenge.domain.Post} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class PostDto implements Serializable {

    private Long id;

    private UserDto user;

    private String title;
    private String body;

    public void setUserId(Long userId) {

        this.user = new UserDto();
        this.user.setId( userId );
    }
}