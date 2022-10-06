package ar.com.saile.accenturechallenge.service;

import ar.com.saile.accenturechallenge.dto.AlbumDto;
import ar.com.saile.accenturechallenge.dto.PageDto;
import ar.com.saile.accenturechallenge.dto.PhotoDto;
import ar.com.saile.accenturechallenge.dto.UserDto;
import ar.com.saile.accenturechallenge.enums.PermissionType;
import ar.com.saile.accenturechallenge.exception.LoginFailedException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService extends UserDetailsService {

    PageDto<UserDto> getAllUsers(int page, String order);

    ResponseEntity<?> login(UserDto.Login appUser, HttpServletRequest request) throws LoginFailedException;

    List<PhotoDto> getPhotosByUserId(Long id);

    List<AlbumDto> getAlbumsByUserId(Long id);

    List<UserDto> getUsersByAlbumId(Long albumId, PermissionType permission);
}
