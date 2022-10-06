package ar.com.saile.accenturechallenge.repository;

import ar.com.saile.accenturechallenge.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}