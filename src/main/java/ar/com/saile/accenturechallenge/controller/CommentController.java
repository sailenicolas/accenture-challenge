package ar.com.saile.accenturechallenge.controller;

import ar.com.saile.accenturechallenge.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/comments")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "", name = "name", required = false) String name,
                                    @RequestParam(defaultValue = "", name = "user", required = false) String user){

        return ResponseEntity.ok( commentService.getAllByNameOrUser(name, user));
    }
}
