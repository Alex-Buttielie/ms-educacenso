package br.com.educacenso.app.repositories;

import br.com.educacenso.app.domains.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipioRepository extends JpaRepository<Municipio, Long> {
}
