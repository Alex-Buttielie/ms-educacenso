package br.com.educacenso.app.unidadeEnsino.repositorys;

import br.com.educacenso.app.unidadeEnsino.domains.IdiomaEnsino;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdiomaEnsinoRepository extends JpaRepository<IdiomaEnsino, Long> {
}
