package ar.com.saile.accenturechallenge.controller;

import ar.com.saile.accenturechallenge.dto.UserDto;
import ar.com.saile.accenturechallenge.enums.PermissionType;
import ar.com.saile.accenturechallenge.exception.BindingResultException;
import ar.com.saile.accenturechallenge.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "asc", name = "order") String order){

        return ResponseEntity.ok(  userService.getAllUsers(page, order) );
    }
    @PostMapping(path = "/auth/login")
    public ResponseEntity<?> login(@RequestBody UserDto.Login appUser, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            throw new BindingResultException( bindingResult );
        }
        return userService.login( appUser, request );
    }
    @GetMapping("{id}/photos")
    public ResponseEntity<?> getPhotosByUserId(@PathVariable Long id){
        return ResponseEntity.ok( userService.getPhotosByUserId(id) );
    }
    @GetMapping("{id}/albums")
    public ResponseEntity<?> getAlbumsByUserId(@PathVariable Long id){
        return ResponseEntity.ok( userService.getAlbumsByUserId(id) );
    }
    @GetMapping("{albumId}/{permission}")
    public ResponseEntity<?> getUsersByalbumId(@PathVariable Long albumId, @PathVariable String permission){

        return ResponseEntity.ok( userService.getUsersByAlbumId(albumId, PermissionType.valueOf( permission )) );
    }

    //    @PostMapping
    // public void create(@RequestBody UserDto album){
    //
    //    }
    //    @PutMapping
    //    public void update(@RequestBody UserDto album){
    //
    //    }
    //    @DeleteMapping("{id}")
    //    public void delete(@PathVariable int id){
    //
    //    }

}
