package br.com.educacenso.app.unidadeEnsino.repositorys;

import br.com.educacenso.app.unidadeEnsino.domains.UnidadeEnsino;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnidadeEnsinoRepository extends JpaRepository<UnidadeEnsino, Long> {
}
