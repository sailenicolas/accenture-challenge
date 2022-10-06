package ar.com.saile.accenturechallenge.controller;

import ar.com.saile.accenturechallenge.dto.AlbumDto;
import ar.com.saile.accenturechallenge.dto.SharingPermissionsDto;
import ar.com.saile.accenturechallenge.exception.BindingResultException;
import ar.com.saile.accenturechallenge.service.AlbumService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/albums")
@AllArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "asc", name = "order") String order) {

        return ResponseEntity.ok( albumService.getAllAlbums( page, order ) );
    }


    @GetMapping("{id}")
    @PreAuthorize("hasPermission(#id, 'album', 'READ')")
    public ResponseEntity<AlbumDto> getById(@PathVariable Long id) {

        return ResponseEntity.ok( albumService.getById( id ) );
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody AlbumDto.Request album, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            throw new BindingResultException( bindingResult );

        return ResponseEntity.ok( albumService.createAlbum( album ) );
    }

    @PostMapping("{id}/share")
    @PreAuthorize("hasPermission(#id, 'shareAlbum', 'READ')")
    public ResponseEntity<?> shareAlbum(@PathVariable Long id, @RequestBody SharingPermissionsDto.Request userPermissionRequest) {

        return ResponseEntity.ok( albumService.shareAlbum( id, userPermissionRequest ) );
    }

    @PutMapping("{id}/changePermissions")
    @PreAuthorize("hasPermission(#id, 'changePermissions', 'WRITE')")
    public ResponseEntity<?> changePermissions(@PathVariable Long id, @Valid @RequestBody SharingPermissionsDto.Request userPermissionRequest) {

        return ResponseEntity.ok( albumService.changePermissions( id, userPermissionRequest ) );
    }
    @PutMapping("{id}")
    @PreAuthorize("hasPermission(#id, 'album', 'WRITE')")
    public ResponseEntity<AlbumDto> update(@PathVariable Long id, @Valid @RequestBody AlbumDto album, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            throw new BindingResultException( bindingResult );
        return ResponseEntity.ok( albumService.updateAlbum( id, album ) );
    }

}
