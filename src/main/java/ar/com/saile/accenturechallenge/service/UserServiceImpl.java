package ar.com.saile.accenturechallenge.service;

import ar.com.saile.accenturechallenge.domain.*;
import ar.com.saile.accenturechallenge.dto.*;
import ar.com.saile.accenturechallenge.enums.PermissionType;
import ar.com.saile.accenturechallenge.exception.LoginFailedException;
import ar.com.saile.accenturechallenge.exception.RecordNotFound;
import ar.com.saile.accenturechallenge.repository.PhotoRepository;
import ar.com.saile.accenturechallenge.repository.SharingPermissionsRepository;
import ar.com.saile.accenturechallenge.repository.UserRepository;
import ar.com.saile.accenturechallenge.utils.PageUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;
    private final SharingPermissionsRepository sharingPermissionsRepository;

    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User n = userRepository.findByUsername( username ).orElseThrow( () -> new UsernameNotFoundException( "Not found" ) );
        UserDto details = getUserDetails( n );
        details.setPassword( n.getPassword() );
        return details;
    }

    private UserDto getUserDetails(User n) {

        Address nAddress = n.getAddress();
        Geo geoOr = nAddress.getGeo();
        GeoDto geo = modelMapper.map( geoOr, GeoDto.class );
        AddressDto addressDto = modelMapper.map( nAddress, AddressDto.class );
        CompanyDto companyDto = modelMapper.map( n.getCompany(), CompanyDto.class );
        List<AlbumDto> albumDtos = n.getAlbums().stream().map( p -> modelMapper.map( p, AlbumDto.class ) ).toList();
        Set<RoleDto> roleDtos = n.getRoles().stream().map( p -> modelMapper.map( p, RoleDto.class ) ).collect( Collectors.toSet());
        return new UserDto(n.getId(), n.getName(), n.getEmail(), n.getUsername(), "", addressDto , n.getPhone(), n.getWebsite(), companyDto, roleDtos, albumDtos );
    }

    @Override
    public PageDto<UserDto> getAllUsers(int page, String order) {
        Page<User> users = userRepository.findAll( PageUtils.getPageable( page, order ) );
        Page<UserDto> userPage = users.map( this::getUserDetails );
        return PageUtils.getPageDto( userPage );
    }

    private final Algorithm algorithm;

    private final BCryptPasswordEncoder passwordEncoder;

    public String createToken(UserDto userDetails, String issuer) {

        LocalDateTime now = LocalDateTime.now();
        Date expiresAt = new Date();
        Date expiredAt = new Date( now.plusHours( 6 ).toInstant( ZoneOffset.UTC ).toEpochMilli() );

        return JWT.create()
                .withNotBefore( expiresAt )
                .withSubject( userDetails.getUsername() )
                .withExpiresAt( expiredAt )
                .withIssuer( issuer )
                .withClaim( "scopes", userDetails.getAuthorities().stream().map( GrantedAuthority::getAuthority ).collect( Collectors.joining( "," ) ) )
                .sign( algorithm );
    }

    @Override
    public ResponseEntity<?> login(UserDto.Login appUser, HttpServletRequest request) throws LoginFailedException {

        User userDetails = userRepository.findByUsername( appUser.getUsername() ).orElseThrow(() -> new UsernameNotFoundException( "NOT FOUND" ));
        if (passwordEncoder.matches( appUser.getPassword(), userDetails.getPassword() )) {
            return ResponseEntity.ok( new LoginResult( this.createToken( modelMapper.map( userDetails, UserDto.class ), request.getServerName() ) ) );
        }
        throw new LoginFailedException( "Login FAILED" );
    }

    @Override
    public List<PhotoDto> getPhotosByUserId(Long id) {

        User found = userRepository.findById( id ).orElseThrow( () -> new UsernameNotFoundException( "USER NOT FOUND" ) );
        List<Long> foundAlbums = found.getAlbums().stream().map( Album::getId ).toList();
        List<Photo> photos = photoRepository.findAllById( foundAlbums );

        return photos.stream().map( p -> modelMapper.map( p, PhotoDto.class ) ).toList();
    }

    @Override
    public List<AlbumDto> getAlbumsByUserId(Long id) {
        User found = userRepository.findById( id ).orElseThrow( () -> new RecordNotFound( "USER NOT FOUND" ) );
        return found.getAlbums().stream().map( p -> modelMapper.map( p, AlbumDto.class ) ).toList();
    }

    @Override
    public List<UserDto> getUsersByAlbumId(Long albumId, PermissionType permission) {

        List<SharingPermissions> byAlbum_idAndPermissionType = sharingPermissionsRepository.findAllByAlbum_IdAndPermissionType( albumId, permission );
        List<User> users = userRepository.findAllById( byAlbum_idAndPermissionType.stream().map( p -> p.getUser().getId() ).toList() );
        return users.stream().map( p -> {
            UserDto userDto = modelMapper.map( p, UserDto.class );
            userDto.setPassword( null );
            return userDto;
        } ).toList();
    }
}
