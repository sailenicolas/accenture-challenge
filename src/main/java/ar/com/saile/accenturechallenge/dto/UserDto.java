package ar.com.saile.accenturechallenge.dto;

import ar.com.saile.accenturechallenge.domain.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * A DTO for the {@link User} entity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "username"
)
public class UserDto implements Serializable, UserDetails {

    private Long id;

    private String name;

    private String email;

    private String username;

    private String password;

    private AddressDto address;

    private String phone;

    private String website;

    private CompanyDto company;

    private Set<RoleDto> roles;
    private List<AlbumDto> albums;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return this.getRoles();
    }

    @Override
    public String getPassword() {

        return this.password;
    }

    @Override
    public String getUsername() {

        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }


    @Setter
    @Getter
    public static class  Login {
        private String username;
        private String password;
    }
}