package ar.com.saile.accenturechallenge.repository;

import ar.com.saile.accenturechallenge.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}