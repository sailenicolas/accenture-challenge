package ar.com.saile.accenturechallenge.service;

import ar.com.saile.accenturechallenge.domain.Comment;
import ar.com.saile.accenturechallenge.dto.CommentDto;
import ar.com.saile.accenturechallenge.repository.CommentRepository;
import ar.com.saile.accenturechallenge.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CommentDto> getAllByNameOrUser(Long postid, Long id, String name, String user) {

        return null;
    }

    @Override
    public List<CommentDto> getAllByNameOrUser(String name, String user) {
        List<Comment> comments = new ArrayList<>();
        if (!name.isBlank() && !name.isEmpty()) {
            comments = commentRepository.findAllByNameLike(name);
        }
        if (!user.isBlank() && !user.isEmpty()) {
            comments = commentRepository.findAllByEmailLikeIgnoreCase(name);
        }
        return comments.stream().map( p -> modelMapper.map(p, CommentDto.class) ).toList();
    }
}
