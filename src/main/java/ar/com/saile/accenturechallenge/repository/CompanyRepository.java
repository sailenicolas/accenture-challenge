package ar.com.saile.accenturechallenge.repository;

import ar.com.saile.accenturechallenge.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

}
