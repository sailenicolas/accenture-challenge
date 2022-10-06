package ar.com.saile.accenturechallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * A DTO for the {@link ar.com.saile.accenturechallenge.domain.Role} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class RoleDto implements Serializable, GrantedAuthority {

    private Long id;

    private String name;

    @Override
    public String getAuthority() {

        return "ROLE_"+this.name;
    }
}