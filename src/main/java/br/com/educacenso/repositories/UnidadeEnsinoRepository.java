package br.com.educacenso.repositories;

import br.com.educacenso.app.domains.UnidadeEnsino;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnidadeEnsinoRepository extends JpaRepository<UnidadeEnsino, Long> {
}
