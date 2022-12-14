package ar.com.saile.accenturechallenge.repository;

import ar.com.saile.accenturechallenge.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {


    List<Comment> findAllByNameContainsIgnoreCase(String name);
    List<Comment> findAllByEmailIsLikeIgnoreCase(String email);

}