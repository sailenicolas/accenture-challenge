package ar.com.saile.accenturechallenge.repository;

import ar.com.saile.accenturechallenge.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

}
