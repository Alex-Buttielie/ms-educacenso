package br.com.educacenso.app.repositories;

import br.com.educacenso.app.domains.IdiomaEnsino;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdiomaEnsinoRepository extends JpaRepository<IdiomaEnsino, Long> {
}
