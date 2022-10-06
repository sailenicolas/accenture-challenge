package ar.com.saile.accenturechallenge.controller;

import ar.com.saile.accenturechallenge.dto.PhotoDto;
import ar.com.saile.accenturechallenge.exception.BindingResultException;
import ar.com.saile.accenturechallenge.service.PhotoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/photos")
@AllArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @GetMapping("{id}")
    //@PreAuthorize( "hasPermission(#id,'photo', 'READ')" )
    public ResponseEntity<?> getById(@PathVariable Long id){

        return ResponseEntity.ok( photoService.getById(id));
    }
    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody PhotoDto photoDto, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            throw new BindingResultException( bindingResult );
        return ResponseEntity.ok( photoService.create(photoDto));

    }
    @PutMapping("{id}")
    //@PreAuthorize( "hasPermission(#id, 'photo','WRITE')" )
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody PhotoDto photoDto, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            throw new BindingResultException( bindingResult );
        return ResponseEntity.ok( photoService.update(id, photoDto));

    }

}
