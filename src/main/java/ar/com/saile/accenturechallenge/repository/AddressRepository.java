package ar.com.saile.accenturechallenge.repository;

import ar.com.saile.accenturechallenge.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}