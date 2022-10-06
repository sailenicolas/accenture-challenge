package ar.com.saile.accenturechallenge.service;

import ar.com.saile.accenturechallenge.dto.CommentDto;

import java.util.List;

public interface CommentService {

    List<CommentDto> getAllByNameOrUser(Long postid, Long id, String name, String user);

    List<CommentDto> getAllByNameOrUser(String name, String user);
}
