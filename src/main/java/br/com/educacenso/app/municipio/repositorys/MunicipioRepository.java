package br.com.educacenso.app.municipio.repositorys;

import br.com.educacenso.app.municipio.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipioRepository extends JpaRepository<Municipio, Long> {
}
