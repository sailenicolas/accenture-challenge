package ar.com.saile.accenturechallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link ar.com.saile.accenturechallenge.domain.Comment} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDto implements Serializable {

    private Long id;

    private PostDto post;

    private String name;

    private String email;

    private String body;

    public void setPostId(Long postId) {

        this.post = new PostDto();
        this.post.setId( postId );
    }
}