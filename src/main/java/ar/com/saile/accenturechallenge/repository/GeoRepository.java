package ar.com.saile.accenturechallenge.repository;

import ar.com.saile.accenturechallenge.domain.Geo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoRepository extends JpaRepository<Geo, Long> {

}