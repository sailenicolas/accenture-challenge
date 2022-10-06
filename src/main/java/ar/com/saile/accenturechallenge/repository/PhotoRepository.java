package ar.com.saile.accenturechallenge.repository;

import ar.com.saile.accenturechallenge.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

}
